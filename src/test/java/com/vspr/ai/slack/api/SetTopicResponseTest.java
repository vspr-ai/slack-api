package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SetTopicResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testDeserialize() {
    SetTopicResponse setTopicResponse = loadFixture("/fixtures/SetTopicResponse.json",
        SetTopicResponse.class);

    assertThat(setTopicResponse.getTopic().isPresent(), is(true));
  }
}