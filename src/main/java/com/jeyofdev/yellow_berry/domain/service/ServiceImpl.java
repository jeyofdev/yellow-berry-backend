package com.jeyofdev.yellow_berry.domain.service;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceImpl extends AbstractDomainService<Service, ServiceRepository> {
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceImpl(ServiceRepository serviceRepository) {
        super(serviceRepository, "Service");
        this.serviceRepository = serviceRepository;
    }

    public Service updateById(UUID serviceId, Service updatedService) {
        Service existingService = findById(serviceId);
        Service existingServiceUpdated = Service.builder()
                .id(serviceId)
                .name(updatedService.getName() != null ? updatedService.getName() : existingService.getName())
                .description(updatedService.getDescription() != null ? updatedService.getDescription() : existingService.getDescription())
                .build();

        return serviceRepository.save(existingServiceUpdated);
    }

    public String deleteById(UUID serviceId) {
        findById(serviceId);
        serviceRepository.deleteById(serviceId);

        return ConfirmMessage.SERVICE_DELETE;
    }
}
