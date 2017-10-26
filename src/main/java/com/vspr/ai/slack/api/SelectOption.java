package com.vspr.ai.slack.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSelectOption.class)
@JsonDeserialize(as = ImmutableSelectOption.class)
@SlackApiImmutableStyle
public abstract class SelectOption {

  public abstract Optional<String> getText();

  public abstract String getValue();

  public static ImmutableSelectOption.Builder builder() {
    return ImmutableSelectOption.builder();
  }
}
