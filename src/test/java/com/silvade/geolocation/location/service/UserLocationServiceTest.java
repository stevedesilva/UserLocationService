package com.silvade.geolocation.location.service;

import com.silvade.geolocation.location.gateway.UserLocationClient;
import com.silvade.geolocation.location.gateway.UserLocationResponse;
import com.silvade.geolocation.location.model.User;
import feign.FeignException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLocationServiceTest {

    public static final String LONDON = "London";
    public static final String FAKE_CITY = "FAKE_CITY";

    private UserLocationClient userLocationClient;

    private UserLocationService userLocationService;

    @Before
    public void setup() {
        userLocationClient = mock(UserLocationClient.class);
        userLocationService = new UserLocationService(userLocationClient);
    }

    @Test
    public void getUsersConnectedWithLondon_ShouldReturnCityList() {

        final List<User> londonUsers = getLondonUserData();
        final List<User> orbitalUsers = user50MilesFromLondon();
        final int expectedUsers = londonUsers.size() + orbitalUsers.size();

        // GIVEN
        when(userLocationClient.getUsers()).thenReturn(orbitalUsers);
        when(userLocationClient.getUsersByCity(LONDON)).thenReturn(londonUsers);

        // WHEN
        final UserLocationResponse usersByCity = userLocationService.getUsersConnectedWithLondon();
        // THEN
        assertThat(usersByCity).isNotNull();
        assertThat(usersByCity.getUsers().size()).isEqualTo(expectedUsers);

    }

    @Test
    public void getUsersByCity_WhenCalledLondon_ShouldReturnCityList() {
        // GIVEN
        when(userLocationClient.getUsersByCity(LONDON)).thenReturn(getLondonUserData());
        // WHEN
        final UserLocationResponse usersByCity = userLocationService.getUsersByCity(LONDON);
        // THEN
        assertThat(usersByCity).isNotNull();
    }

    @Test
    public void getUsersByCity_WhenCityNotFound_ShouldReturnEmptyList() {
        // GIVEN
        when(userLocationClient.getUsersByCity(FAKE_CITY)).thenReturn(Lists.emptyList());
        // WHEN
        final UserLocationResponse usersByCity = userLocationService.getUsersByCity(FAKE_CITY);
        // THEN
        assertThat(usersByCity.getUsers()).isEmpty();
    }

    @Test
    public void shouldThrowException_WhenFeignException_GetUsersByCity() {
        // GIVEN
        when(userLocationClient.getUsersByCity(FAKE_CITY)).thenThrow(mock(FeignException.class));
        // WHEN
        Throwable throwable = catchThrowable(() -> userLocationService.getUsersByCity(FAKE_CITY));
        // THEN
        assertThat(throwable).isInstanceOf(FeignException.class);
    }


    @Test
    public void getUsers_ShouldReturnCityList() {
        // GIVEN
        when(userLocationClient.getUsers()).thenReturn(user50MilesFromLondon());
        // WHEN
        final UserLocationResponse usersByCity = userLocationService.getAllUsers();
        // THEN
        assertThat(usersByCity).isNotNull();
    }

    @Test
    public void getUsers_WhenNoUsersFound_ShouldReturnEmptyList() {
        // GIVEN
        when(userLocationClient.getUsers()).thenReturn(Lists.emptyList());
        // WHEN
        final UserLocationResponse allUsers = userLocationService.getAllUsers();
        // THEN
        assertThat(allUsers.getUsers()).isEmpty();
    }

    @Test
    public void shouldThrowException_WhenFeignException_GetUsers() {
        // GIVEN
        when(userLocationClient.getUsers()).thenThrow(mock(FeignException.class));
        // WHEN
        Throwable throwable = catchThrowable(() -> userLocationService.getAllUsers());
        // THEN
        assertThat(throwable).isInstanceOf(FeignException.class);
    }

    private List<User> user50MilesFromLondon() {
        final List<User> users = new ArrayList<>();
        users.add(new User(322, "Hugo", "Lynd", "hlynd8x@merriam-webster.com", "109.0.153.166", 51.6710832, 0.8078532));
        users.add(new User(554, "Ancell", "Garnsworthy", "agarnsworthy7d@seattletimes.com", "67.4.69.137", 51.6553959, 0.0572553));
        users.add(new User(266, "Phyllys", "Hebbs", "phebbsfd@umn.edu", "100.89.186.13", 51.5489435, 0.3860497));
        return users;
    }

    private List<User> getLondonUserData() {
        final List<User> users = new ArrayList<>();
        users.add(new User(135, "Mechelle", "Boam", "mboam3q@thetimes.co.uk", "113.71.242.187", -6.5115909, 105.652983));
        users.add(new User(688, "Tiffi", "Colbertson", "tcolbertsonj3@vimeo.com", "141.49.93.0", 37.13, -84.08));
        users.add(new User(396, "Terry", "Stowgill", "tstowgillaz@webeden.co.uk", "143.190.50.240", -6.7098551, 111.3479498));
        users.add(new User(794, "Katee", "Gopsall", "kgopsallm1@cam.ac.uk", "203.138.133.164", 5.7204203, 10.901604));
        return users;
    }

}