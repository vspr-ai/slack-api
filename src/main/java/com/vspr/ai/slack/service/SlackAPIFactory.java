package com.vspr.ai.slack.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.ws.rs.client.Client;
import org.glassfish.jersey.client.JerseyClientBuilder;

public class SlackAPIFactory {

  private String clientId;

  private String clientSecret;

  private Client client;

  private int maxRetries;

  public SlackAPI build() {
    checkNotNull(clientId, "Client ID must not be null");
    checkNotNull(clientSecret, "Client secret must not be null");
    checkNotNull(client, "Client must not be null");
    return new SlackAPIImpl(clientId, clientSecret, client, maxRetries);
  }

  public SlackAPIFactory setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public SlackAPIFactory setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
    return this;
  }

  public SlackAPIFactory setClient(Client client) {
    this.client = client;
    return this;
  }

  public SlackAPIFactory setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public static SlackAPIFactory newSlackFactory() {
    return new SlackAPIFactory()
        .setClient(new JerseyClientBuilder().build())
        .setMaxRetries(5);
  }
}
