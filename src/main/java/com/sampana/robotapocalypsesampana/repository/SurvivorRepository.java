package com.sampana.robotapocalypsesampana.repository;

import com.sampana.robotapocalypsesampana.exception.NotFoundException;
import com.sampana.robotapocalypsesampana.model.Survivor;
import com.sampana.robotapocalypsesampana.repository.contract.IRepository;
import com.sampana.robotapocalypsesampana.repository.contract.ISurvivorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

@Service
public class SurvivorRepository implements IRepository<Survivor> {

    private final ISurvivorRepository iSurvivorRepository;

    public SurvivorRepository(ISurvivorRepository iSurvivorRepository) {
        this.iSurvivorRepository = iSurvivorRepository;
    }

    @Override
    public Survivor create(Survivor survivor) {
        return iSurvivorRepository.save(survivor);
    }

    @Override
    public Page<Survivor> getAll(int pageNum, int pageSize) {
        if (pageNum > 0) {
            return iSurvivorRepository.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(new Sort.Order(Sort.Direction.DESC, "id"))));
        } else {
            List<Survivor> ts = iSurvivorRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
            return new PageImpl<>(ts);
        }
    }

    @Override
    public Survivor getById(long id) {
        Optional<Survivor> optionalSurvivor = iSurvivorRepository.findById(id);
        return optionalSurvivor.orElseThrow(() -> new NotFoundException("Survivor " + id + " not found"));
    }

    public Survivor getByUuid(String uuid) {
        Optional<Survivor> optionalSurvivor = iSurvivorRepository.findByUuid(uuid);
        return optionalSurvivor.orElseThrow(() -> new NotFoundException("Survivor " + uuid + " not found"));
    }

    @Override
    public Survivor update(Survivor survivor) {
        if (iSurvivorRepository.findById(survivor.getId()).isEmpty())
            throw new NotFoundException("Survivor not found");

        return iSurvivorRepository.save(survivor);
    }

    @Override
    public void delete(long id) {
        if (iSurvivorRepository.findById(id).isEmpty())
            throw new NotFoundException("Survivor not found");

        iSurvivorRepository.deleteById(id);
    }
}
