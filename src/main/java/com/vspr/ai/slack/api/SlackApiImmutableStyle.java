package com.vspr.ai.slack.api;

import org.immutables.value.Value;

@Value.Style(
    get = {"is*", "get*"},
    init = "set*",
    jdkOnly = true
)
public @interface SlackApiImmutableStyle {

}
