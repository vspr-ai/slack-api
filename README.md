## Building

`gradle build`

## TODO

* [ ] Improve readme with usage examples
* [x] Set up push to maven central
* [ ] Factory for building API
* [ ] Usage examples
* [ ] Slash commands, event subscription

## Usage
First, create an instance of the slack API with the `SlackAPIFactory`:

```java
    SlackAPI slackAPI = SlackAPIFactory.newSlackFactory()
        .setClientId("test-id")
        .setClientSecret("test-secret")
        .build();
```
