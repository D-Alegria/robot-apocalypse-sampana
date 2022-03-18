package com.sampana.robotapocalypsesampana.repository.contract;

import com.sampana.robotapocalypsesampana.model.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Repository
public interface ISurvivorRepository extends JpaRepository<Survivor, Long> {

    Optional<Survivor> findByUuid(String uuid);
    Optional<Survivor> findByNameIgnoreCase(String uuid);
}
