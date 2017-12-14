package com.vspr.ai.slack.service;

import com.vspr.ai.slack.api.CreateChannelResponse;
import com.vspr.ai.slack.api.InviteUserToChannelResponse;
import com.vspr.ai.slack.api.ListUsersResponse;
import com.vspr.ai.slack.api.Message;
import com.vspr.ai.slack.api.OauthAccessResponse;
import com.vspr.ai.slack.api.PinRequest;
import com.vspr.ai.slack.api.PinRequestResponse;
import com.vspr.ai.slack.api.SetPurposeResponse;
import com.vspr.ai.slack.api.SetTopicResponse;
import com.vspr.ai.slack.api.SlackMessageResponse;
import java.net.URI;
import javax.annotation.Nullable;

/**
 * Created by cobb on 7/18/17.
 */
public interface SlackAPI {

  /**
   * Post the specified message to the slack web com.vspr.ai.slack.api
   */
  SlackMessageResponse postMessage(Message message);

  /**
   * Post the specified message to the slack web com.vspr.ai.slack.api
   */
  void postMessageToResponseUrl(Message message, URI uri);

  /**
   * Get a list of Users from the slack API.
   */
  ListUsersResponse listUsers(String token, @Nullable String cursor, int limit, boolean presence);

  /**
   * Create a new slack channel
   */
  CreateChannelResponse createChannel(String name, String userAuthToken);


  /**
   * Invite a user to the specified channel
   */
  InviteUserToChannelResponse inviteToChannel(String userId, String channelId,
      String userAuthToken);

  /**
   * Set a channel's topic
   */
  SetTopicResponse setTopic(String topic, String channelId, String token);

  /**
   * Set A channel's purpose
   */
  SetPurposeResponse setPurpose(String purpose, String channelId, String token);

  /**
   * Pin something in a channel
   */
  PinRequestResponse pin(PinRequest pinRequest);

  /**
   * Request Access via the Slack Oauth flow.
   */
  OauthAccessResponse getAccess(String code);

}
