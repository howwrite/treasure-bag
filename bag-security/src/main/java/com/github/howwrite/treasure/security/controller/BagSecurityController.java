package com.github.howwrite.treasure.security.controller;

import com.github.howwrite.treasure.core.Response;
import com.github.howwrite.treasure.security.config.SecuritySwitch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/security")
public class BagSecurityController {

    @GetMapping("/aes-key")
    public Response<Map<String, String>> aesKey() {
        return Response.ok(SecuritySwitch.aesKeyConfig.calValue().getNow());
    }
}
