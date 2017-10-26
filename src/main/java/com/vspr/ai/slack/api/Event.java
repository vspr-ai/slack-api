package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/27/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEvent.class)
@JsonDeserialize(as = ImmutableEvent.class)
@SlackApiImmutableStyle
public abstract class Event {

  public abstract Optional<String> getChallenge();

  public abstract String getToken();

  @JsonProperty("team_id")
  public abstract Optional<String> getTeamId();

  @JsonProperty("event")
  public abstract Optional<EventType> getEventType();

  @JsonAnyGetter
  public abstract Map<String, Object> getOther();

  public static ImmutableEvent.Builder builder() {
    return ImmutableEvent.builder();
  }

}
