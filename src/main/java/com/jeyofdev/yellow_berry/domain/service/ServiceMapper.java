package com.jeyofdev.yellow_berry.domain.service;

import com.jeyofdev.yellow_berry.domain.service.dto.ServiceDTO;
import com.jeyofdev.yellow_berry.domain.service.dto.SaveServiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO mapFromEntity(Service service);
    Service mapToEntity(SaveServiceDTO saveServiceDTO);
}
