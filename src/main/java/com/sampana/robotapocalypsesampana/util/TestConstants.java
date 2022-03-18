package com.sampana.robotapocalypsesampana.util;

import com.sampana.robotapocalypsesampana.model.*;
import com.sampana.robotapocalypsesampana.model.enums.Gender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

public class TestConstants {

    public static Survivor getTestSurvivor() {
        Survivor survivor = new Survivor();
        survivor.setUuid("test-uuid");
        survivor.setAge(40);
        survivor.setGender(Gender.Male);
        survivor.setLocation(new Location());
        survivor.setName("test-name");
        survivor.setResource(new Resource());
        return survivor;
    }

    public static List<Survivor> getTestSurvivors() {
        List<Survivor> testSurvivors = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            Survivor survivor = new Survivor();
            survivor.setUuid("test-uuid-" + i);
            survivor.setAge(40 + i);
            if (i % 2 == 0)
                survivor.setGender(Gender.Male);
            else
                survivor.setGender(Gender.Female);
            if (i % 2 == 0)
                survivor.setInfected(true);
            else
                survivor.setInfected(false);
            survivor.setLocation(new Location());
            survivor.setName("test-name-" + i);
            survivor.setResource(new Resource());
            testSurvivors.add(survivor);
        }
        return testSurvivors;
    }

    public static List<Robot> getRobots() {
        List<Robot> robots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Robot robot = new Robot();
            if (i % 2 == 0)
                robot.setCategory("Land");
            else
                robot.setCategory("Flying");
            robot.setManufacturedDate(LocalDateTime.now());
            robot.setModel("Tesla");
            robot.setSerialNumber("000" + i);
            robots.add(robot);
        }
        return robots;
    }

    public static Report getReport() {
        Report report = new Report();
        List<Survivor> infectedSurvivors = new ArrayList<>();
        List<Survivor> nonInfectedSurvivors = new ArrayList<>();
        List<Survivor> survivors = getTestSurvivors();
        for (Survivor survivor : survivors) {
            if (survivor.isInfected())
                infectedSurvivors.add(survivor);
            else
                nonInfectedSurvivors.add(survivor);
        }
        report.setInfectedSurvivorsPercentage(50.0);
        report.setNonInfectedSurvivorsPercentage(50.0);
        report.setInfectedSurvivors(infectedSurvivors);
        report.setNonInfectedSurvivors(nonInfectedSurvivors);
        report.setRobots(getRobots());
        return report;
    }
}
