package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationServiceTest {

    private ReservationService testReservationService;

    @TestConfiguration
    static class MapperImplTestContextConfiguration {
        @Bean
        public Mapper mapper() {
            return new Mapper();
        }
    }

    @TestConfiguration
    static class ReservationCheckImplTestContextConfiguration {
        @Bean
        public ReservationCheck reservationCheck(ConferenceRoomRepository conferenceRoomRepository) {
            return new ReservationCheck(conferenceRoomRepository);
        }
    }

    @Autowired
    private Mapper mapper;
    @Autowired
    private ReservationCheck reservationCheck;
    @Mock
    private ReservationRepository testReservations;
    @Autowired
    private ConferenceRoomRepository testConferenceRooms;
    @Autowired
    private OrganizationRepository testOrganizationRepository;

    List<ConferenceRoom> roomsList = new ArrayList<>();

    List<Reservation> reservatonsList = new ArrayList<>();

    private Organization testOrganization = new Organization("Organization 1", roomsList);

    private ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, testOrganization, reservatonsList);

    private Reservation testReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 00), LocalDateTime.of(2019, 3, 23, 17, 00), testConferenceRoom);

    private Reservation newTestReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 00), LocalDateTime.of(2019, 3, 23, 17, 00), testConferenceRoom);

    private ReservationDTO newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 17, 00), LocalDateTime.of(2019, 3, 23, 18, 00), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));

    private Long testId = 1L;

    @Before
    public void setUpTestReservationService() {
        this.testReservationService = new ReservationService(testReservations, testConferenceRooms, testOrganizationRepository, mapper, reservationCheck);
    }

    @Test
    public void saveWhenConferenceRoomIdExists() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRooms.save(testConferenceRoom);
        reservatonsList.add(testReservation);
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
        verify(testReservations, times(1)).save(any(Reservation.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenConferenceRoomIdNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRooms.save(testConferenceRoom);
        reservatonsList.add(testReservation);
        //when
        testReservationService.save(newTestReservationDTO, 10L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationStartAfterReservationDate() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRooms.save(testConferenceRoom);
        reservatonsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 15, 00), LocalDateTime.of(2019, 3, 23, 14, 00), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationDurationTooShort() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRooms.save(testConferenceRoom);
        reservatonsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 14, 00), LocalDateTime.of(2019, 3, 23, 14, 05), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationDurationTooLong() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        var newTestReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 00), LocalDateTime.of(2019, 3, 23, 17, 00), new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
                //when
        testReservationService.save(newTestReservationDTO, testId);
    }

    @Test(expected = RoomReservedException.class)
    public void saveWhenReservationDuringTheSamePeriod() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRooms.save(testConferenceRoom);
        reservatonsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 00), LocalDateTime.of(2019, 3, 23, 17, 00), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test
    public void updateWhenReservationIdExists() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.ofNullable(testReservation));
        //when
        testReservationService.update(newTestReservationDTO, testId);
        //then
        assertEquals(testReservation.getReservingId(), newTestReservationDTO.getReservingId());
        assertEquals(testReservation.getReservationStart(), newTestReservationDTO.getReservationStart());
        assertEquals(testReservation.getReservationEnd(), newTestReservationDTO.getReservationEnd());
    }

    @Test
    public void updateWhenReservationNotExist() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.update(newTestReservationDTO, testId);
        //then
//        assertEquals(newTestReservation.getId(), (testId));
    }

    @Test
    public void deleteWhenReservationIdExists() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.ofNullable(newTestReservation));
        //when
        testReservationService.delete(testId);
        //then
        verify(testReservations, times(1)).deleteById(testId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenReservationIdNotExist() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.delete(testId);
        //then
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.flush();
    }
}