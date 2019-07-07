package com.silvade.geolocation.location.controller;

import com.silvade.geolocation.location.gateway.UserLocationResponse;
import com.silvade.geolocation.location.service.UserLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User Location  API", description = "Operations to get all Users in London and with a 50 mile radius")
@RestController
public class UserLocationController {

    private final UserLocationService userLocationService;

    public UserLocationController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    @ApiOperation(value = "View a list of users connected with London", response = UserLocationResponse.class)
    @GetMapping(path = "/users", produces = "application/json")
    public UserLocationResponse getUsersConnectedWithLondon() {
        return userLocationService.getUsersConnectedWithLondon();
    }

}

