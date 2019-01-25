package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private static OrganizationRepository testOrganizations;

    @Autowired
    private static ConferenceRoomRepository testConferenceRooms;

    @Autowired
    private static ReservationRepository testReservations;

    @TestConfiguration
    public class EquipmentServiceImplTestContextConfiguration {
        @Bean
        public ReservationService reservationService() {
            return new ReservationService(testReservations, testConferenceRooms, testOrganizations);
        }

    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService testService;

    @Test
    public void getReservations() throws Exception {
        //given
        when(testService.findAll()).thenReturn(Collections.singletonList(new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T17:00:00", new ConferenceRoom("Conference Room 1", 5, true, 5, new Organization("Organization 1")))));
        //when
        mvc.perform(get("/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}