package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Exceptions.StartAfterEndException;
import com.przemek.patronage.Exceptions.WrongDurationException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    private ReservationService testReservationService;
    @Mock
    private ReservationRepository testReservations;
    @Mock
    private ConferenceRoomRepository testConferenceRooms;
    @Mock
    private ConferenceRoom testConferenceRoom;
    @Mock
    private Reservation testReservation;
    @Mock
    private Reservation newTestReservation;

    private Long testId = 1L;

    @Before
    public void setTestUpReservationService() {
        this.testReservationService = new ReservationService(testReservations, testConferenceRooms);
    }

    @Test
    public void saveWhenConferenceRoomIdExists() throws NoSuchIdException, RoomReservedException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        when(newTestReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        when(newTestReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T17:00:00"));
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

    @Test (expected = StartAfterEndException.class)
    public void saveWhenReservationStartAfterReservationDate() throws NoSuchIdException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        when(newTestReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T17:00:00"));
        when(newTestReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test (expected = WrongDurationException.class)
    public void saveWhenReservationDurationTooShort() throws NoSuchIdException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        when(newTestReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        when(newTestReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T16:05:00"));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test (expected = WrongDurationException.class)
    public void saveWhenReservationDurationTooLong() throws NoSuchIdException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        when(newTestReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        when(newTestReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T18:01:00"));
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test (expected = IllegalArgumentException.class)
    public void saveWhenReservationStartSameDate() throws NoSuchIdException, WrongDurationException, StartAfterEndException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        when(newTestReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        when(newTestReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T17:00:00"));
        when(testReservation.getReservationStart()).thenReturn(LocalDateTime.parse("2019-03-23T16:00:00"));
        when(testReservation.getReservationEnd()).thenReturn(LocalDateTime.parse("2019-03-23T18:00:00"));
        // (newTestReservation.getReservationStart().isEqual(testReservation.getReservationStart())).thenReturn(true);
        // zwraca: org.mockito.exceptions.misusing.WrongTypeOfReturnValue
        //when
        testReservationService.save(newTestReservation, testId);
    }

    @Test
    public void updateWhenReservationIdExists() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.ofNullable(newTestReservation));
        //when
        testReservationService.update(newTestReservation, testId);
        //then
        verify(newTestReservation, times(1)).setReservingId(newTestReservation.getReservingId());
        verify(newTestReservation, times(1)).setReservationStart(newTestReservation.getReservationStart());
        verify(newTestReservation, times(1)).setReservationEnd(newTestReservation.getReservationEnd());
        verify(newTestReservation, times(1)).setConferenceRoom(newTestReservation.getConferenceRoom());
    }

    @Test
    public void updateWhenReservationNotExist() {
        //given
        when(testReservations.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.update(newTestReservation, testId);
        //then
        verify(newTestReservation, times(1)).setId(testId);
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