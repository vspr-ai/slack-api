package com.vspr.ai.slack.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.sonatype.goodies.testsupport.TestSupport;

public class UserTest extends TestSupport {

  @Test
  public void builder() throws Exception {
    User user = User.builder()
        .setName("cobb")
        .setId("123")
        .setProfile(Profile.builder()
            .setFirstName("Eric")
            .setLastName("Cobb")
            .build())
        .build();

    assertThat(user.getName(), is("cobb"));
  }
}