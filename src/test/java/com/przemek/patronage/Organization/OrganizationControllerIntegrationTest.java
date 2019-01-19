package com.przemek.patronage.Organization;

import com.przemek.patronage.PatronageApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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
    private OrganizationRepository testRepository;

    @After
    public void resetDb() {
        testRepository.deleteAll();
    }

    @Test
    public void getOrganizations() throws Exception {
        //given
        testRepository.save(new Organization("Organization 1"));
        //when
        mvc.perform(get("/organizations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addUserWhenRecordValid() throws Exception {
        //given
        //when
        mvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(
                "{\"name\":\"Organization 1\"}"));
        Organization testOrganization = testRepository.findByName("Organization 1");
        //then
        Assert.assertNotNull(testOrganization);
    }

    @Test
    public void addUserWhenRecordInValid() throws Exception {
        //given
        //when
        mvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(
                "{\"name\":\"Organization 2\"}"));
        Organization testOrganization = testRepository.findByName("Organization 1");
        //then
        Assert.assertNull(testOrganization);
    }
}