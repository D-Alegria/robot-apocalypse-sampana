package com.sampana.robotapocalypsesampana.repository;

import com.sampana.robotapocalypsesampana.RobotApocalypseSampanaApplication;
import com.sampana.robotapocalypsesampana.exception.NotFoundException;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.repository.contract.ISurvivorRepository;
import com.sampana.robotapocalypsesampana.util.TestConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RobotApocalypseSampanaApplication.class)
@SpringBootTest
class SurvivorRepositoryTest {

    Survivor expectedSurvivor;
    @Autowired
    private ISurvivorRepository iSurvivorRepository;
    @Autowired
    private SurvivorRepository survivorRepository;

    @BeforeEach
    void setUp() {
        expectedSurvivor = iSurvivorRepository.save(TestConstants.getTestSurvivor());
    }

    @AfterEach
    void tearDown() {
        iSurvivorRepository.deleteById(expectedSurvivor.getId());
    }

    @Test
    void create() {
        // arrange
        Survivor test = TestConstants.getTestSurvivor();
        test.setUuid("test-uuid-unique");
        // act
        Survivor actualSurvivor = survivorRepository.create(test);
        // assert
        assertTrue(actualSurvivor.getId() > 0);
        assertEquals(test.getUuid(), actualSurvivor.getUuid());
        // close
        iSurvivorRepository.deleteById(actualSurvivor.getId());
    }

    @Test
    void getAll() {
        // act
        Page<Survivor> actualSurvivor = survivorRepository.getAll(1, 3);
        // assert
        assertTrue(actualSurvivor.getTotalElements() > 0);
    }

    @Test
    void getById() {
        // act
        Survivor actualSurvivor = survivorRepository.getById(expectedSurvivor.getId());
        // assert
        assertEquals(expectedSurvivor.getUuid(), actualSurvivor.getUuid());

        // assert
        assertThrows(NotFoundException.class, () -> survivorRepository.getById(10000000009090L));
    }

    @Test
    void update() {
        // arrange
        expectedSurvivor.setUuid("update_uuid");
        // act
        Survivor actualSurvivor = survivorRepository.update(expectedSurvivor);
        // assert
        assertEquals(expectedSurvivor.getUuid(), actualSurvivor.getUuid());
    }

    @Test
    void delete() {
        // arrange
        Survivor test = TestConstants.getTestSurvivor();
        test.setUuid("test-uuid-unique");
        test = iSurvivorRepository.save(test);
        // act
        survivorRepository.delete(test.getId());
        // assert
        assertFalse(iSurvivorRepository.existsById(test.getId()));
    }

    @Test
    void getByUuid() {
        // act
        Survivor actualSurvivor = survivorRepository.getByUuid(expectedSurvivor.getUuid());
        // assert
        assertEquals(expectedSurvivor.getUuid(), actualSurvivor.getUuid());

        // assert
        assertThrows(NotFoundException.class, () -> survivorRepository.getByUuid("unknown"));
    }
}