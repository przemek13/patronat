package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EquipmentServiceTest {

    @TestConfiguration
    static class MapperImplTestContextConfiguration {
        @Bean
        public Mapper mapper() {
            return new Mapper();
        }
    }

    @TestConfiguration
    static class EquipmentUpdateImplTestContextConfiguration {
        @Bean
        public EquipmentDataChange equipmentDataChange() {
            return new EquipmentDataChange();
        }
    }

    private EquipmentService testEquipmentService;
    @Autowired
    private Mapper testMapper;
    @Mock
    private EquipmentRepository testEquipmentRepository;
    @Autowired
    private ConferenceRoomRepository testConferenceRoomRepository;
    @Autowired
    private EquipmentDataChange testEquipmentDataChange;

    private final ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1"));

    private final Equipment testEquipment = new Equipment("BenQ", true, 1, "+12 123456789", InterfaceConnections.BLUETOOTH, testConferenceRoom);

    private final EquipmentDTO newTestEquipmentDTO = new EquipmentDTO("Hitachi", false, new ConferenceRoomDTO("Conference Room 1", 10, true, 10, new OrganizationDTO("Organization 1")));

    private final Long testId = 1L;

    @Before
    public void setUpTestEquipmentService() {
        this.testEquipmentService = new EquipmentService(testEquipmentRepository, testConferenceRoomRepository, testMapper, testEquipmentDataChange);
    }

    @Test
    public void saveWhenConferenceRoomIdExist() {
        //given
        testConferenceRoomRepository.save(testConferenceRoom);
        //when
        testEquipmentService.save(newTestEquipmentDTO, testConferenceRoom.getId());
        //then
        verify(testEquipmentRepository, times(1)).save(any(Equipment.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenConferenceRoomIdNotExist() {
        //given
        testConferenceRoomRepository.save(testConferenceRoom);
        //when
        testEquipmentService.save(newTestEquipmentDTO, 10L);
        //then
    }

    @Test
    public void updateWhenEquipmentIdExists() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.ofNullable(testEquipment));
        //when
        testEquipmentService.update(newTestEquipmentDTO, testId);
        //then
        assertEquals(testEquipment.getProjectorName(), newTestEquipmentDTO.getProjectorName());
        assertEquals(testEquipment.isPhone(), newTestEquipmentDTO.isPhone());
        assertSame(testEquipment.getInternalNumber(), newTestEquipmentDTO.getInternalNumber());
        assertEquals(testEquipment.getExternalNumber(), newTestEquipmentDTO.getExternalNumber());
        assertEquals(testEquipment.getConnections(), newTestEquipmentDTO.getConnections());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWhenEquipmentIdNotExist() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testEquipmentService.update(newTestEquipmentDTO, testId);
        //then
    }

    @Test
    public void deleteWhenOrganizationIdExists() {
        //given
        when(testEquipmentRepository.findById(testId)).thenReturn(Optional.ofNullable(testEquipment));
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