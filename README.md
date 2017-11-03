## Building

`gradle build`

## TODO

* [x] Improve readme with usage examples
* [x] Set up push to maven central
* [x] Factory for building API
* [ ] Slash commands, event subscription

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