package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

/**
 * Created by cobb on 7/17/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableMessage.class)
@JsonDeserialize(as = ImmutableMessage.class)
@SlackApiImmutableStyle
public abstract class Message {

  public abstract Optional<String> getToken();

  public abstract Optional<String> getChannel();

  public abstract String getText();

  @Default
  public boolean getAsUser() {
    return true;
  }

  public abstract List<Attachment> getAttachments();

  @JsonProperty("thread_ts")
  public abstract Optional<String> getThreadTs();

  public static ImmutableMessage.Builder builder() {
    return ImmutableMessage.builder();
  }

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
