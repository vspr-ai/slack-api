package com.vspr.ai.slack.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import javax.ws.rs.client.Client;
import org.junit.Test;
import org.sonatype.goodies.testsupport.TestSupport;

public class SlackAPIFactoryTest extends TestSupport {

  @Test
  public void testWithDefaults() {
    SlackAPI slackAPI = SlackAPIFactory.newSlackFactory()
        .setClientId("test-id")
        .setClientSecret("test-secret")
        .build();

    assertThat(slackAPI, notNullValue());
  }

  @Test(expected = NullPointerException.class)
  public void testRequiredFields() {
    SlackAPIFactory.newSlackFactory()
        .build();
  }

  @Test
  public void testCustomValues() {

    SlackAPI slackAPI = SlackAPIFactory.newSlackFactory()
        .setClientId("test-id")
        .setClientSecret("test-secret")
        .setClient(mock(Client.class))
        .setMaxRetries(10)
        .build();
    assertThat(slackAPI, notNullValue());
  }
}