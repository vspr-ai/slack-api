package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePinRequest.class)
@JsonDeserialize(as = ImmutablePinRequest.class)
@SlackApiImmutableStyle
public abstract class PinRequest {

  @JsonIgnore
  public abstract String getToken();

  public abstract String getChannel();

  public abstract Optional<String> getFile();

  @JsonProperty("file_comment")
  public abstract Optional<String> getFileComment();

  public abstract Optional<String> getTimestamp();

  public static ImmutablePinRequest.Builder builder() {
    return ImmutablePinRequest.builder();
  }
}
