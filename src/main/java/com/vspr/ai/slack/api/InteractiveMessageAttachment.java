package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

/**
 * Created by cobb on 7/17/17.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableInteractiveMessageAttachment.class)
@JsonDeserialize(as = ImmutableInteractiveMessageAttachment.class)
@SlackApiImmutableStyle
public abstract class InteractiveMessageAttachment extends Attachment {

  public abstract Optional<String> getText();

  public abstract String getFallback();

  @JsonProperty("callback_id")
  public abstract String getCallbackId();

  public abstract List<Action> getActions();

  @Default
  public String getAttachmentType() {
    return "default";
  }

  public static ImmutableInteractiveMessageAttachment.Builder builder() {
    return ImmutableInteractiveMessageAttachment.builder();
  }

}
