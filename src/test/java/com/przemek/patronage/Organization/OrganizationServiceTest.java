package com.przemek.patronage.Organization;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceTest {

    private OrganizationService testOrganizationService;

    @Mock
    private OrganizationRepository testOrganizations;

    private Organization testOrganization = new Organization("Organization 1");

    private Organization newTestOrganization = new Organization("Organization 2");

    private Long testId = 1L;

    @Before
    public void setUpTestOrganizationService() {
        this.testOrganizationService = new OrganizationService(testOrganizations);
    }

    @Test
    public void saveWhenOrganizationNameNotExist() {
        //given
        when(testOrganizations.findByName(newTestOrganization.getName())).thenReturn(null);
        //when
        testOrganizationService.save(newTestOrganization);
        //then
        verify(testOrganizations, times(1)).save(newTestOrganization);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveWhenOrganizationNameExists() {
        //given
        when(testOrganizations.findByName(newTestOrganization.getName())).thenReturn(newTestOrganization);
        //when
        testOrganizationService.save(newTestOrganization);
        //then
    }

    @Test
    public void updateWhenOrganizationIdExists() {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.ofNullable(testOrganization));
        //when
        testOrganizationService.update(newTestOrganization, testId);
        //then
        assertEquals(testOrganization.getName(), newTestOrganization.getName());
    }

    @Test
    public void updateWhenOrganizationIdNotExist() {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.empty());
        //when
        testOrganizationService.update(newTestOrganization, testId);
        //then
        assertEquals(newTestOrganization.getId(), (testId));
    }

    @Test
    public void deleteWhenOrganizationIdExists() {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.ofNullable(newTestOrganization));
        //when
        testOrganizationService.delete(testId);
        //then
        verify(testOrganizations, times(1)).deleteById(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWhenOrganizationIdNotExist() {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.empty());
        //when
        testOrganizationService.delete(testId);
        //then
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("tear down class");
        System.out.flush();
    }
}