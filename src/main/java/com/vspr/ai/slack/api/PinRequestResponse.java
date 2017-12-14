package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePinRequestResponse.class)
@JsonDeserialize(as = ImmutablePinRequestResponse.class)
@SlackApiImmutableStyle
public abstract class PinRequestResponse extends BaseSlackResponse {

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
