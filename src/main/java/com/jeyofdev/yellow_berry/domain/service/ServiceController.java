package com.jeyofdev.yellow_berry.domain.service;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.service.dto.SaveServiceDTO;
import com.jeyofdev.yellow_berry.domain.service.dto.ServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceImpl serviceImpl;
    private final ServiceMapper serviceMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ServiceDTO>>> findAllService() {
        List<Service> serviceList = serviceImpl.findAll();
        List<ServiceDTO> serviceDTOList = serviceList.stream().map(serviceMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, serviceDTOList);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<DomainSuccessResponse<ServiceDTO>> findServiceById(@PathVariable("serviceId") UUID serviceId) {
        Service service = serviceImpl.findById(serviceId);
        ServiceDTO serviceDTO = serviceMapper.mapFromEntity(service);

        return DomainSuccessResponse.get(HttpStatus.OK, serviceDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<ServiceDTO>> saveService(@RequestBody SaveServiceDTO saveServiceDTO) {
        Service service = serviceMapper.mapToEntity(saveServiceDTO);
        Service newService = serviceImpl.save(service);
        ServiceDTO newServiceDTO = serviceMapper.mapFromEntity(newService);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newServiceDTO);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<DomainSuccessResponse<ServiceDTO>> updateServiceById(
            @PathVariable("serviceId") UUID serviceId,
            @RequestBody SaveServiceDTO saveServiceDTO
    ) {
        Service service = serviceMapper.mapToEntity(saveServiceDTO);
        Service updateService = serviceImpl.updateById(serviceId, service);
        ServiceDTO updateServiceDTO = serviceMapper.mapFromEntity(updateService);

        return DomainSuccessResponse.get(HttpStatus.OK, updateServiceDTO);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteServiceById(@PathVariable("serviceId") UUID serviceId) {
        String message = serviceImpl.deleteById(serviceId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
