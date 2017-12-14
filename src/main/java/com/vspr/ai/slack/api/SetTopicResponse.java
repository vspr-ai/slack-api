package com.vspr.ai.slack.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSetTopicResponse.class)
@JsonDeserialize(as = ImmutableSetTopicResponse.class)
@SlackApiImmutableStyle
public abstract class SetTopicResponse extends BaseSlackResponse {

  /**
   * The topic set. Will be {@link Optional#empty()} if an error occurred.
   */
  public abstract Optional<String> getTopic();
}
