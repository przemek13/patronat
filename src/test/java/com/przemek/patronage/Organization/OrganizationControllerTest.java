package com.przemek.patronage.Organization;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrganizationControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getOrganizationsTest() throws Exception {
        String uri = "/organizations";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Organization[] productlist = super.mapFromJson(content, Organization[].class);
        assertTrue(productlist.length > 0);
    }

    @Test
    public void addOrganizationTest() throws Exception {
        String uri = "/organizations";
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setName("Test Organization 1");
        String inputJson = super.mapToJson(organization);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateOrganizationTest() throws Exception {
        String uri = "/organizations/1";
        Organization organization = new Organization();
        organization.setName("Test Organization 2");
        String inputJson = super.mapToJson(organization);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteOrganizationExistingIdTest() throws Exception {
        String uri = "/organizations/3";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteOrganizationNonExistingIdTest() throws Exception {
        String uri = "/organizations/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}