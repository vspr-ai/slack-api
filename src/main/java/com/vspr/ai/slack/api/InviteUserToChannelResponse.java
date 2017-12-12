package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableInviteUserToChannelResponse.class)
@JsonDeserialize(as = ImmutableInviteUserToChannelResponse.class)
@SlackApiImmutableStyle
public abstract class InviteUserToChannelResponse extends BaseSlackResponse{

  /**
   * The target channel, will be {@link Optional#empty()} if request failed.
   */
  public abstract Optional<Channel> getChannel();

  /**
   * Map for containing json fields not explicitly defined.
   */
  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
