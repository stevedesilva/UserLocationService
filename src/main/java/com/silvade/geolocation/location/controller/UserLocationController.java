package com.silvade.geolocation.location.controller;

import com.silvade.geolocation.location.gateway.UserLocationResponse;
import com.silvade.geolocation.location.service.UserLocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLocationController {

    private final UserLocationService userLocationService;

    public UserLocationController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @GetMapping(path="/users", produces = "application/json")
    public UserLocationResponse getUsersConnectedWithLondon()
    {
        return userLocationService.getUsersConnectedWithLondon();
    }

}

