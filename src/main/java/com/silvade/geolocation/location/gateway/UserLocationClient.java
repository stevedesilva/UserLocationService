package com.silvade.geolocation.location.gateway;

import com.silvade.geolocation.location.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "location", url = "${user.service.baseurl}")
public interface UserLocationClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users",produces = "application/json",headers = "application/json")
    List<User> getUsers();

    @RequestMapping(method = RequestMethod.GET, value = "/city/{city}/users",produces = "application/json", headers = "application/json")
    List<User> getUsersByCity(@PathVariable("city") String city);

}
