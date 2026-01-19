package de.tklein.tklab.openproject.mcp.openproject.client;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
@RequiredArgsConstructor
public class OpenProjectTemplateRenderer {

  private final Configuration freemarkerConfig;

  public String render(String templateName, Map<String, ?> model) {
    try {
      Template template = freemarkerConfig.getTemplate(templateName);
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    } catch (IOException | TemplateException e) {
      throw new InternalError("Failed to render template " + templateName + ": " + e.getMessage(),
          e);
    }
  }
}