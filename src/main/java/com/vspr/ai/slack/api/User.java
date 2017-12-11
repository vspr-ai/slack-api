package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUser.class)
@JsonDeserialize(as = ImmutableUser.class)
@SlackApiImmutableStyle
public abstract class User {

  public abstract String getId();

  public abstract String getName();

  @JsonProperty("is_bot")
  public abstract Optional<Boolean> isBot();

  public abstract Profile getProfile();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
