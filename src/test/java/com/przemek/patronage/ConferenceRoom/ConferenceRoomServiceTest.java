package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Organization.OrganizationDataChange;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    private Long testId = 1L;

    List<ConferenceRoom> roomsList = new ArrayList<>();

    private Organization testOrganization = new Organization("Organization 1", roomsList);

    private ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 10, true, 10, testOrganization);

    private ConferenceRoom newTestConferenceRoom = new ConferenceRoom ("Conference Room 2", 1, false, 100, new Organization("Organization 2"));

    private ConferenceRoomDTO newTestConferenceRoomDTO = new ConferenceRoomDTO("Conference Room 2", 1, false, 100, new OrganizationDTO("Organization 2"));

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
        var newTestConferenceRoomDTO = new ConferenceRoomDTO ("Conference Room 1", 1, false, 100, new OrganizationDTO("Organization 1"));
        //when
        testConferenceRoomService.save(newTestConferenceRoomDTO, testConferenceRoom.getOrganization().getId());
        //then
    }

    @Test
    public void updateWhenConferenceRoomIdExists() {
        //given
        when(testConferenceRoomRepository.findById(testId)).thenReturn(Optional.ofNullable(testConferenceRoom));
        //when
        testConferenceRoomService.update(newTestConferenceRoomDTO, testId);
        //then
        assertEquals(testConferenceRoom.getName(), (newTestConferenceRoom.getName()));
        assertSame(testConferenceRoom.getFloor(), (newTestConferenceRoom.getFloor()));
        assertSame(testConferenceRoom.isAvailable(), (newTestConferenceRoom.isAvailable()));
        assertSame(testConferenceRoom.getSittingAndStandingPlaces(), (newTestConferenceRoom.getSittingAndStandingPlaces()));
    }

    @Test (expected = IllegalArgumentException.class)
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