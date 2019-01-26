package com.przemek.patronage.Reservation;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository testReservationRepository;

    Reservation testReservation = new Reservation("Reserving 1", LocalDateTime.of(2019, 3, 23, 16, 00), LocalDateTime.of(2019, 3, 23, 17, 00), new ConferenceRoom("Conference Room 1", 1, true, 10, new Organization("Organization 1")));

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
        var testComparedReservation = testReservationRepository.findById(testReservation.getId());
        //then
        Assert.assertEquals(Optional.of(testReservation), testComparedReservation);
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