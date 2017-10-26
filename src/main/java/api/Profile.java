package api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableProfile.class)
@JsonDeserialize(as = ImmutableProfile.class)
@SlackApiImmutableStyle
public abstract class Profile {

  @JsonProperty("first_name")
  public abstract Optional<String> getFirstName();

  @JsonProperty("last_name")
  public abstract Optional<String> getLastName();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();

  public static ImmutableProfile.Builder builder() {
    return ImmutableProfile.builder();
  }
}
