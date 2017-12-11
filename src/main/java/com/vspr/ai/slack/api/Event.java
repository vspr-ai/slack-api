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

  /**
   * Challenge token provided to slack api
   */
  public abstract Optional<String> getChallenge();

  /**
   * Token for your app, should be valdiated before using even.
   */
  public abstract String getToken();

  /**
   * Team_id sourcing the event.
   */
  @JsonProperty("team_id")
  public abstract Optional<String> getTeamId();

  /**
   * Additional event details
   */
  @JsonProperty("event")
  public abstract Optional<EventType> getEventType();

  @JsonAnyGetter
  public abstract Map<String, Object> getOther();

  public static ImmutableEvent.Builder builder() {
    return ImmutableEvent.builder();
  }

}
