package com.silvade.geolocation.location.service;

import com.silvade.geolocation.location.gateway.UserLocationResponse;
import com.silvade.geolocation.location.gateway.UserLocationClient;
import com.silvade.geolocation.location.model.DistanceCalculator;
import com.silvade.geolocation.location.model.Location;
import com.silvade.geolocation.location.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserLocationService implements UserLocation {

    private static final Location LONDON_LOCATION =  new Location(51.5074, 0.1278);
    private static final double LONDON_RADIUS = 18.63d; // length of M25 / 2 * Pi
    private static final String LONDON = "London";
    private static final int DISTANCE = 50;

//    @Autowired
    private UserLocationClient userLocationClient;

    public UserLocationService(UserLocationClient userLocationClient) {
        this.userLocationClient = userLocationClient;
    }

    @Override
    public UserLocationResponse getUsersByCity(String city) {
        return UserLocationResponse.builder()
                .users(userLocationClient.getUsersByCity(city))
                .build();
    }

    @Override
    public UserLocationResponse getAllUsers() {
        return UserLocationResponse.builder()
                .users(userLocationClient.getUsers())
                .build();

    }

    @Override
    public UserLocationResponse getUsersConnectedWithLondon() {
        return createUserLocationResponse(addAllUsersConnectedToLondon());
    }

    private UserLocationResponse createUserLocationResponse(Set<User> combinedUsers) {
        List<User> result = new ArrayList<>();
        result.addAll(combinedUsers);
        return UserLocationResponse.builder()
                .users(result)
                .build();
    }

    private Set<User> addAllUsersConnectedToLondon() {
        Set<User> combinedUsers = new HashSet<>();
        combinedUsers.addAll(getLondonUsers());
        combinedUsers.addAll(getUsersLivingWithin50MilesOfLondon());
        return combinedUsers;
    }


    private List<User> getLondonUsers() {
        return getUsersByCity(LONDON).getUsers();
    }

    private Set<User> getUsersLivingWithin50MilesOfLondon() {
        return filterUsersLivingWithin50MilesOfLondon(getAllUserLocationData());
    }

    private Set<User> filterUsersLivingWithin50MilesOfLondon(List<User> users) {
        return users.stream()
                    .filter( user -> new DistanceCalculator( Location.from( user ), LONDON_LOCATION ).computeInMiles() <= DISTANCE + LONDON_RADIUS )
                    .collect( Collectors.toSet());
    }

    private List<User> getAllUserLocationData() {
        return getAllUsers().getUsers();
    }

}
