package com.jeyofdev.yellow_berry.core.classes;

import com.jeyofdev.yellow_berry.core.interfaces.domain.DomainService;
import com.jeyofdev.yellow_berry.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AbstractDomainService<T, R extends JpaRepository<T, UUID>> implements DomainService<T> {
    private final R repository;
    private final String entityName;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(UUID entityId) throws NotFoundException {
        return repository.findById(entityId).orElseThrow(
            () -> new NotFoundException(MessageFormat.format("Entity {0} with id {1} cannot be found", entityName, entityId)));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T updateById(UUID entityId, T updatedEntity) {
        throw new UnsupportedOperationException("updateById is not implemented.");
    }

    @Override
    public String deleteById(UUID entityId) {
        throw new UnsupportedOperationException("deleteById is not implemented.");
    }
}
