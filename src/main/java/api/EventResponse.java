package api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/27/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableEventResponse.class)
@JsonDeserialize(as = ImmutableEventResponse.class)
@SlackApiImmutableStyle
public abstract class EventResponse {

  public abstract Optional<String> getChallenge();

  @JsonAnyGetter
  public abstract Map<String, Object> getOther();

  public static ImmutableEventResponse.Builder builder() {
    return ImmutableEventResponse.builder();
  }
}
