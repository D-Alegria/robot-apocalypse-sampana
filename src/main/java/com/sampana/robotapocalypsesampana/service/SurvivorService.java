package com.sampana.robotapocalypsesampana.service;

import com.sampana.robotapocalypsesampana.exception.BadRequestException;
import com.sampana.robotapocalypsesampana.exception.RequestAlreadyPerformedException;
import com.sampana.robotapocalypsesampana.model.Location;
import com.sampana.robotapocalypsesampana.model.Resource;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.model.request.InfectedReportRequest;
import com.sampana.robotapocalypsesampana.repository.SurvivorRepository;
import com.sampana.robotapocalypsesampana.util.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Service
public class SurvivorService {

    private final SurvivorRepository survivorRepository;

    public SurvivorService(SurvivorRepository survivorRepository) {
        this.survivorRepository = survivorRepository;
    }

    public Response<Survivor> addSurvivor(Survivor survivor) {
        if (survivorRepository.getByName(survivor.getName()) != null)
            throw new BadRequestException("Survivor with name " + survivor.getName() + " already exists");
        survivor.setUuid(UUID.randomUUID().toString());
        survivor.setInfected(false);
        survivor.setResource(new Resource());
        Survivor saved = survivorRepository.create(survivor);
        return Utils.successfulResponse(List.of(saved));
    }

    public Response<Survivor> updateSurvivorLocation(String uuid, Location location) {
        Survivor survivor = survivorRepository.getByUuid(uuid);
        location.setId(survivor.getLocation().getId());
        survivor.setLocation(location);
        Survivor updated = survivorRepository.update(survivor);
        return Utils.successfulResponse(List.of(updated));
    }

    public Response<Survivor> reportInfected(InfectedReportRequest request) {
        survivorRepository.getByUuid(request.getInformantUuid());
        Survivor infected = survivorRepository.getByUuid(request.getInfectedUuid());

        if (request.getInfectedUuid().equals(request.getInformantUuid()))
            throw new BadRequestException("Sorry, you cannot report yourself");

        if (infected.isInfected())
            throw new RequestAlreadyPerformedException(String.format("%s as been flag as INFECTED", infected.getName()));

        if (infected.getInformants() == null)
            infected.setInformants(new ArrayList<>());

        if (infected.getInformants().contains(request.getInformantUuid()))
            throw new RequestAlreadyPerformedException("You have reported this infected");

        infected.getInformants().add(request.getInformantUuid());
        if (infected.getInformants().size() >= 3)
            infected.setInfected(true);

        Survivor updated = survivorRepository.update(infected);
        return Utils.successfulResponse(List.of(updated));
    }


}
