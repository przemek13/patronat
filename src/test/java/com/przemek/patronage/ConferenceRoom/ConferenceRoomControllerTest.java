package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.OrganizationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ConferenceRoomService testConferenceRoomService;

    @Test
    public void getConferenceRooms() throws Exception {
        //given
        when(testConferenceRoomService.findAll()).thenReturn(Collections.singletonList(new ConferenceRoomDTO("Conference Room 1", 10, true, 10, new OrganizationDTO("Organization 1"))));
        //when
        mvc.perform(get("/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}