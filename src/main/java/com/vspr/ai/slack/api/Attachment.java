package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

/**
 * Created by cobb on 7/17/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAttachment.class)
@JsonDeserialize(as = ImmutableAttachment.class)
@SlackApiImmutableStyle
public abstract class Attachment {

  public abstract Optional<String> getText();

  public abstract String getFallback();

  @JsonProperty("callback_id")
  public abstract Optional<String> getCallbackId();

  //Using 'nullable' instead of optional so 'addAction' will still be in builder, preventing a breaking a change
  @Nullable
  public abstract List<Action> getActions();

  @Default
  public String getAttachmentType() {
    return "default";
  }

  public static ImmutableAttachment.Builder builder() {
    return ImmutableAttachment.builder();
  }

  @JsonAnyGetter
  @AllowNulls
  public abstract Map<String, Object> getOther();

}
