package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.Organization;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceTest {

    private EquipmentService testEquipmentService;

    @Mock
    private EquipmentRepository testEquipmentRepository;
    @Mock
    private ConferenceRoomRepository testConferenceRooms;
    @Mock
    private ConferenceRoom testConferenceRoom;

    private Equipment testEquipment = new Equipment("BenQ", true, 1, "+12 123456789", InterfaceConnections.BLUETOOTH, new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1")));

    private Equipment newTestEquipment = new Equipment("Hitachi", false, new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1")));

    private Long testId = 1L;

    @Before
    public void setUpTestEquipmentService() {
        this.testEquipmentService = new EquipmentService(testEquipmentRepository, testConferenceRooms);
    }

    @Test
    public void saveWhenConferenceRoomIdExist() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        //when
        testEquipmentService.save(newTestEquipment, testId);
        //then
        verify(testEquipmentRepository, times(1)).save(newTestEquipment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenConferenceRoomIdNotExist() {
        //given
        when(testConferenceRooms.findById(testId)).thenReturn(Optional.empty());
        //when
        testEquipmentService.save(newTestEquipment, testId);
        //then
    }

    @Test
    public void updateWhenEquipmentIdExists() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.ofNullable(testEquipment));
        //when
        testEquipmentService.update(newTestEquipment, testId);
        //then
        assertEquals(testEquipment.getProjectorName(), newTestEquipment.getProjectorName());
        assertEquals(testEquipment.isPhone(), newTestEquipment.isPhone());
        assertSame(testEquipment.getInternalNumber(), newTestEquipment.getInternalNumber());
        assertEquals(testEquipment.getExternalNumber(), newTestEquipment.getExternalNumber());
        assertEquals(testEquipment.getConnections(), newTestEquipment.getConnections());
        assertEquals(testEquipment.getConferenceroom(), newTestEquipment.getConferenceroom());
    }

    @Test
    public void updateWhenEquipmentIdNotExist() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testEquipmentService.update(newTestEquipment, testId);
        //then
        assertEquals(newTestEquipment.getId(), (testId));
    }

    @Test
    public void deleteWhenOrganizationIdExists() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.ofNullable(newTestEquipment));
        //when
        testEquipmentService.delete(testId);
        //then
        verify(testEquipmentRepository, times(1)).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenOrganizationIdNotExist() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testEquipmentService.delete(testId);
        //then
    }
}