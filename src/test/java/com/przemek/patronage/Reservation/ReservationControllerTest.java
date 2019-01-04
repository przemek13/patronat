package com.przemek.patronage.Reservation;

import com.przemek.patronage.Organization.AbstractTest;
import com.przemek.patronage.Organization.Organization;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReservationControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getReservationsTest() throws Exception {
        String uri = "/reservations";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Organization[] productlist = super.mapFromJson(content, Organization[].class);
        assertTrue(productlist.length > 0);
    }

    @Test
    public void addReservationTest() throws Exception {
        String uri = "/reservations/1";
        Reservation reservation = new Reservation();
        reservation.setReservingId("Test Reserving 1");
        String inputJson = super.mapToJson(reservation);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateReservationTest() throws Exception {
        String uri = "/reservations/1";
        Reservation reservation = new Reservation();
        reservation.setReservingId("Test Reserving 2");
        String inputJson = super.mapToJson(reservation);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteReservationExistingIdTest() throws Exception {
        String uri = "/reservations/4";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteReservationNonExistingIdTest() throws Exception {
        String uri = "/reservations/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}