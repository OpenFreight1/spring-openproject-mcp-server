package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.AttachmentDto;
import de.tklein.tklab.openproject.mcp.dto.WikiPageDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import jakarta.validation.constraints.NotNull;
import java.util.Base64;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpTool.McpAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 *
 * OpenProject's APIv3 only exposes a wiki page's id/title — there is no endpoint to read or
 * write the page's actual text content, only through OpenProject's own UI. The only other
 * capability the API exposes for wiki pages is attaching files.
 */
@Log4j2
@Validated
@Component
public class WikiPageTools {

  private final OpenProjectApiClient openProjectApiClient;

  public WikiPageTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "Fetches a wiki page's id and title by id. Note: OpenProject's API does not expose the page's text content, only its title.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public WikiPageDto wikiPageShow(@NotNull Integer wikiPageId) {
    return openProjectApiClient.wikiPageShow(wikiPageId);
  }

  @McpTool(
      description = "Lists attachments on a wiki page.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<AttachmentDto> wikiPageAttachments(@NotNull Integer wikiPageId) {
    return openProjectApiClient.wikiPageAttachmentList(wikiPageId);
  }

  @McpTool(
      description = "Uploads an attachment to a wiki page. Provide wikiPageId, fileName and base64-encoded fileContent. Optionally provide fileContentType.")
  public Integer wikiPageUploadAttachment(@NotNull Integer wikiPageId, @NotNull String fileName,
      @NotNull String fileContentBase64, @NotNull String fileContentType) {
    byte[] bytes;
    try {
      bytes = Base64.getDecoder().decode(fileContentBase64);
    } catch (IllegalArgumentException _) {
      throw new IllegalArgumentException("'fileContentBase64' must be valid Base64");
    }
    return openProjectApiClient.wikiPageUploadAttachment(wikiPageId, fileName, bytes,
        fileContentType);
  }

}
