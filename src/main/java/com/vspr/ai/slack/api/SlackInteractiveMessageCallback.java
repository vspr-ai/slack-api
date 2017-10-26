package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/18/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableSlackInteractiveMessageCallback.class)
@JsonDeserialize(as = ImmutableSlackInteractiveMessageCallback.class)
@SlackApiImmutableStyle
public abstract class SlackInteractiveMessageCallback {

  public abstract List<Action> getActions();

  @JsonProperty("callback_id")
  public abstract String getCallbackId();

  public abstract Channel getChannel();

  public abstract String getToken();

  @JsonProperty("message_ts")
  public abstract String getMessageTs();

  @JsonAnyGetter
  public abstract Map<String, Object> getOther();

  public static ImmutableSlackInteractiveMessageCallback.Builder builder() {
    return ImmutableSlackInteractiveMessageCallback.builder();
  }
}
