package com.sampana.robotapocalypsesampana.util;

import com.sampana.robotapocalypsesampana.model.Location;
import com.sampana.robotapocalypsesampana.model.Resource;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.model.enums.Gender;

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
}
