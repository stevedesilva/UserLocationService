package com.silvade.geolocation.location.service;

import com.silvade.geolocation.location.gateway.UserLocationResponse;

public interface UserLocation {

    UserLocationResponse getAllUsers();
    UserLocationResponse getUsersByCity(String city);
    UserLocationResponse getUsersConnectedWithLondon();

}
