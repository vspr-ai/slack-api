package com.vspr.ai.slack.service;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.StringEscapeUtils.unescapeXml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.annotations.VisibleForTesting;
import com.vspr.ai.slack.api.ListUsersResponse;
import com.vspr.ai.slack.api.Message;
import com.vspr.ai.slack.api.OauthAccessResponse;
import com.vspr.ai.slack.api.SlackMessageResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cobb on 7/18/17.
 */
public class SlackAPIImpl implements SlackAPI {

  private static final Logger logger = LoggerFactory.getLogger(SlackAPIImpl.class);
  private static ObjectMapper MAPPER = new ObjectMapper().registerModule(new Jdk8Module());
  private static String SLACK_BASE_WEB_API = "https://slack.com/api";

  private static final String POST_MESSAGE = "/chat.postMessage";
  private static final String LIST_USERS = "users.list";
  private static final String OAUTH_ACCESS = "oauth.access";

  private final Client client;
  private final String clientId;
  private final String clientSecret;

  public SlackAPIImpl(String clientId,
      String clientSecret,
      Client client) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;

    JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
    jacksonProvider.setMapper(MAPPER);
    this.client = client.register(jacksonProvider);
  }

  @Override
  public SlackMessageResponse postMessage(Message message) {
    return rateLimitAwareRequest(() -> client.target(postMessageUri())
        .request()
        .post(Entity.entity(toMap(message), APPLICATION_FORM_URLENCODED),
            SlackMessageResponse.class));
  }

  @Override
  public void postMessageToResponseUrl(Message message, URI uri) {
    rateLimitAwareRequest(() -> client.target(uri)
        .request()
        .post(Entity.entity(message, APPLICATION_JSON),
            Response.class));
  }

  @Override
  public ListUsersResponse listUsers(String token, @Nullable String cursor, int limit,
      boolean presence) {
    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("token", token);
    ofNullable(cursor).ifPresent(cursorValue -> requestMap.putSingle("cursor", cursorValue));
    requestMap.putSingle("limit", Integer.toString(limit));
    requestMap.putSingle("presence", Boolean.toString(presence));

    return rateLimitAwareRequest(() -> client.target(listUsersUri())
        .request()
        .post(Entity.entity(requestMap, APPLICATION_FORM_URLENCODED),
            ListUsersResponse.class));
  }

  @Override
  public OauthAccessResponse getAccess(String code) {
    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("code", code);
    requestMap.putSingle("client_id", clientId);
    requestMap.putSingle("client_secret", clientSecret);

    return rateLimitAwareRequest(() -> client.target(oauthAccessUri())
        .request()
        .post(Entity.entity(requestMap, APPLICATION_FORM_URLENCODED),
            OauthAccessResponse.class));
  }

  @VisibleForTesting
  MultivaluedMap<String, String> toMap(Message message) {
    MultivaluedMap<String, String> retValue = new MultivaluedHashMap<>();

    message.getToken().ifPresent((token) -> retValue.putSingle("token", token));
    message.getChannel().ifPresent((channel) -> retValue.putSingle("channel", channel));
    retValue.putSingle("text", unescapeXml(message.getText()));
    retValue.putSingle("as_user", Boolean.toString(message.getAsUser()));
    message.getThreadTs().ifPresent(threadTs -> retValue.putSingle("thread_ts", threadTs));
    if (message.getAttachments().size() > 0) {
      try {
        retValue.putSingle("attachments", MAPPER.writeValueAsString(message.getAttachments()));
      } catch (JsonProcessingException e) {
        throw new IllegalArgumentException(e);
      }
    }

    return retValue;
  }

  @VisibleForTesting
  URI postMessageUri() {
    try {
      return UriBuilder.fromUri(new URI(SLACK_BASE_WEB_API))
          .path(POST_MESSAGE)
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @VisibleForTesting
  URI listUsersUri() {
    try {
      return UriBuilder.fromUri(new URI(SLACK_BASE_WEB_API))
          .path(LIST_USERS)
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @VisibleForTesting
  URI oauthAccessUri() {
    try {
      return UriBuilder.fromUri(new URI(SLACK_BASE_WEB_API))
          .path(OAUTH_ACCESS)
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @VisibleForTesting
  <T> T rateLimitAwareRequest(Supplier<T> supplier) {
    int counter = 0;
    int maxRetries = 5;
    do {
      try {
        return supplier.get();
      } catch (WebApplicationException e) {

        Response response = e.getResponse();
        if (response.getStatus() != 429) {
          throw (e);
        }
        logger.warn("An 429 was received from slack. I will retry.", e);
        try {
          int retrySeconds = parseInt(response.getHeaderString("Retry-After"));
          logger.warn("Retrying after {} seconds", retrySeconds);
          sleep(retrySeconds * 1000);
        } catch (InterruptedException e1) {
          throw new IllegalArgumentException(e);
        }
      }
      counter++;
    } while (counter <= maxRetries);
    logger.error("Unable to finish request after {} retries.", counter);
    throw new IllegalArgumentException("Unable to finish request.");
  }
}
