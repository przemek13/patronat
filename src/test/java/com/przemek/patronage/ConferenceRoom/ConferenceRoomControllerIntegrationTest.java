package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
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
public class ConferenceRoomControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ConferenceRoomRepository testConferenceRoomRepository;
    @Autowired
    private OrganizationRepository testOrganizationRepository;

    @After
    public void resetConferenceRoomDb() {
        testConferenceRoomRepository.deleteAll();
    }

    @Test
    public void getConferenceRooms() throws Exception {
        //given

        testConferenceRoomRepository.save(new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1")));
        //when
        mvc.perform(get("/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addConferenceRoomWhenRecordValid() throws Exception {
        //given
        testOrganizationRepository.save(new Organization("Organization 1"));
        //when
        mvc.perform(post("/rooms/1").contentType(MediaType.APPLICATION_JSON).content(
                "    {\n" + "\"name\": \"Conference Room 1\",\n" + "\"floor\": 10,\n" + "\"available\": true,\n" + " \"sittingAndStandingPlaces\": 10\n" + "}"));
        ConferenceRoom testConferenceRoom = testConferenceRoomRepository.findByName("Conference Room 1");
        //then
        Assert.assertNotNull(testConferenceRoom);
    }

    @Test
    public void addConferenceRoomWhenRecordInValid() throws Exception {
        //given
        testOrganizationRepository.save(new Organization("Organization 1"));
        //when
        mvc.perform(post("/rooms/1").contentType(MediaType.APPLICATION_JSON).content(
                "    {\n" + "\"name\": \"Conference Room name to long\",\n" + "\"floor\": 10,\n" + "\"available\": true,\n" + " \"sittingAndStandingPlaces\": 10\n" + "}"));
        ConferenceRoom testConferenceRoom = testConferenceRoomRepository.findByName("Conference Room name to long");
        //then
        Assert.assertNull(testConferenceRoom);
    }
}
