package com.ofs.portal.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ofs.portal.model.LeaveBalance;

public class EmployeeRestServiceTest extends AbstractRestServiceTest {

    @Override
    protected String getResourcePath() {
        return "/employees";
    }

    @Test
    public void testGetLeaveBalance() {

        WebTarget t = target(getResourceFullPath() + "/test@object-frontier.com/leavebalance");
        final Response response = t.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, response.getStatus());
        final List<LeaveBalance> leaveBalances = response.readEntity(new GenericType<List<LeaveBalance>>() {});
        assertNotNull(leaveBalances);
    }
}
