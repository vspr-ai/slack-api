package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableListUsersResponse.class)
@JsonDeserialize(as = ImmutableListUsersResponse.class)
@SlackApiImmutableStyle
public abstract class ListUsersResponse extends BaseSlackResponse {

  public abstract List<User> getMembers();

  @JsonProperty("response_metadata")
  public abstract ResponseMetadata getResponseMetadata();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
