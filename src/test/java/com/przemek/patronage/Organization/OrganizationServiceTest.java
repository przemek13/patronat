package com.przemek.patronage.Organization;

import com.przemek.patronage.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationServiceTest {

    @TestConfiguration
    static class MapperImplTestContextConfiguration {
        @Bean
        public Mapper mapper() {
            return new Mapper();
        }
    }

    @TestConfiguration
    static class OrganizationUpdateImplTestContextConfiguration {
        @Bean
        public OrganizationDataChange organizationDataChange() {
            return new OrganizationDataChange();
        }
    }

    @Mock
    private OrganizationRepository testOrganizationRepository;
    @Autowired
    private Mapper testMapper;
    @Autowired
    OrganizationDataChange testOrganizationDataChange;

    private OrganizationService testOrganizationService;

    private final Organization testOrganization = new Organization("Organization 1");

    private final Organization newTestOrganization = new Organization("Organization 2");

    private final OrganizationDTO newTestOrganizationDTO = new OrganizationDTO("Organization 2");

    private final Long testId = 1L;

    @Before
    public void setUpTestOrganizationService() {
        this.testOrganizationService = new OrganizationService(testOrganizationRepository, testMapper, testOrganizationDataChange);
    }

    @Test
    public void saveWhenOrganizationNameNotExist() {
        //given
        when(testOrganizationRepository.findByName(newTestOrganization.getName())).thenReturn(null);
        //when
        testOrganizationService.save(newTestOrganizationDTO);
        //then
        verify(testOrganizationRepository, times(1)).save(any(Organization.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenOrganizationNameExists() {
        //given
        when(testOrganizationRepository.findByName(newTestOrganization.getName())).thenReturn(newTestOrganization);
        //when
        testOrganizationService.save(newTestOrganizationDTO);
        //then
    }

    @Test
    public void updateWhenOrganizationIdExists() {
        //given
        when(testOrganizationRepository.findById(testId)).thenReturn((Optional.ofNullable(testOrganization)));
        //when
        testOrganizationService.update(newTestOrganizationDTO, testId);
        //then
        assertEquals(testOrganization.getName(), newTestOrganization.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWhenOrganizationIdNotExist() {
        //given
        when(testOrganizationRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testOrganizationService.update(newTestOrganizationDTO, testId);
        //then
    }

    @Test
    public void deleteWhenOrganizationIdExists() {
        //given
        when(testOrganizationRepository.findById(testId)).thenReturn(Optional.ofNullable(newTestOrganization));
        //when
        testOrganizationService.delete(testId);
        //then
        verify(testOrganizationRepository, times(1)).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenOrganizationIdNotExist() {
        //given
        when(testOrganizationRepository.findById(testId)).thenReturn(Optional.empty());
        //when
        testOrganizationService.delete(testId);
        //then
    }
}