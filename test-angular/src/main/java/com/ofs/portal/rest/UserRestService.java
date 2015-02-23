package com.ofs.portal.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofs.portal.model.User;
import com.ofs.portal.service.UserService;

@Component
@Path("users")
public class UserRestService {

    @Autowired
    private UserService userService;

    @Autowired
    private WebTarget ofsSrvEndpoint;

    @GET
    @Path("/userdetails")
    @Produces({ MediaType.APPLICATION_JSON })
    public User getUserDetails(@Context HttpServletRequest context) {

        String loginName = context.getRemoteUser();
        if (loginName == null) {
            return null;
        }

        WebTarget target = ofsSrvEndpoint.path("/employees/" + loginName + "/roles");

        List<String> list = target.request().get(new GenericType<List<String>>() {});

        User user = new User();
        user.setWidgets(userService.getWidgets(list));
        user.setLoginName(loginName);

        return user;
    }
}
