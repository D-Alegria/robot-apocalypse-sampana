package com.sampana.robotapocalypsesampana.repository.contract;

import org.springframework.data.domain.Page;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

public interface IRepository<T> {

    T create(T t);

    Page<T> getAll(int pageNum, int pageSize);

    T getById(long id);

    T update(T t);

    void delete(long id);
}
