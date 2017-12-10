package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class OauthAccessResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testGoodResponse() {
    OauthAccessResponse oauthAccessResponse = loadFixture("/fixtures/OauthAccessResponse.json",
        OauthAccessResponse.class);

    assertThat(oauthAccessResponse, notNullValue());
    assertThat(oauthAccessResponse.getBot().isPresent(), is(true));
    assertThat(oauthAccessResponse.getOk(), is("true"));
  }
}