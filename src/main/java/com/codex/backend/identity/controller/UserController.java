package com.codex.backend.identity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codex.backend.system.api.ApiPaths;
import com.codex.backend.system.api.ApiVersion;


@Controller
@RequestMapping(ApiVersion.V1 + ApiPaths.IDENTIFICATION)
public class UserController {
    @PostMapping("/users")
    public String createUser(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
