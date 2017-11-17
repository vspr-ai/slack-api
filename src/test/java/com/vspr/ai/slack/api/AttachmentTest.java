package com.vspr.ai.slack.api;

import org.junit.Test;
import org.sonatype.goodies.testsupport.TestSupport;

public class AttachmentTest extends TestSupport {

  @Test
  public void testActionsOptional() {
    Attachment.builder()
        .setFallback("test")
        .setCallbackId("test2")
        .build();
  }
}