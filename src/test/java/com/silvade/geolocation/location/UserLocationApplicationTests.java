package com.silvade.geolocation.location;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import com.silvade.geolocation.location.gateway.UserLocationClient;
import com.silvade.geolocation.location.model.User;
import feign.FeignException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLocationApplicationTests {

    private static final int LOCATION_API_PORT = 8092;
    private static WireMockServer locationApi;

    @Autowired
    private UserLocationClient userLocationClient;

    @BeforeClass
    public static void setup() {
        locationApi = new WireMockServer(LOCATION_API_PORT);
        locationApi.start();
    }

    @AfterClass
    public static void teardown() {
        if (locationApi != null) {
            locationApi.stop();
        }
    }

    @Test
    public void testGetAllUsers_ReturnsListOfUsers() {
        locationApi.stubFor(get(urlEqualTo("/users"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(okJson(getUserTestData())));
        // WHEN
        List<User> users = userLocationClient.getUsers();

//		//THEN
        assertThat(users.size()).isNotZero();
    }

    private String getUserTestData() {
        return "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"first_name\": \"Maurise\",\n" +
                "        \"last_name\": \"Shieldon\",\n" +
                "        \"email\": \"mshieldon0@squidoo.com\",\n" +
                "        \"ip_address\": \"192.57.232.111\",\n" +
                "        \"latitude\": 34.003135,\n" +
                "        \"longitude\": -117.7228641\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"first_name\": \"Bendix\",\n" +
                "        \"last_name\": \"Halgarth\",\n" +
                "        \"email\": \"bhalgarth1@timesonline.co.uk\",\n" +
                "        \"ip_address\": \"4.185.73.82\",\n" +
                "        \"latitude\": -2.9623869,\n" +
                "        \"longitude\": 104.7399789\n" +
                "    }\n" +
                "]";
    }

    @Test
    public void testGetAllUsers_ReturnsError() {
        locationApi.stubFor(get(urlEqualTo("/users"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(okJson("[]")));

        // WHEN
        List<User> users = userLocationClient.getUsers();

        //THEN
        assertThat(users.isEmpty());
        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    public void testGetAllUsers_ForInvalidUsers() {
        locationApi.stubFor(get(urlEqualTo("/city/London/users"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")));

        // WHEN
        try {
            userLocationClient.getUsersByCity("");
        } catch (FeignException e) {
            MatcherAssert.assertThat(e.status(), Matchers.equalTo(NOT_FOUND.value()));
        }
    }

    @Test
    public void testGetAllUsersForCity_ForUnknownCity() {
        locationApi.stubFor(get(urlEqualTo("/city/London/users"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("users.json")));

        // WHEN
        try {
            userLocationClient.getUsersByCity("fake");
        } catch (FeignException e) {
            MatcherAssert.assertThat(e.status(), Matchers.equalTo(NOT_FOUND.value()));
        }
    }

    @Test
    public void testGetAllUsers_dReturnsError() {
        locationApi.stubFor(get(urlEqualTo("/users"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        // WHEN
        try {
            List<User> users = userLocationClient.getUsers();
            fail("Exception should be thrown");

        } catch (FeignException e) {
        }

    }

}
