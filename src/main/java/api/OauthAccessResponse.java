package api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableOauthAccessResponse.class)
@JsonDeserialize(as = ImmutableOauthAccessResponse.class)
@SlackApiImmutableStyle
public abstract class OauthAccessResponse extends BaseSlackResponse {

  @JsonProperty("team_id")
  public abstract Optional<String> getTeamId();

  @JsonProperty("team_name")
  public abstract Optional<String> getTeamName();

  public abstract Optional<BotOauthData> getBot();

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();

  public static ImmutableOauthAccessResponse.Builder builder() {
    return ImmutableOauthAccessResponse.builder();
  }
}
