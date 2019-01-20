package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Exceptions.StartAfterEndException;
import com.przemek.patronage.Exceptions.WrongDurationException;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    private ReservationService testReservationService;
    @Mock
    private ReservationRepository testReservations;
    @Mock
    private ConferenceRoomRepository testConferenceRooms;
    @Mock
    private OrganizationRepository testOrganizationRepository;
    @Mock
    private ConferenceRoom testConferenceRoom;

    private Reservation testReservation = new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T17:00:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));

    private Reservation newTestReservation = new Reservation("Reserving 2", "2019-03-23T18:00:00", "2019-03-23T19:00:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));

    private Long testId = 1L;

    @Before
    public void setTestUpReservationService() {
        this.testReservationService = new ReservationService(testReservations, testConferenceRooms, testOrganizationRepository);
    }

    @Test
    public void saveWhenConferenceRoomIdExists() throws NoSuchIdException, RoomReservedException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        //when
        testReservationService.save(newTestReservation, testId);
        //then
        verify(testReservations, times(1)).save(newTestReservation);
    }

    @Test(expected = NoSuchIdException.class)
    public void saveWhenConferenceRoomIdNotExist() throws NoSuchIdException, RoomReservedException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test(expected = StartAfterEndException.class)
    public void saveWhenReservationStartAfterReservationDate() throws NoSuchIdException, WrongDurationException, StartAfterEndException, RoomReservedException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        var newTestReservation = new Reservation("Reserving 1", "2019-03-23T17:00:00", "2019-03-23T16:00:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test(expected = WrongDurationException.class)
    public void saveWhenReservationDurationTooShort() throws NoSuchIdException, WrongDurationException, StartAfterEndException, IOException, RoomReservedException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        var newTestReservation = new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T16:05:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test(expected = WrongDurationException.class)
    public void saveWhenReservationDurationTooLong() throws NoSuchIdException, WrongDurationException, StartAfterEndException, IOException, RoomReservedException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        var newTestReservation = new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T18:01:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test(expected = RoomReservedException.class)
    public void saveWhenReservationDuringTheSamePeriod() throws NoSuchIdException, WrongDurationException, StartAfterEndException, RoomReservedException {
        //given
        when(testConferenceRoom.getReservations()).thenReturn(Collections.singletonList(testReservation));
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        var testReservation = new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T17:00:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
        var newTestReservation = new Reservation("Reserving 2", "2019-03-23T16:00:00", "2019-03-23T17:00:00", new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));
        //when
        testReservationService.save(newTestReservation, testId);
        //then
    }

    @Test
    public void updateWhenReservationIdExists() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.ofNullable(testReservation));
        //when
        testReservationService.update(newTestReservation, testId);
        //then
        assertEquals(testReservation.getReservingId(), newTestReservation.getReservingId());
        assertEquals(testReservation.getReservationStart(), newTestReservation.getReservationStart());
        assertEquals(testReservation.getReservationEnd(), newTestReservation.getReservationEnd());
        assertEquals(testReservation.getConferenceRoom(), newTestReservation.getConferenceRoom());
    }

    @Test
    public void updateWhenReservationNotExist() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.update(newTestReservation, testId);
        //then
        assertEquals(newTestReservation.getId(), (testId));
    }

    @Test
    public void deleteWhenReservationIdExists() throws NoSuchIdException {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.ofNullable(newTestReservation));
        //when
        testReservationService.delete(testId);
        //then
        verify(testReservations, times(1)).deleteById(testId);
    }

    @Test(expected = NoSuchIdException.class)
    public void deleteWhenReservationIdNotExist() throws NoSuchIdException {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.delete(testId);
        //then
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("tear down class");
        System.out.flush();
    }
}