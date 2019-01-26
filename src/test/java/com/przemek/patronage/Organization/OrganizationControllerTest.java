package com.przemek.patronage.Organization;

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
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {

//    @Autowired
//    private static OrganizationRepository testOrganizations;
//
//    @TestConfiguration
//    public class OrganizationServiceImplTestContextConfiguration {
//        @Bean
//        public OrganizationService testOrganizationService() {
//            return new OrganizationService(testOrganizations);
//        }
//    }
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private OrganizationService testService;
//
//    @Test
//    public void getOrganizations() throws Exception {
//        //given
//        when(testService.findAll()).thenReturn(Collections.singletonList(new Organization("Organization 1")));
//        //when
//        mvc.perform(get("/organizations")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
}