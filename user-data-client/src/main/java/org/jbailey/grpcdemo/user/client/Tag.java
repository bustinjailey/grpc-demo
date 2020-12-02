package org.jbailey.grpcdemo.user.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record Tag(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
}