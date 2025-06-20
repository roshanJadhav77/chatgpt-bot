package com.chatgpt.chatgpt_bot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PromptRequest {
    @Schema(description = "User prompt to send to ChatGPT", example = "Tell me a joke.")
    private String prompt;
    @Schema(description = "User model", example = "llama3.2")
    private String model = "llama3"; // default

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
