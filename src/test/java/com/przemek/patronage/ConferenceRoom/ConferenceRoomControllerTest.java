package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ConferenceRoomController.class)
public class ConferenceRoomControllerTest {

//    @Autowired
//    private static OrganizationRepository testOrganizations;
//
//    @Autowired
//    private static ConferenceRoomRepository testConferenceRooms;
//
//    @TestConfiguration
//    public class ConferenceRoomServiceImplTestContextConfiguration {
//        @Bean
//        public ConferenceRoomService conferenceRoomServiceService() {
//            return new ConferenceRoomService(testConferenceRooms, testOrganizations);
//        }
//    }
//
//    @TestConfiguration
//    static class MapperImplTestContextConfiguration {
//        @Bean
//        public Mapper mapper() {
//            return new Mapper();
//        }
//    }
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private ConferenceRoomService testService;
//
//    @Test
//    public void getConferenceRooms() throws Exception {
//        //given
//        when(testService.findAll()).thenReturn(Collections.singletonList(new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1"))));
//        //when
//        mvc.perform(get("/rooms")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
}