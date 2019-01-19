package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.Organization.Organization;
import lombok.var;
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
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository testReservationRepository;

    Reservation testReservation = new Reservation("Reserving 1", "2019-03-23T16:00:00", "2019-03-23T17:00:00", new ConferenceRoom("Conference Room 1", 5, true, 5, new Organization("Organization 1")));

    @Test
    public void saveWhenNoData() {
        //given
        //when
        List<Reservation> testReservations = testReservationRepository.findAll();
        //then
        Assert.assertEquals(0, testReservations.size());
    }

    @Test
    public void saveAndReturn() {
        //given
        //when
        testReservationRepository.save(testReservation);
        var testComparedReservation = testReservationRepository.findByReservingId("Reserving 1");
        //then
        Assert.assertEquals(testReservation, testComparedReservation);
    }

    @Test
    public void PersistAndReflectListSize() {
        //given
        //when
        entityManager.persist(testReservation);
        List<Reservation> testReservations = testReservationRepository.findAll();
        //then
        Assert.assertEquals(1, testReservations.size());
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.flush();
    }

}