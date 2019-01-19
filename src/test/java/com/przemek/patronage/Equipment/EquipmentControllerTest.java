package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.Organization;
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
@WebMvcTest(EquipmentController.class)
public class EquipmentControllerTest {

    @Autowired
    private static EquipmentRepository testEquipment;

    @Autowired
    private static ConferenceRoomRepository testConferenceRooms;

    @TestConfiguration
    public class ConferenceRoomServiceImplTestContextConfiguration {
        @Bean
        public EquipmentService equipmentService() {
            return new EquipmentService(testEquipment, testConferenceRooms);
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EquipmentService testService;

    @Test
    public void getEquipment() throws Exception {
        //given
        when(testService.findAll()).thenReturn(Collections.singletonList(new Equipment("Hitachi", false, new ConferenceRoom("Conference Room 4", 10, true, 10, new Organization("Organization 5")))));
        //when
        mvc.perform(get("/equipment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}