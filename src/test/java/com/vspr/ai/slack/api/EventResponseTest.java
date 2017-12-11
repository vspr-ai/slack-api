package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.sonatype.goodies.testsupport.TestSupport;

public class EventResponseTest extends TestSupport {

  @Test
  public void builder() throws Exception {
    EventResponse eventResponse = EventResponse.builder()
        .setChallenge("test")
        .build();

    assertThat(eventResponse.getChallenge().isPresent(), is(true));
  }

}