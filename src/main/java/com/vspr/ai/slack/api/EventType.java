package com.vspr.ai.slack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base Type for slack events: https://api.slack.com/events-api Should be deserialized using
 * jackson's polymorphic deserialization
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImmutableMessageImEventType.class, name = "message"),
})
public abstract class EventType {

  /**
   * The type of event, used for polymorphic deserialization
   */
  public abstract String getType();
}
