package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;

public class EventTypeTest extends SlackApiFixtureBaseTest {

  @Test
  public void testPolymorphicDeserialization() {
    EventType eventType = loadFixture("/fixtures/MessageEventType.json", EventType.class);
    assertThat(eventType, instanceOf(MessageImEventType.class));
  }
}