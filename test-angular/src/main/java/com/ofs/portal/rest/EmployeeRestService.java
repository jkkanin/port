package com.ofs.portal.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofs.portal.model.LeaveBalance;

@Component
@Path("employees")
public class EmployeeRestService {

    @Autowired
    private WebTarget ofsSrvEndpoint;

    @GET
    @Path("/{loginName}/leavebalance")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<LeaveBalance> getLeaveBalance(@PathParam("loginName") String loginName) {

    	WebTarget target = ofsSrvEndpoint.path("/employees/" + loginName + "/leavebalance");
        return target.request().get(new GenericType<List<LeaveBalance>>() {});
    }
}