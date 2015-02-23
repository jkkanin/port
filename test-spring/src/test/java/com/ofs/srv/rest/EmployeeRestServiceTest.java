package com.ofs.srv.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ofs.srv.model.ApproverInfo;
import com.ofs.srv.model.LeaveBalance;

public class EmployeeRestServiceTest extends AbstractRestServiceTest {

    @Override
    protected String getResourcePath() {
        return "/employees";
    }

    @Test
    public void testGetApprovers() {

        final Response response = webTarget("/test/approvers").get();
        assertEquals(200, response.getStatus());
        final List<ApproverInfo> approvers = response.readEntity(new GenericType<List<ApproverInfo>>() {});
        assertNotNull(approvers);
    }

    @Test
    public void testGetLeaveBalance() {

        final Response response = webTarget("/test/leavebalance").get();
        assertEquals(200, response.getStatus());
        final List<LeaveBalance> leaveBalance = response.readEntity(new GenericType<List<LeaveBalance>>() {});
        assertNotNull(leaveBalance);
    } 

    @Test
    public void testGetUserRoles() {

        final Response response = webTarget("/test/roles").get();
        assertEquals(200, response.getStatus());
        final List<String> roles = response.readEntity(new GenericType<List<String>>() {});
        assertNotNull(roles);
    }   
}
