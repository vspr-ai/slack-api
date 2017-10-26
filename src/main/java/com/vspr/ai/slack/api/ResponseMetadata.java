package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableResponseMetadata.class)
@JsonDeserialize(as = ImmutableResponseMetadata.class)
@SlackApiImmutableStyle
public abstract class ResponseMetadata {

  @JsonProperty("next_cursor")
  public abstract String getNextCursor();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
