package com.ofs.portal.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ofs.portal.model.Widget;

public class UserRestServiceTest extends AbstractRestServiceTest {

    @Override
    protected String getResourcePath() {
        return "/users";
    }

    @Test
    public void testGetWidgets() {

        WebTarget t = target(getResourceFullPath() + "/widgets");
        final Response response = t.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, response.getStatus());
        final List<Widget> users = response.readEntity(new GenericType<List<Widget>>() {});
        assertTrue(users.size() > 0);
    }
}
