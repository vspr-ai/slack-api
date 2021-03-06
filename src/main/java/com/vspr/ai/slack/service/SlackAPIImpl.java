package com.vspr.ai.slack.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.StringEscapeUtils.unescapeXml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.annotations.VisibleForTesting;
import com.vspr.ai.slack.api.BaseSlackResponse;
import com.vspr.ai.slack.api.CreateChannelResponse;
import com.vspr.ai.slack.api.InviteUserToChannelResponse;
import com.vspr.ai.slack.api.ListUsersResponse;
import com.vspr.ai.slack.api.Message;
import com.vspr.ai.slack.api.OauthAccessResponse;
import com.vspr.ai.slack.api.PinRequest;
import com.vspr.ai.slack.api.PinRequestResponse;
import com.vspr.ai.slack.api.SetPurposeResponse;
import com.vspr.ai.slack.api.SetTopicResponse;
import com.vspr.ai.slack.api.SlackMessageResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
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

  private static final String POST_MESSAGE = "/chat.postMessage";
  private static final String CREATE_CHANNEL = "/channels.create";
  private static final String INVITE_CHANNEL = "/channels.invite";
  private static final String SET_TOPIC = "/channels.setTopic";
  private static final String SET_PURPOSE = "/channels.setPurpose";
  private static final String PINS_ADD = "/pins.add";
  private static final String LIST_USERS = "users.list";
  private static final String OAUTH_ACCESS = "oauth.access";

  private final Client client;
  private final String clientId;
  private final String clientSecret;
  private final int maxRetries;

  SlackAPIImpl(String clientId,
      String clientSecret,
      Client client, int maxRetries) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.maxRetries = maxRetries;

    JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
    jacksonProvider.setMapper(MAPPER);
    this.client = client.register(jacksonProvider);
  }

  @Override
  public SlackMessageResponse postMessage(Message message) {
    return rateLimitAwareRequest(() -> client.target(postMessageUri())
        .request()
        .post(entity(toMap(message), APPLICATION_FORM_URLENCODED),
            SlackMessageResponse.class));
  }

  @Override
  public void postMessageToResponseUrl(Message message, URI uri) {
    rateLimitAwareRequest(() -> client.target(uri)
        .request()
        .post(entity(message, APPLICATION_JSON),
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
        .post(entity(requestMap, APPLICATION_FORM_URLENCODED),
            ListUsersResponse.class));
  }

  @Override
  public CreateChannelResponse createChannel(String name, String userToken) {
    checkNotNull(name, "Name is required to create a channel.");
    checkNotNull(userToken, "User Auth Token is required to create a channel.");

    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("name", name);
    requestMap.putSingle("token", userToken);
    return rateLimitAwareRequest(() -> client.target(createChannelUri())
        .request()
        .post(entity(requestMap, APPLICATION_FORM_URLENCODED),
            CreateChannelResponse.class));
  }

  @Override
  public InviteUserToChannelResponse inviteToChannel(String userId, String channelId,
      String userAuthToken) {
    checkNotNull(userId, "userId is required to create a channel.");
    checkNotNull(channelId, "channelId is required to create a channel.");
    checkNotNull(userAuthToken, "User Token is required to create a channel.");

    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("channel", channelId);
    requestMap.putSingle("user", userId);
    requestMap.putSingle("token", userAuthToken);

    return rateLimitAwareRequest(() -> client.target(inviteToChannelUri())
        .request()
        .post(entity(requestMap, APPLICATION_FORM_URLENCODED),
            InviteUserToChannelResponse.class));
  }

  @Override
  public SetTopicResponse setTopic(String topic, String channelId, String token) {
    checkNotNull(topic, "topic is required to set a topic.");
    return setChannelAttribute("topic", topic, setTopicUri(), channelId, token,
        SetTopicResponse.class);
  }

  private <T extends BaseSlackResponse> T setChannelAttribute(String attributeName,
      String attribute, URI uri, String channelId, String token, Class<T> responseClass) {
    checkNotNull(channelId, "channelId is required to set a topic.");
    checkNotNull(token, "A Token is required to set a topic.");

    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("channel", channelId);
    requestMap.putSingle(attributeName, attribute);
    requestMap.putSingle("token", token);

    return rateLimitAwareRequest(() -> client.target(uri)
        .request()
        .post(entity(requestMap, APPLICATION_FORM_URLENCODED),
            responseClass));
  }

  @Override
  public SetPurposeResponse setPurpose(String purpose, String channelId, String token) {
    checkNotNull(purpose, "purpose is required to set a purpose.");
    return setChannelAttribute("purpose", purpose, setPurposeUri(), channelId, token,
        SetPurposeResponse.class);
  }

  @Override
  public PinRequestResponse pin(PinRequest pinRequest) {
    return rateLimitAwareRequest(() -> client.target(addPinsUri())
        .request()
        .header("Authorization", format("Bearer %s", pinRequest.getToken()))
        .post(entity(pinRequest,
            new MediaType("application", "json", Charset.defaultCharset().name())),
            PinRequestResponse.class));
  }

  @Override
  public OauthAccessResponse getAccess(String code) {
    MultivaluedMap<String, String> requestMap = new MultivaluedHashMap<>();
    requestMap.putSingle("code", code);
    requestMap.putSingle("client_id", clientId);
    requestMap.putSingle("client_secret", clientSecret);

    return rateLimitAwareRequest(() -> client.target(oauthAccessUri())
        .request()
        .post(entity(requestMap, APPLICATION_FORM_URLENCODED),
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

  private URI getUri(String path) {
    try {
      String SLACK_BASE_WEB_API = "https://slack.com/api";
      return UriBuilder.fromUri(new URI(SLACK_BASE_WEB_API))
          .path(path)
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @VisibleForTesting
  URI postMessageUri() {
    return getUri(POST_MESSAGE);
  }

  @VisibleForTesting
  URI createChannelUri() {
    return getUri(CREATE_CHANNEL);
  }

  @VisibleForTesting
  URI inviteToChannelUri() {
    return getUri(INVITE_CHANNEL);
  }

  @VisibleForTesting
  URI listUsersUri() {
    return getUri(LIST_USERS);
  }

  @VisibleForTesting
  URI addPinsUri() {
    return getUri(PINS_ADD);
  }

  @VisibleForTesting
  URI oauthAccessUri() {
    return getUri(OAUTH_ACCESS);
  }

  @VisibleForTesting
  URI setTopicUri() {
    return getUri(SET_TOPIC);
  }

  @VisibleForTesting
  URI setPurposeUri() {
    return getUri(SET_PURPOSE);
  }

  @VisibleForTesting
  <T> T rateLimitAwareRequest(Supplier<T> supplier) {
    int counter = 0;
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
