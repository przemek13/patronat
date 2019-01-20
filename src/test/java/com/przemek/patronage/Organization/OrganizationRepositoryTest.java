package com.przemek.patronage.Organization;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizationRepository testOrganizationRepository;

    Organization testOrganization = new Organization("Organization 1");

    @Test
    public void returnWhenNoData() {
        //given
        //when
        List<Organization> testOrganizations = testOrganizationRepository.findAll();
        //then
        Assert.assertEquals(0, testOrganizations.size());
    }

    @Test
    public void saveAndReturn() {
        //given
        //when
        testOrganizationRepository.save(testOrganization);
        var testComparedOrganization = testOrganizationRepository.findByName("Organization 1");
        //then
        Assert.assertEquals(testOrganization, testComparedOrganization);
    }

    @Test
    public void PersistAndReflectListSize() {
        //given
        //when
        entityManager.persist(testOrganization);
        List<Organization> testOrganizations = testOrganizationRepository.findAll();
        //then
        Assert.assertEquals(1, testOrganizations.size());
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.flush();
    }
}