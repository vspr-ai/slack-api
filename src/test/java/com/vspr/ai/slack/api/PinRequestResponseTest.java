package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class PinRequestResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testDeserialize() {
    PinRequestResponse pinRequestResponse = loadFixture("/fixtures/PinRequestResponse.json",
        PinRequestResponse.class);
    assertThat(pinRequestResponse.getOk(), is("true"));
  }
}