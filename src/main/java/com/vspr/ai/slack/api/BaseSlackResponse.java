package com.vspr.ai.slack.api;

import java.util.Optional;

/**
 * Base response for requests to the Slack API.
 */
public abstract class BaseSlackResponse {

  /**
   * Was the request processed correctly?
   */
  public abstract String getOk();

  /**
   * Error message from slack if request failed
   */
  public abstract Optional<String> getError();
}
