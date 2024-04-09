package com.hyewon.springplayground.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SlackClient {
    private static final Slack slack = Slack.getInstance();
    public static final String SLACK_TOKEN = "";
    public static final String SLACK_CHANNEL = "";
    private final MethodsClient methodsClient;

    public SlackClient() {
        this.methodsClient = slack.methods(SLACK_TOKEN);
    }

    public void sendSlack(String message) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(SLACK_CHANNEL)
                .text(message)
                .build();

        try {
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendSlack(String channel, String message) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(channel)
            .text(message)
            .build();

        try {
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendSlack(String channel, String message, String iconEmoji) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel(channel)
            .iconEmoji(iconEmoji)
            .text(message)
            .build();

        try {
            ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
