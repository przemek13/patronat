package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    private Reservation newTestReservation;

    private Long testId = 1L;

    @Before
    public void setTestUpReservationService() {
        this.testReservationService = new ReservationService(testReservations, testConferenceRooms);
    }

    @Test
    public void saveWhenConferenceRoomIdExists() throws NoSuchIdException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        //when
        testReservationService.save(newTestReservation, testId);
        //then
        verify(testReservations, times(1)).save(newTestReservation);
    }

    @Test(expected = NoSuchIdException.class)
    public void saveWhenConferenceRoomIdNotExist() throws NoSuchIdException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
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
    public void updateWhenReservationdNotExist() {
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