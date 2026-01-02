package de.tklein.tklab.openproject.mcp.openproject.client;

import java.net.URI;

public record OpenProjectConnection(URI baseUrl, String bearerToken) {

}
