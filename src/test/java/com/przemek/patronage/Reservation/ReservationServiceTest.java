package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    static class ReservationUpdateImplTestContextConfiguration {
        @Bean
        public ReservationDataChange reservationDataChange() {
            return new ReservationDataChange();
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
    private ConferenceRoomRepository testConferenceRoomRepository;
    @Autowired
    private Mapper testMapper;
    @Autowired
    private ReservationCheck reservationCheck;
    @Mock
    private ReservationRepository testReservationRepository;
    @Autowired
    private OrganizationRepository testOrganizationRepository;
    @Autowired
    private ReservationDataChange testReservationDataChange;

    private final List<ConferenceRoom> roomsList = new ArrayList<>();

    private final List<Reservation> reservationsList = new ArrayList<>();

    private final Organization testOrganization = new Organization("Organization 1", roomsList);

    private final ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, testOrganization, reservationsList);

    private final Reservation testReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 0), LocalDateTime.of(2019, 3, 23, 17, 0), testConferenceRoom);

    private final Reservation newTestReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 0), LocalDateTime.of(2019, 3, 23, 17, 0), new ConferenceRoom("Conference Room 2", 1, true, 10, new Organization("Organization 2")));

    private final ReservationDTO newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 17, 0), LocalDateTime.of(2019, 3, 23, 18, 0), new ConferenceRoomDTO("Conference Room 2", 1, true, 10, new OrganizationDTO("Organization 2")));

    private final Long testId = 1L;

    @Before
    public void setUpTestReservationService() {
        this.testReservationService = new ReservationService(testReservationRepository, testConferenceRoomRepository, testOrganizationRepository, testMapper, reservationCheck, testReservationDataChange);
    }

    @Test
    public void findForConcreteConferenceRoom() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        roomsList.add(testConferenceRoom);
        reservationsList.add(testReservation);
        //when
        List<ReservationDTO> reservationDTOList = testReservationService.findForConcreteConferenceRoom(testOrganization.getId(), testConferenceRoom.getId());
        //then
        assertEquals(1, reservationDTOList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findForConcreteConferenceRoomWhenOrganizationIdNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        roomsList.add(testConferenceRoom);
        reservationsList.add(testReservation);
        //when
        testReservationService.findForConcreteConferenceRoom(10L, testConferenceRoom.getId());
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void findForConcreteConferenceRoomWhenConferenceRoomIdNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        roomsList.add(testConferenceRoom);
        reservationsList.add(testReservation);
        //when
        testReservationService.findForConcreteConferenceRoom(testOrganization.getId(), 10L);
        //then
    }

    @Test
    public void saveWhenConferenceRoomIdExists() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
        verify(testReservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenConferenceRoomIdNotExist() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        //when
        testReservationService.save(newTestReservationDTO, 10L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationStartAfterReservationDate() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 15, 0), LocalDateTime.of(2019, 3, 23, 14, 0), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationDurationTooShort() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 14, 0), LocalDateTime.of(2019, 3, 23, 14, 5), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenReservationDurationTooLong() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 10, 0), LocalDateTime.of(2019, 3, 23, 12, 1), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = RoomReservedException.class)
    public void saveWhenReservationDuringTheSamePeriod() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 0), LocalDateTime.of(2019, 3, 23, 17, 0), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = RoomReservedException.class)
    public void saveWhenReservationStartsDuringOther() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 30), LocalDateTime.of(2019, 3, 23, 17, 30), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test(expected = RoomReservedException.class)
    public void saveWhenReservationEndsDuringOther() {
        //given
        testOrganizationRepository.save(testOrganization);
        testConferenceRoomRepository.save(testConferenceRoom);
        reservationsList.add(testReservation);
        var newTestReservationDTO = new ReservationDTO("Reserving 1", LocalDateTime.of(2019, 3, 23, 15, 30), LocalDateTime.of(2019, 3, 23, 16, 30), new ConferenceRoomDTO("Conference Room 1", 1, true, 10, new OrganizationDTO("Organization 1")));
        //when
        testReservationService.save(newTestReservationDTO, testConferenceRoom.getId());
        //then
    }

    @Test
    public void updateWhenReservationIdExists() {
        //given
        when(testReservationRepository.findById(testId)).thenReturn(Optional.ofNullable(testReservation));
        //when
        testReservationService.update(newTestReservationDTO, testId);
        //then
        assertEquals(testReservation.getReservingId(), newTestReservationDTO.getReservingId());
        assertEquals(testReservation.getReservationStart(), newTestReservationDTO.getReservationStart());
        assertEquals(testReservation.getReservationEnd(), newTestReservationDTO.getReservationEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWhenReservationIdNotExist() {
        //given
        when(testReservationRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.update(newTestReservationDTO, testId);
        //then
    }

    @Test
    public void deleteWhenReservationIdExists() {
        //given
        when(testReservationRepository.findById(testId)).thenReturn(Optional.ofNullable(newTestReservation));
        //when
        testReservationService.delete(testId);
        //then
        verify(testReservationRepository, times(1)).deleteById(testId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenReservationIdNotExist() {
        //given
        when(testReservationRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testReservationService.delete(testId);
        //then
    }
}