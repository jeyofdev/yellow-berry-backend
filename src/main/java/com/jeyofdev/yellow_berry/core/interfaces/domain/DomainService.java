package com.jeyofdev.yellow_berry.core.interfaces.domain;

import com.jeyofdev.yellow_berry.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface DomainService<T> {
    List<T> findAll();

    T findById(UUID entityId) throws NotFoundException;

    T save(T faq);

    T updateById(UUID entityId, T updatedEntity);

    String deleteById(UUID entityId);
}
