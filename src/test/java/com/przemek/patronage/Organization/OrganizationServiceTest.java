package com.przemek.patronage.Organization;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceTest {

    private OrganizationService testOrganizationService;
    @Mock
    private OrganizationRepository testOrganizations;
    @Mock
    private Organization testOrganization;
    @Mock
    private Organization newTestOrganization;

    private Long testId = 1L;

    @Before
    public void setUpTestOrganizationService() {
        this.testOrganizationService = new OrganizationService(testOrganizations);
    }

    @Test
    public void saveWhenOrganizationNameNotExist() throws SameNameException {
        //given
        when(testOrganizations.findByName(newTestOrganization.getName())).thenReturn(null);
        //when
        testOrganizationService.save(newTestOrganization);
        //then
        verify(testOrganizations, times(1)).save(newTestOrganization);
    }

    @Test (expected = SameNameException.class)
    public void saveWhenOrganizationNameExists() throws SameNameException {
        //given
        Organization newTestOrganization = new Organization("Test Organization 1");
        when(testOrganizations.findByName(newTestOrganization.getName())).thenReturn(newTestOrganization);
        //when
        testOrganizationService.save(newTestOrganization);
        //then
    }

    @Test
    public void updateWhenOrganizationIdExists()  {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.ofNullable(testOrganization));
        //when
        testOrganizationService.update(newTestOrganization, testId);
        //then
        verify(testOrganization, times(1)).setName(newTestOrganization.getName());
    }

    @Test
    public void updateWhenOrganizationIdNotExist()  {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.empty());
        //when
        testOrganizationService.update(newTestOrganization, testId);
        //then
        verify(newTestOrganization, times(1)).setId(testId);
    }

    @Test
    public void deleteWhenOrganizationIdExists() throws NoSuchIdException {
        //given
        when(testOrganizations.findById(testId)).thenReturn(Optional.ofNullable(newTestOrganization));
        //when
        testOrganizationService.delete(testId);
        //then
        verify(testOrganizations, times(1)).deleteById(1L);
    }

    @Test (expected = NoSuchIdException.class)
    public void deleteWhenOrganizationIdNotExist() throws NoSuchIdException {
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