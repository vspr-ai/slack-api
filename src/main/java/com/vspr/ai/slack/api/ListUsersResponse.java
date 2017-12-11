package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Response to {@link com.vspr.ai.slack.service.SlackAPI#listUsers(String, String, int, boolean)}
 */
@Value.Immutable
@JsonSerialize(as = ImmutableListUsersResponse.class)
@JsonDeserialize(as = ImmutableListUsersResponse.class)
@SlackApiImmutableStyle
public abstract class ListUsersResponse extends BaseSlackResponse {

  /**
   * List of users returned in the query
   */
  public abstract List<User> getMembers();

  /**
   * Response Metadata contains information about response pagination
   */
  @JsonProperty("response_metadata")
  public abstract Optional<ResponseMetadata> getResponseMetadata();

  /**
   * Contains all json data not explicitly found in this DTO.
   */
  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();
}
