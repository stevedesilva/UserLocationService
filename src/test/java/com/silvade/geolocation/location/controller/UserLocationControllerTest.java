package com.silvade.geolocation.location.controller;

import com.silvade.geolocation.location.gateway.UserLocationResponse;
import com.silvade.geolocation.location.service.UserLocationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UserLocationControllerTest {

    public static final int ONCE = 1;

    private UserLocationService userLocationService;

    private UserLocationController userLocationController;

    @Before
    public void setup() {
        userLocationService = mock(UserLocationService.class);
        userLocationController = new UserLocationController(userLocationService);
    }

    @Test
    public void getUsersConnectedWithLondon() {
        // GIVEN
        when(userLocationService.getUsersConnectedWithLondon()).thenReturn(createResponse());

        // WHEN
        userLocationController.getUsersConnectedWithLondon();

        // THEN
        verify(userLocationService, Mockito.times(ONCE)).getUsersConnectedWithLondon();
    }

    private UserLocationResponse createResponse() {
        return UserLocationResponse.builder().build();
    }
}