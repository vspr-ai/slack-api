package api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * Created by cobb on 7/28/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableMessageImEventType.class)
@JsonDeserialize(as = ImmutableMessageImEventType.class)
@SlackApiImmutableStyle
public abstract class MessageImEventType extends EventType {

  public static final String EVENT_TYPE = "message";

  public String getType() {
    return EVENT_TYPE;
  }

  public abstract String getChannel();

  public abstract Optional<String> getUser();

  public abstract Optional<String> getText();

  public abstract String getTs();

  @JsonProperty("bot_id")
  public abstract Optional<String> getBotId();

  @JsonProperty("thread_ts")
  public abstract Optional<String> getThreadTs();

  @JsonAnyGetter
  public abstract Map<String, Object> getOther();

  public static ImmutableMessageImEventType.Builder builder() {
    return ImmutableMessageImEventType.builder();
  }
}
