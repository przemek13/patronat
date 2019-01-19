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

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomServiceTest {

    private ConferenceRoomService testConferenceRoomService;

    @Mock
    private ConferenceRoomRepository testConferenceRooms;
    @Mock
    private OrganizationRepository testOrganizations;
    @Mock
    private Organization testOrganization;

    private ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1"));

    private ConferenceRoom newTestConferenceRoom = new ConferenceRoom("Conference Room 2", 1, false, 100, new Organization("Organization 2"));

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
        assertEquals(testConferenceRoom.getName(), (newTestConferenceRoom.getName()));
        assertSame(testConferenceRoom.getFloor(), (newTestConferenceRoom.getFloor()));
        assertSame(testConferenceRoom.isAvailable(), (newTestConferenceRoom.isAvailable()));
        assertSame(testConferenceRoom.getSittingAndStandingPlaces(), (newTestConferenceRoom.getSittingAndStandingPlaces()));
        assertEquals(testConferenceRoom.getOrganization(), (newTestConferenceRoom.getOrganization()));
    }

    @Test
    public void updateWhenConferenceRoomIdNotExist() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.update(newTestConferenceRoom, testId);
        //then
        assertEquals(newTestConferenceRoom.getId(), (testId));
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
        System.out.flush();
    }
}