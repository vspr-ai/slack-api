package com.vspr.ai.slack.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSlackAccessCode.class)
@JsonDeserialize(as = ImmutableSlackAccessCode.class)
@SlackApiImmutableStyle
public abstract class SlackAccessCode {

  public abstract String getCode();

  public static ImmutableSlackAccessCode.Builder builder() {
    return ImmutableSlackAccessCode.builder();
  }
}
