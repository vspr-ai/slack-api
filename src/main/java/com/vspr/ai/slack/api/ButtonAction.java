package com.vspr.ai.slack.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/17/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableButtonAction.class)
@JsonDeserialize(as = ImmutableButtonAction.class)
@SlackApiImmutableStyle
public abstract class ButtonAction extends Action {

  public String getType() {
    return "button";
  }

  public static ImmutableButtonAction.Builder builder() {
    return ImmutableButtonAction.builder();
  }
}
