package de.tklein.tklab.openproject.mcp.security;

/**
 * Used as the cache entry for auth token validation.
 *
 * @param href from OpenProject user link
 * @param name from OpenProject user link
 */
public record ValidatedOpenProjectUser(String href, String name) {

}
