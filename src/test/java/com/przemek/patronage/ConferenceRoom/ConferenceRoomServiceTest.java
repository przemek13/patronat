package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomServiceTest {

    ConferenceRoomService testConferenceRoomService;

    @Mock
    private ConferenceRoomRepository testConferenceRooms;
    @Mock
    private OrganizationRepository testOrganizations;
    @Mock
    private Organization testOrganization;
    @Mock
    private ConferenceRoom testConferenceRoom;
    @Mock
    private ConferenceRoom newTestConferenceRoom;

    private Long testId = 1L;

    @Before
    public void setUpTestConferenceRoomService() {
        this.testConferenceRoomService = new ConferenceRoomService(testConferenceRooms, testOrganizations);
    }

    @Test
    public void saveWhenOrganizationIdExistsAndConferenceRoomNameNotExist() throws NoSuchIdException, SameNameException {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.of(testOrganization));
        when(testConferenceRooms.findByName(newTestConferenceRoom.getName())).thenReturn(null);
        //when
        testConferenceRoomService.save(newTestConferenceRoom, testId);
        //then
        verify(testOrganizations, times(1)).save(testOrganization);
    }

    @Test(expected = NoSuchIdException.class)
    public void saveWhenOrganizationIdNotExist() throws NoSuchIdException, SameNameException {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.save(newTestConferenceRoom, testId);
        //then
    }


    @Test(expected = SameNameException.class)
    public void saveWhenConferenceRoomNameExists() throws NoSuchIdException, SameNameException {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.of(testOrganization));
        when(testConferenceRooms.findByName(newTestConferenceRoom.getName())).thenReturn(newTestConferenceRoom);
        //when
        testConferenceRoomService.save(newTestConferenceRoom, testId);
        //then
    }

    @Test
    public void updateWhenConferenceRoomIdExists() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        //when
        testConferenceRoomService.update(newTestConferenceRoom, testId);
        //then
        verify(testConferenceRoom, times(1)).setName(newTestConferenceRoom.getName());
        verify(testConferenceRoom, times(1)).setOptionalId(newTestConferenceRoom.getOptionalId());
        verify(testConferenceRoom, times(1)).setFloor(newTestConferenceRoom.getFloor());
        verify(testConferenceRoom, times(1)).setAvailable(newTestConferenceRoom.isAvailable());
        verify(testConferenceRoom, times(1)).setSittingAndStandingPlaces(newTestConferenceRoom.getSittingAndStandingPlaces());
        verify(testConferenceRoom, times(1)).setLyingPlaces(newTestConferenceRoom.getLyingPlaces());
        verify(testConferenceRoom, times(1)).setHangingPlaces(newTestConferenceRoom.getHangingPlaces());
    }

    @Test
    public void updateWhenConferenceRoomIdNotExist() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.update(newTestConferenceRoom, testId);
        //then
        verify(newTestConferenceRoom, times(1)).setId(testId);
    }

    @Test
    public void deleteWhenOrganizationIdExists() throws NoSuchIdException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(newTestConferenceRoom));
        //when
        testConferenceRoomService.delete(testId);
        //then
        verify(testConferenceRooms, times(1)).deleteById(1L);
    }

    @Test(expected = NoSuchIdException.class)
    public void deleteWhenOrganizationIdNotExist() throws NoSuchIdException {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.delete(testId);
        //then
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("tear down class");
        System.out.flush();
    }
}