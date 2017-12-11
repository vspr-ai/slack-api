# Slack API
[![Build Status](https://travis-ci.org/vspr-ai/slack-api.svg?branch=master)](https://travis-ci.org/vspr-ai/slack-api) [![codecov](https://codecov.io/gh/vspr-ai/slack-api/branch/master/graph/badge.svg)](https://codecov.io/gh/vspr-ai/slack-api)

## Building

`gradle build`


## Usage
First, create an instance of the slack API with the `SlackAPIFactory`:

```java
    SlackAPI slackAPI = SlackAPIFactory.newSlackFactory()
        .setClientId("test-id")
        .setClientSecret("test-secret")
        .build();
```

Then, post a message to channel:

```java
    Message test = Message.builder()
        .setToken("test")
        .setText("hello world")
        .setChannel("@dg")
        .setAsUser(true)
        .build();

    underTest.postMessage(test);
```
