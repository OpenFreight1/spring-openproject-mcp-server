// Jenkinsfile — OpenProject MCP Server (OF1 fork CI)
//
// Runs on the in-cluster Kubernetes Jenkins (cloud "kubernetes", namespace
// "jenkins", serviceAccount "default") — the SAME controller as OpenFreight1/infras,
// OpenFreight1/beehive and BeeCO-CoreCO-Management-Service. Agents are PODS (no Docker
// daemon), so each tool runs in its own container, selected per-stage with container("...").
//
// This is the Maven/Java port of the BeeCO-CoreCO-Management-Service pipeline — pod shape,
// hadolint, buildah->Harbor are structurally identical. Only the image coordinates differ
// (registry.openfreightone.com/platform/openproject-mcp instead of beeco/...), because this
// repo is a fork of the community tmskln/spring-openproject-mcp-server (MIT), kept on our own
// Harbor so we can ship OF1-only tools (e.g. workPackageAddComment) ahead of upstream review.
//
// PLUGIN NOTE (verified against apps/jenkins/values.yaml in infras): Timestamper, AnsiColor,
// JUnit and Workspace Cleanup are NOT installed, so timestamps()/ansiColor()/junit/cleanWs are
// intentionally OMITTED. Pods are ephemeral (podRetention: Never).
//
// Scope: CI (build + test) PLUS an image build & push to Harbor via Buildah, gated to the
// deployment branch (main -> prod). No SonarQube stage yet (no 'openproject-mcp' project
// configured in SonarQube) — add one following BeeCO-CoreCO-Management-Service's pattern if
// wanted later.

pipeline {

  agent {
    kubernetes {
      defaultContainer 'maven'
      yaml '''
apiVersion: v1
kind: Pod
metadata:
  namespace: jenkins
  labels:
    jenkins/jenkins-jenkins-agent: "true"
spec:
  serviceAccountName: default
  securityContext:
    runAsUser: 0
  containers:
    - name: maven
      # pom.xml pins java.version=25 (not 21 like BeeCO-CoreCO-Management-Service) — the
      # compiler plugin's <release> can't target a JDK newer than the one running it.
      image: maven:3.9-eclipse-temurin-25
      command: ["sh", "-c", "sleep infinity"]
      tty: true
      resources:
        requests: { cpu: "250m",  memory: "1Gi" }
        limits:   { cpu: "2000m", memory: "2Gi" }
    - name: hadolint
      image: hadolint/hadolint:v2.12.0-alpine
      command: ["sh", "-c", "sleep infinity"]
      tty: true
      resources:
        requests: { cpu: "50m",  memory: "64Mi" }
        limits:   { cpu: "300m", memory: "256Mi" }
    - name: buildah
      image: quay.io/buildah/stable:v1.43.1
      command: ["sh", "-c", "sleep infinity"]
      tty: true
      securityContext:
        privileged: true   # SYS_ADMIN + unconfined seccomp/AppArmor for buildah's mounts
      env:
        - { name: STORAGE_DRIVER,     value: overlay }
        - { name: BUILDAH_ISOLATION,  value: chroot }
        - { name: REGISTRY_AUTH_FILE, value: /tmp/auth.json }
        - { name: CONTAINERS_REGISTRIES_CONF, value: /tmp/registries.conf }
      volumeMounts:
        - name: buildah-storage
          mountPath: /var/lib/containers
      resources:
        requests: { cpu: "250m",  memory: "1Gi" }
        limits:   { cpu: "2",     memory: "4Gi" }
  volumes:
    - name: buildah-storage
      emptyDir: {}
'''
    }
  }

  options {
    disableConcurrentBuilds(abortPrevious: true)
    buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '10'))
    timeout(time: 30, unit: 'MINUTES')
  }

  stages {

    stage('Build & Test') {
      steps {
        checkout scm
        container('maven') {
          // No mvnw checked in; Maven comes from the container. Tests run (not skipped),
          // EXCEPT the 'integration_remote' tag (OpenProjectApiClientIntegrationTest): it
          // spins up a real OpenProject via docker-compose/Testcontainers, which needs a
          // Docker daemon this pod's maven container doesn't have (only 'buildah', below,
          // runs privileged). Upstream's own CI hits the same wall and skips tests
          // entirely for releases (-DskipTests in .github/workflows/release-docker.yml);
          // excluding just this one tag keeps the rest of the suite (incl. our new
          // workPackageAddComment validation test) running for real in CI.
          sh 'mvn -B -ntp clean verify -DexcludedGroups=integration_remote'
        }
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
        }
      }
    }

    stage('Lint Dockerfile') {
      steps {
        catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
          container('hadolint') {
            sh 'hadolint docker/Dockerfile'
          }
        }
      }
    }

    // Build & push to Harbor. Deployment branch ONLY: main -> prod.
    //
    //   registry.openfreightone.com/platform/openproject-mcp
    //     :<pom-version>-<short-sha>   immutable, one per commit
    //     :prod                        moving pointer (main only)
    //
    // PREREQUISITES (already in place, shared with beehive/BeeCO-CoreCO-Management-Service):
    //   * Harbor project 'platform' exists.
    //   * Harbor robot 'robot$jenkins' has PUSH permission on 'platform'.
    //   * Jenkins 'Secret text' credential id 'harbor-registry' = that robot's token.
    stage('Build & Push Image') {
      when {
        branch 'main'
      }
      environment {
        REGISTRY          = 'registry.openfreightone.com'
        IMAGE             = 'platform/openproject-mcp'
        HARBOR_ROBOT_USER = 'robot$jenkins'
      }
      steps {
        script {
          def version
          container('maven') {
            version = sh(returnStdout: true,
              script: 'mvn -q -B -ntp help:evaluate -Dexpression=project.version -DforceStdout | tail -n1').trim()
          }
          def shortSha = (env.GIT_COMMIT ?: 'unknown').take(7)
          env.IMAGE_REF     = "${env.REGISTRY}/${env.IMAGE}:${version}-${shortSha}"
          env.IMAGE_REF_ENV = "${env.REGISTRY}/${env.IMAGE}:prod"
          echo "Pushing ${env.IMAGE_REF}  (+ moving tag :prod)"
        }
        container('buildah') {
          withCredentials([string(credentialsId: 'harbor-registry', variable: 'REG_PASS')]) {
            sh '''
              set -eu
              printf 'unqualified-search-registries = ["docker.io"]\\nshort-name-mode = "permissive"\\n' > "$CONTAINERS_REGISTRIES_CONF"
              printf '%s' "$REG_PASS" | buildah login --username "$HARBOR_ROBOT_USER" --password-stdin "$REGISTRY"
              buildah build -f docker/Dockerfile -t "$IMAGE_REF" -t "$IMAGE_REF_ENV" "$WORKSPACE"
              buildah push "$IMAGE_REF"
              buildah push "$IMAGE_REF_ENV"
            '''
          }
        }
      }
    }
  }

  post {
    always {
      echo "Pipeline finished with status: ${currentBuild.currentResult}"
    }
    success {
      echo "CI PASSED on branch ${env.BRANCH_NAME ?: 'n/a'} (#${env.BUILD_NUMBER})."
    }
    failure {
      echo "CI FAILED on branch ${env.BRANCH_NAME ?: 'n/a'} (#${env.BUILD_NUMBER}). Check the stage logs."
    }
  }
}
