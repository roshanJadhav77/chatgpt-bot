package com.chatgpt.chatgpt_bot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatResponse {
    @Schema(description = "User reply to send to ChatGPT", example = "Tell me a joke.")
    private String reply;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
