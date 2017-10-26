package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Optional;

/**
 * Created by cobb on 7/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImmutableButtonAction.class, name = "button"),
    @JsonSubTypes.Type(value = ImmutableSelectAction.class, name = "select")
})
public abstract class Action {

  public abstract String getName();

  public abstract Optional<String> getText();

  public abstract String getType();

  public abstract Optional<String> getValue();
}
