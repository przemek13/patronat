package com.przemek.patronage.Organization;

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
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrganizationService testOrganizationService;

    @Test
    public void getOrganizations() throws Exception {
        //given
        when(testOrganizationService.findAll()).thenReturn(Collections.singletonList(new OrganizationDTO("Organization 1")));
        //when
        //then
        mvc.perform(get("/organizations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}