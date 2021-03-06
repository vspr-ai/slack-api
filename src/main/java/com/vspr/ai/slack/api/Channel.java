package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/18/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableChannel.class)
@JsonDeserialize(as = ImmutableChannel.class)
@SlackApiImmutableStyle
public abstract class Channel {

  public abstract String getId();

  public abstract String getName();

  public static ImmutableChannel.Builder builder() {
    return ImmutableChannel.builder();
  }

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
