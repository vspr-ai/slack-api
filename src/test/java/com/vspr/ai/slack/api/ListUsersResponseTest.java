package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class ListUsersResponseTest extends SlackApiFixtureBaseTest {

  @Test
  public void testDeserialize() {
    ListUsersResponse listUsersResponse = loadFixture("/fixtures/ListUsers.json",
        ListUsersResponse.class);

    assertThat(listUsersResponse.getMembers(), hasSize(2));
    assertThat(
        listUsersResponse.getResponseMetadata().orElseThrow(IllegalArgumentException::new)
            .getNextCursor(), notNullValue());
  }
}