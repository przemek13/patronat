package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.Organization.Organization;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EquipmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EquipmentRepository testEquipmentRepository;

    Equipment testEquipment = new Equipment("Hitachi", false, new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));

    @Test
    public void saveWhenNoData() {
        //given
        //when
        List<Equipment> testEquipmentList = testEquipmentRepository.findAll();
        //then
        Assert.assertEquals(0, testEquipmentList.size());
    }

    @Test
    public void saveAndReturn() {
        //given
        //when
        testEquipmentRepository.save(testEquipment);
        var testComparedEquipment = testEquipmentRepository.findById(testEquipment.getId());
        //then
        Assert.assertEquals(Optional.of(testEquipment), testComparedEquipment);
    }

    @Test
    public void PersistAndReflectListSize() {
        //given
        //when
        entityManager.persist(testEquipment);
        List<Equipment> testEquipmentList = testEquipmentRepository.findAll();
        //then
        Assert.assertEquals(1, testEquipmentList.size());
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.flush();
    }

}