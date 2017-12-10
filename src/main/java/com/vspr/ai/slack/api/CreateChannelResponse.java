package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Response from a request to create a channel, see {@link com.vspr.ai.slack.service.SlackAPI#createChannel(String,
 * String)}
 */
@Value.Immutable
@JsonSerialize(as = ImmutableCreateChannelResponse.class)
@JsonDeserialize(as = ImmutableCreateChannelResponse.class)
@SlackApiImmutableStyle
public abstract class CreateChannelResponse extends BaseSlackResponse {

  /**
   * Get the channel, will be {@link Optional#empty()} if request failed.
   */
  public abstract Optional<Channel> getChannel();

  /**
   * Map for containing json fields not explicitly defined.
   */
  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
