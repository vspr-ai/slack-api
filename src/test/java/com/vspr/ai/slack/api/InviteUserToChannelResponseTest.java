package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class InviteUserToChannelResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testDeserialize() {
    InviteUserToChannelResponse inviteUserToChannelResponse = loadFixture(
        "/fixtures/ChannelInviteResponse.json", InviteUserToChannelResponse.class);

    assertThat(inviteUserToChannelResponse, notNullValue());
  }

}