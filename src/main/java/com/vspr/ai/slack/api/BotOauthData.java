package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableBotOauthData.class)
@JsonDeserialize(as = ImmutableBotOauthData.class)
@SlackApiImmutableStyle
public abstract class BotOauthData {

  @JsonProperty("bot_user_id")
  public abstract String getBotUserId();

  @JsonProperty("bot_access_token")
  public abstract String getBotAccessToken();

  public static ImmutableBotOauthData.Builder builder() {
    return ImmutableBotOauthData.builder();
  }
}
