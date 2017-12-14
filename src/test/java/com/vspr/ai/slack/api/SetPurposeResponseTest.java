package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SetPurposeResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testDeserialization() {
    SetPurposeResponse setPurposeResponse = loadFixture("/fixtures/SetPurposeResponse.json",
        SetPurposeResponse.class);
    assertThat(setPurposeResponse.getPurpose().isPresent(), is(true));
  }
}