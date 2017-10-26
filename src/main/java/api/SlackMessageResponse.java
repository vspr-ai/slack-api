package api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/18/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableSlackMessageResponse.class)
@JsonDeserialize(as = ImmutableSlackMessageResponse.class)
@SlackApiImmutableStyle
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SlackMessageResponse extends BaseSlackResponse {

  public abstract Optional<String> getError();

  public abstract Optional<String> getChannel();

  public abstract Optional<String> getTs();

  public abstract Map<String, Object> getMessage();
}
