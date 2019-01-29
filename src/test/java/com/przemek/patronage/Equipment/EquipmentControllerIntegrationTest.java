package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.PatronageApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void resetDb() {
        testEquipmentRepository.deleteAll();
    }

    @Test
    public void getEquipment() throws Exception {
        //given
        testEquipmentRepository.save(new Equipment("Hitachi", false, new ConferenceRoom("Conference Room 1", 10, true, 10, new Organization("Organization 2"))));
        //when
        //then
        mvc.perform(get("/equipment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addEquipmentWhenRecordValid() throws Exception {
        //given
        testConferenceRoomRepository.save(new ConferenceRoom("Conference Room 1", 1, false, 100, new Organization("Organization 3")));
        //when
        mvc.perform(post("/equipment/1").contentType(MediaType.APPLICATION_JSON).content(
                "    {\n" + "\"projectorName\": \"BenQ\",\n" + "\"internalNumber\": 0,\n" + "\"externalNumber\": \"+12 123456789\",\n" + "\"connections\": \"BLUETOOTH\",\n" + "\"phone\": true\n" + "}"));
        List<Equipment> equipmentList = testEquipmentRepository.findAll();
        //then
        Assert.assertEquals(equipmentList.size(), 1);
    }

    @Test
    public void addEquipmentWhenRecordInValid() throws Exception {
        //given
        testConferenceRoomRepository.save(new ConferenceRoom("Conference Room 2", 1, false, 100, new Organization("Organization 4")));
        //when
        mvc.perform(post("/equipment/1").contentType(MediaType.APPLICATION_JSON).content(
                "    {\n" + "\"projectorName\": \"BenQ\",\n" + "\"internalNumber\": 0,\n" + "\"externalNumber\": \"+12123456789\",\n" + "\"connections\": \"BLUETOOTH\",\n" + "\"phone\": true\n" + "}"));
        List<Equipment> equipmentList = testEquipmentRepository.findAll();
        //then
        Assert.assertEquals(equipmentList.size(), 0);
    }
}
