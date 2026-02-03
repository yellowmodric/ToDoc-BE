package com.solinone.todoc.font.presentation;

import com.solinone.todoc.font.application.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiTestControlloer {

    private final AiChatService aiChatService;

    @GetMapping("/test")
    public String test(@RequestParam String message) {
        return aiChatService.chat(message);
    }
}
