package com.vspr.ai.slack.api;

import java.util.Optional;
import org.immutables.value.Value.Default;

/**
 * Base response for requests to the Slack API.
 */
public abstract class BaseSlackResponse {

  /**
   * Was the request processed correctly?
   */
  @Default
  public String getOk() {
    return "true";
  }

  /**
   * Error message from slack if request failed
   */
  public abstract Optional<String> getError();
}
