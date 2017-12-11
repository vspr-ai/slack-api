package com.vspr.ai.slack.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import org.sonatype.goodies.testsupport.TestSupport;

abstract class SlackApiFixtureBaseTest extends TestSupport {

  static final ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

  <T> T loadFixture(String fixture, Class<T> fixtureClazz) {
    try {
      return mapper.readValue(getClass().getResourceAsStream(fixture), fixtureClazz);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
