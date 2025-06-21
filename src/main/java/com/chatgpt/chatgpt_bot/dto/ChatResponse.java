package com.chatgpt.chatgpt_bot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    @Schema(description = "User reply to send to ChatGPT", example = "Tell me a joke.")
    private String reply;
}
