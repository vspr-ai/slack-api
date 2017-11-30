package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCreateChannelResponse.class)
@JsonDeserialize(as = ImmutableCreateChannelResponse.class)
@SlackApiImmutableStyle
public abstract class CreateChannelResponse extends BaseSlackResponse {
  public abstract Optional<Channel> getChannel();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
