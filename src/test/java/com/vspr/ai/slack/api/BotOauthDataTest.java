package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.sonatype.goodies.testsupport.TestSupport;

public class BotOauthDataTest extends TestSupport {

  @Test
  public void builder() throws Exception {
    BotOauthData botOauthData = BotOauthData.builder()
        .setBotAccessToken("testToken")
        .setBotUserId("testUserId")
        .build();

    assertThat(botOauthData, notNullValue());
  }

}