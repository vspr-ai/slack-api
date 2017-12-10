package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class CreateChannelResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void typicalSuccess() throws Exception {
    CreateChannelResponse createChannelResponse = loadFixture(
        "/fixtures/CreateChannelResponse.json", CreateChannelResponse.class);

    assertThat(createChannelResponse, notNullValue());
    assertThat(createChannelResponse.getChannel().isPresent(), is(true));
  }

  @Test
  public void errorCase() throws Exception {
    CreateChannelResponse createChannelResponse = loadFixture(
        "/fixtures/CreateChannelBadResponse.json", CreateChannelResponse.class);

    assertThat(createChannelResponse, notNullValue());
    assertThat(createChannelResponse.getChannel().isPresent(), is(false));
    assertThat(createChannelResponse.getOk(), is("false"));
  }
}