package com.ofs.srv.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofs.srv.model.ApproverInfo;
import com.ofs.srv.model.LeaveBalance;
import com.ofs.srv.service.EmployeeService;

@Component
@Path("employees")
public class EmployeeRestService {

    @Autowired
    private EmployeeService employeeService;

    @GET
    @Path("/{loginName}/approvers")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ApproverInfo> getApprovers(@PathParam("loginName") String loginName) {
        return employeeService.getEmployeeApproverInfo(loginName);
    }

    @GET
    @Path("/{loginName}/leavebalance")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<LeaveBalance> getLeaveBalance(@PathParam("loginName") String loginName) {
        return employeeService.getEmployeeLeaveInfo(loginName);
    }

    @GET
    @Path("/{loginName}/roles")	
    @Produces({ MediaType.APPLICATION_JSON })
    public List<String> getUserRoles(@PathParam("loginName") String loginName) {
        return employeeService.getUserRoles(loginName);
    }
}
