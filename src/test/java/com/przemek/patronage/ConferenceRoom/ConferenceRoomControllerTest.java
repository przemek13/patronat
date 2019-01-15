package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.AbstractTest;
import com.przemek.patronage.Organization.Organization;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConferenceRoomControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getConferenceRoomsTest() throws Exception {
        String uri = "/rooms";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ConferenceRoom[] conferenceroomlist = super.mapFromJson(content, ConferenceRoom[].class);
        assertTrue(conferenceroomlist.length > 0);
    }

    @Test
    public void addConferenceRoomTest() throws Exception {
        String uri = "/rooms/3";
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        conferenceRoom.setName("Test Room 1");
        conferenceRoom.setAvailable(true);
        String inputJson = super.mapToJson(conferenceRoom);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateConferenceRoomTest() throws Exception {
        String uri = "/rooms/1";
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        conferenceRoom.setName("Test Room 2");
        String inputJson = super.mapToJson(conferenceRoom);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteConferenceRoomExistingIdTest() throws Exception {
        String uri = "/rooms/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteConferenceRoomNonExistingIdTest() throws Exception {
        String uri = "/rooms/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}