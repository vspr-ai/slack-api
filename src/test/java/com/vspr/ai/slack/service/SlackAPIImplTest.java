package com.vspr.ai.slack.service;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.vspr.ai.slack.api.Attachment;
import com.vspr.ai.slack.api.ButtonAction;
import com.vspr.ai.slack.api.CreateChannelResponse;
import com.vspr.ai.slack.api.InviteUserToChannelResponse;
import com.vspr.ai.slack.api.ListUsersResponse;
import com.vspr.ai.slack.api.Message;
import com.vspr.ai.slack.api.OauthAccessResponse;
import com.vspr.ai.slack.api.SlackMessageResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.function.Supplier;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

/**
 * Created by cobb on 7/18/17.
 */
public class SlackAPIImplTest {

  private ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());
  private SlackAPIImpl underTest;

  @Mock
  private Client client;

  @Mock
  private WebTarget webTarget;

  @Mock
  private Invocation.Builder webTargetBuilder;

  @Captor
  private ArgumentCaptor<Entity<?>> entityArgumentCaptor;

  @Mock
  private SlackMessageResponse postMessageResponse;

  @Mock
  private CreateChannelResponse createChannelResponse;

  @Mock
  private ListUsersResponse listUsersResponse;

  @Mock
  private Supplier<SlackMessageResponse> slackMessageResponseSupplier;

  @Mock
  private Response responseMock;

  @Mock
  private StatusType statusType;

  @Mock
  private InviteUserToChannelResponse inviteUserToChannelResponse;

  @Mock
  private OauthAccessResponse oauthAccessResponse;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    when(client.register(any(JacksonJaxbJsonProvider.class))).thenReturn(client);
    underTest = new SlackAPIImpl("test", "secret-test", client, 0);
  }

  @Test
  public void testOauthAccess() {
    configureOauthAccess();
    assertThat(underTest.getAccess("test"), is(oauthAccessResponse));
  }

  private void configureOauthAccess() {
    when(client.target(underTest.oauthAccessUri())).thenReturn(webTarget);
    when(webTarget.request()).thenReturn(webTargetBuilder);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(OauthAccessResponse.class)))
        .thenReturn(oauthAccessResponse);
  }

  @Test
  public void testListUsers() {
    configureListUser();
    assertThat(underTest.listUsers("test", "", 200, false), is(listUsersResponse));
  }

  private void configureListUser() {
    when(client.target(underTest.listUsersUri())).thenReturn(webTarget);
    when(webTarget.request()).thenReturn(webTargetBuilder);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(ListUsersResponse.class)))
        .thenReturn(listUsersResponse);
  }

  @Test
  public void postMessage() throws Exception {
    configurePostMessage();
    Message test = Message.builder()
        .setToken("test")
        .setText("hello world")
        .setChannel("@dg")
        .setAsUser(true)
        .build();

    assertThat(underTest.postMessage(test), is(postMessageResponse));
    assertThat(entityArgumentCaptor.getValue(), notNullValue());
  }

  @Test
  public void createChannel() {
    configureCreateChannel();
    assertThat(underTest.createChannel("test", "token"), is(createChannelResponse));
    assertThat(entityArgumentCaptor.getValue(), notNullValue());
  }

  private void configureCreateChannel() {
    when(client.target(underTest.createChannelUri())).thenReturn(webTarget);
    when(webTarget.request()).thenReturn(webTargetBuilder);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(CreateChannelResponse.class)))
        .thenReturn(createChannelResponse);
  }

  @Test
  public void inviteToChannel() {
    configureInviteToChannel();
    assertThat(underTest.inviteToChannel("userId", "channelId", "token"),
        is(inviteUserToChannelResponse));
    final Entity<?> entity = entityArgumentCaptor.getValue();
    assertThat(entity, notNullValue());

    MultivaluedHashMap values = (MultivaluedHashMap) entity.getEntity();

    assertThat(values.get("channel").get(0), is("channelId"));
    assertThat(values.get("token").get(0), is("token"));
    assertThat(values.get("user").get(0), is("userId"));
  }

  private void configureInviteToChannel() {
    when(client.target(underTest.inviteToChannelUri())).thenReturn(webTarget);
    when(webTarget.request()).thenReturn(webTargetBuilder);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(InviteUserToChannelResponse.class)))
        .thenReturn(inviteUserToChannelResponse);
  }

  private void configurePostMessage() {
    when(client.target(underTest.postMessageUri())).thenReturn(webTarget);
    when(webTarget.request()).thenReturn(webTargetBuilder);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(SlackMessageResponse.class)))
        .thenReturn(postMessageResponse);
  }

  @Test
  public void toMap() throws JsonProcessingException {

    Attachment attachment = Attachment.builder()
        .setText("How well does it address the problem?")
        .setFallback("dang")
        .setCallbackId(UUID.randomUUID().toString())
        .addActions(ButtonAction.builder()
            .setName("Say Hello")
            .setText("Hello")
            .setValue("Hello")
            .build())
        .build();

    Message message = Message.builder()
        .setToken("test")
        .setChannel("@cobb")
        .setText("hello")
        .setAsUser(true)
        .addAttachments(attachment)
        .build();

    MultivaluedMap<String, String> actual = underTest.toMap(message);

    assertThat(actual.getFirst("token"), is("test"));
    assertThat(actual.getFirst("channel"), is("@cobb"));
    assertThat(actual.getFirst("text"), is("hello"));
    assertThat(actual.getFirst("as_user"), is("true"));
    assertThat(actual.getFirst("attachments"), is(mapper.writeValueAsString(
        singletonList(attachment))));
  }

  @Test
  public void postMessageUri() throws MalformedURLException, URISyntaxException {
    assertThat(underTest.postMessageUri().toString(),
        is(new URI("https://slack.com/api/chat.postMessage").toString()));
  }

  @Test
  public void testListUsersUri() throws MalformedURLException, URISyntaxException {
    assertThat(underTest.listUsersUri().toString(),
        is(new URI("https://slack.com/api/users.list").toString()));
  }

  @Test
  public void testLisOauthAccessUri() throws MalformedURLException, URISyntaxException {
    assertThat(underTest.oauthAccessUri().toString(),
        is(new URI("https://slack.com/api/oauth.access").toString()));
  }

  @Test
  public void testRetry() {

    underTest = new SlackAPIImpl("test", "secret-test", client, 5);
    when(responseMock.getStatus()).thenReturn(429);
    when(responseMock.getStatusInfo()).thenReturn(statusType);
    when(responseMock.getHeaderString("Retry-After")).thenReturn("1");

    doThrow(new WebApplicationException(responseMock)).doAnswer(
        invocationOnMock -> postMessageResponse).when(slackMessageResponseSupplier).get();

    assertThat(underTest.rateLimitAwareRequest(slackMessageResponseSupplier),
        is(postMessageResponse));
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceedMaxRetries() {
    when(responseMock.getStatus()).thenReturn(429);
    when(responseMock.getStatusInfo()).thenReturn(statusType);
    when(responseMock.getHeaderString("Retry-After")).thenReturn("1");

    doThrow(new WebApplicationException(responseMock))
        .doAnswer(invocationOnMock -> postMessageResponse).when(slackMessageResponseSupplier).get();

    underTest.rateLimitAwareRequest(slackMessageResponseSupplier);
  }

  @Test(expected = WebApplicationException.class)
  public void differentStatusIsRethrown() {
    when(responseMock.getStatus()).thenReturn(401);
    when(responseMock.getStatusInfo()).thenReturn(statusType);
    when(responseMock.getHeaderString("Retry-After")).thenReturn("1");

    doThrow(new WebApplicationException(responseMock))
        .doAnswer(invocationOnMock -> postMessageResponse).when(slackMessageResponseSupplier).get();

    underTest.rateLimitAwareRequest(slackMessageResponseSupplier);
  }

  @Test
  public void testSendToResponseUrl() throws URISyntaxException {
    configurePostMessage();
    Message test = Message.builder()
        .setToken("test")
        .setText("hello world")
        .setChannel("@dg")
        .setAsUser(true)
        .build();

    final URI uri = new URI("https://hooks.slack.com/commands/1234/5678");
    when(client.target(uri)).thenReturn(webTarget);
    when(webTargetBuilder
        .post(entityArgumentCaptor.capture(), eq(Response.class)))
        .thenReturn(Response.ok().build());

    underTest.postMessageToResponseUrl(test, uri);

    assertThat(entityArgumentCaptor.getValue(), notNullValue());
  }
}