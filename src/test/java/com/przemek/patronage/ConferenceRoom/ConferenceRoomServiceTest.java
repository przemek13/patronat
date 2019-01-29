package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Organization.OrganizationRepository;
import com.przemek.patronage.Reservation.Reservation;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConferenceRoomServiceTest {

    @TestConfiguration
    static class MapperImplTestContextConfiguration {
        @Bean
        public Mapper mapper() {
            return new Mapper();
        }
    }

    @TestConfiguration
    static class ConferenceRoomUpdateImplTestContextConfiguration {
        @Bean
        public ConferenceRoomDataChange conferenceRoomDataChange() {
            return new ConferenceRoomDataChange();
        }
    }

    private ConferenceRoomService testConferenceRoomService;
    @Autowired
    private Mapper testMapper;
    @Mock
    private ConferenceRoomRepository testConferenceRoomRepository;
    @Autowired
    private OrganizationRepository testOrganizationRepository;
    @Autowired
    private ConferenceRoomDataChange testConferenceRoomDataChange;

    private final Long testId = 1L;

    private final List<ConferenceRoom> roomsList = new ArrayList<>();

    private final List<ConferenceRoomDTO> roomDTOList = new ArrayList<>();

    private final List<Reservation> reservationsList = new ArrayList<>();

    private final List<ReservationDTO> reservationDTOList = new ArrayList<>();

    private final Organization testOrganization = new Organization("Organization 1", roomsList);

    private final OrganizationDTO testOrganizationDTO = new OrganizationDTO("Organization 1", roomDTOList);

    private final ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, testOrganization);

    private final ConferenceRoomDTO testConferenceRoomDTO = new ConferenceRoomDTO("Conference Room 1", 10, true, 10, testOrganizationDTO);

    private final ConferenceRoom newTestConferenceRoom = new ConferenceRoom("Conference Room 2", "Optional Id 2", 1, false, 100, 50, 10, reservationsList, testOrganization, null);

    private final ConferenceRoomDTO newTestConferenceRoomDTO = new ConferenceRoomDTO("Conference Room 2", "Optional Id 2", 1, false, 100, 50, 10, reservationDTOList, testOrganizationDTO, null);

    private final Equipment testEquipment = new Equipment("Hitachi", false, testConferenceRoom);

    private final EquipmentDTO testEquipmentDTO = new EquipmentDTO("Hitachi", false, testConferenceRoomDTO);


    @Before
    public void setUpTestConferenceRoomService() {
        this.testConferenceRoomService = new ConferenceRoomService(testConferenceRoomRepository, testOrganizationRepository, testMapper, testConferenceRoomDataChange);
    }

    @Test
    public void saveWhenOrganizationIdExistsAndConferenceRoomNameNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        when(testConferenceRoomRepository.findByName(testConferenceRoom.getName())).thenReturn(null);
        //when
        testConferenceRoomService.save(newTestConferenceRoomDTO, testConferenceRoom.getOrganization().getId());
        //then
        verify(testConferenceRoomRepository, times(1)).save(any(ConferenceRoom.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenOrganizationIdNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        when(testConferenceRoomRepository.findByName(testConferenceRoom.getName())).thenReturn(null);
        //when
        testConferenceRoomService.save(newTestConferenceRoomDTO, 10L);
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenConferenceRoomNameExists() {
        //given
        testOrganizationRepository.save(testOrganization);
        when(testConferenceRoomRepository.findByName(testConferenceRoom.getName())).thenReturn(testConferenceRoom);
        var newTestConferenceRoomDTO = new ConferenceRoomDTO("Conference Room 1", 1, false, 100, new OrganizationDTO("Organization 1"));
        //when
        testConferenceRoomService.save(newTestConferenceRoomDTO, testConferenceRoom.getOrganization().getId());
        //then
    }

    @Test
    public void updateWhenConferenceRoomIdExists() {
        //given
        when(testConferenceRoomRepository.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        newTestConferenceRoomDTO.setEquipment(testEquipmentDTO);
        //when
        testConferenceRoomService.update(newTestConferenceRoomDTO, testId);
        //then
        assertEquals(testConferenceRoom.getName(), newTestConferenceRoom.getName());
        assertEquals(testConferenceRoom.getFloor(), newTestConferenceRoom.getFloor());
        assertEquals(testConferenceRoom.isAvailable(), newTestConferenceRoom.isAvailable());
        assertEquals(testConferenceRoom.getSittingAndStandingPlaces(), newTestConferenceRoom.getSittingAndStandingPlaces());
        assertEquals(testConferenceRoom.getLyingPlaces(), newTestConferenceRoom.getLyingPlaces());
        assertEquals(testConferenceRoom.getHangingPlaces(), newTestConferenceRoom.getHangingPlaces());
        assertEquals(testConferenceRoom.getReservations(), newTestConferenceRoom.getReservations());
        assertEquals(testConferenceRoom.getEquipment(), newTestConferenceRoom.getEquipment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWhenConferenceRoomIdNotExist() {
        //given
        when(testConferenceRoomRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.update(newTestConferenceRoomDTO, testId);
        //then
    }

    @Test
    public void deleteWhenOrganizationIdExists() {
        //given
        when(testConferenceRoomRepository.findById(testId)).thenReturn(Optional.ofNullable(newTestConferenceRoom));
        //when
        testConferenceRoomService.delete(testId);
        //then
        verify(testConferenceRoomRepository, times(1)).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenOrganizationIdNotExist() {
        //given
        when(testConferenceRoomRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testConferenceRoomService.delete(testId);
        //then
    }
}