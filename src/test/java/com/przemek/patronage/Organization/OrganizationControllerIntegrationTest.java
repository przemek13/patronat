package com.przemek.patronage.Organization;

import com.przemek.patronage.PatronageApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PatronageApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class OrganizationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrganizationRepository testOrganizationRepository;

    @After
    public void resetOrganizationDb() {
        testOrganizationRepository.deleteAll();
    }

    @Test
    public void getOrganizations() throws Exception {
        //given
        testOrganizationRepository.save(new Organization("Organization 1"));
        //when
        //then
        mvc.perform(get("/organizations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addOrganizationWhenRecordValid() throws Exception {
        //given
        //when
        mvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(
                "{\"name\":\"Organization 1\"}"));
        var testOrganization = testOrganizationRepository.findByName("Organization 1");
        //then
        Assert.assertNotNull(testOrganization);
    }

    @Test
    public void addOrganizationWhenRecordInValid() throws Exception {
        //given
        //when
        mvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(
                "{\"name\":\"Organization name to long\"}"));
        var testOrganization = testOrganizationRepository.findByName("Organization name to long");
        //then
        Assert.assertNull(testOrganization);
    }
}