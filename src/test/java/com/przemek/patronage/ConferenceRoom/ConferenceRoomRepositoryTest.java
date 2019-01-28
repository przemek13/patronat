package com.przemek.patronage.ConferenceRoom;

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

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConferenceRoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ConferenceRoomRepository testConferenceRoomRepository;

    private ConferenceRoom testConferenceRoom = new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1"));

    @Test
    public void saveWhenNoData() {
        //given
        //when
        List<ConferenceRoom> testConferenceRooms = testConferenceRoomRepository.findAll();
        //then
        Assert.assertEquals(0, testConferenceRooms.size());
    }

    @Test
    public void saveAndReturn() {
        //given
        //when
        testConferenceRoomRepository.save(testConferenceRoom);
        var testComparedConferenceRoom = testConferenceRoomRepository.findByName("Conference Room 1");
        //then
        Assert.assertEquals(testConferenceRoom, testComparedConferenceRoom);
    }

    @Test
    public void PersistAndReflectListSize() {
        //given
        //when
        entityManager.persist(testConferenceRoom);
        List<ConferenceRoom> testOrganizations = testConferenceRoomRepository.findAll();
        //then
        Assert.assertEquals(1, testOrganizations.size());
    }
}