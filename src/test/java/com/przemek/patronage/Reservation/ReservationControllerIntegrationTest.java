package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.Organization;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PatronageApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ReservationRepository testReservationRepository;
    @Autowired
    private ConferenceRoomRepository testConferenceRoomRepository;

    @After
    public void resetReservationDb() {
        testReservationRepository.deleteAll();
    }

    @Test
    public void getReservations() throws Exception {
        //given
        testReservationRepository.save(new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 0), LocalDateTime.of(2019, 3, 23, 17, 0), new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1"))));
        //when
        mvc.perform(get("/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addReservationWhenRecordValid() throws Exception {
        //given
        testConferenceRoomRepository.save(new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1")));
        //when
        mvc.perform(post("/reservations/1").contentType(MediaType.APPLICATION_JSON).content(
                "{\n" + " \"reservingId\": \"Reserving 1\",\n" + " \"reservationStart\": \"2019-03-23 23:00\",\n" + " \"reservationEnd\": \"2019-03-23 24:00\"\n" + "}"));
        Optional<Reservation> testReservation = testReservationRepository.findById(2L);
        //then
        Assert.assertNotNull(testReservation);
    }

    @Test
    public void addReservationWhenRecordInValid() throws Exception {
        //given
        testConferenceRoomRepository.save(new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1")));
        //when
        mvc.perform(post("/reservations/1").contentType(MediaType.APPLICATION_JSON).content(
                "{\n" + " \"reservingId\": \"Reserving 1\",\n" + " \"reservationStart\": \"2019-03-23 24:00\",\n" + " \"reservationEnd\": \"2019-03-23 23:00\"\n" + "}"));
        Optional<Reservation> testReservation = testReservationRepository.findById(2L);
        //then
        Assert.assertEquals(testReservation, (Optional.empty()));
    }
}