package com.silvade.geolocation.location.gateway;

import com.silvade.geolocation.location.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserLocationResponse {

    private List<User> users;

}
