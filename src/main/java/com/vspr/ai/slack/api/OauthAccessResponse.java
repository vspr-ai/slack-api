package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Response Sent by slack when the user grants access.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableOauthAccessResponse.class)
@JsonDeserialize(as = ImmutableOauthAccessResponse.class)
@SlackApiImmutableStyle
public abstract class OauthAccessResponse extends BaseSlackResponse {

  /**
   * The id for the user's team.
   */
  @JsonProperty("team_id")
  public abstract Optional<String> getTeamId();

  /**
   * The name of the team
   */
  @JsonProperty("team_name")
  public abstract Optional<String> getTeamName();

  /**
   * If app asked for bot permissions, return authentication data for bot.
   */
  public abstract Optional<BotOauthData> getBot();

  /**
   * Map for containing json fields not explicitly defined.
   */
  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();

  public static ImmutableOauthAccessResponse.Builder builder() {
    return ImmutableOauthAccessResponse.builder();
  }
}
