package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import org.junit.Test;

public class EventTest extends SlackApiFixtureBaseTest {

  @Test
  public void testUrlVerificationEvent() {
    Event event = loadFixture("/fixtures/UrlVerificationEvent.json", Event.class);

    assertThat(event.getChallenge(),
        is(Optional.of("3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P")));
  }

  @Test
  public void testBuilder() {
    Event event = Event.builder()
        .setToken("test-token")
        .build();

    assertThat(event.getToken(), is("test-token"));
  }
}