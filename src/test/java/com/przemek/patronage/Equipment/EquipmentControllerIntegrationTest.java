package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.PatronageApplication;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PatronageApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EquipmentControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ConferenceRoomRepository testConferenceRoomRepository;
    @Autowired
    private EquipmentRepository testEquipmentRepository;

    @After
    public void resetEquipmentDb() {
        testEquipmentRepository.deleteAll();
    }

    @Test
    public void getEquipment() throws Exception {
        //given
        testEquipmentRepository.save(new Equipment("Hitachi", false, new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 1"))));
        //when
        //then
        mvc.perform(get("/equipment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

