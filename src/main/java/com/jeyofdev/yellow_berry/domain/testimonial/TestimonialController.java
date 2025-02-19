package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.testimonial.dto.SaveTestimonialDTO;
import com.jeyofdev.yellow_berry.domain.testimonial.dto.TestimonialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/testimonial")
@RequiredArgsConstructor
public class TestimonialController {
    private final TestimonialService testimonialService;
    private final TestimonialMapper testimonialMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<TestimonialDTO>>> findAllTestimonials() {
        List<Testimonial> testimonialList = testimonialService.findAll();
        List<TestimonialDTO> testimonialDTOList = testimonialList.stream().map(testimonialMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, testimonialDTOList);
    }

    @GetMapping("/{testimonialId}")
    public ResponseEntity<DomainSuccessResponse<TestimonialDTO>> findTestimonialById(@PathVariable("testimonialId") UUID testimonialId) {
        Testimonial testimonial = testimonialService.findById(testimonialId);
        TestimonialDTO testimonialDTO = testimonialMapper.mapFromEntity(testimonial);

        return DomainSuccessResponse.get(HttpStatus.OK, testimonialDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<TestimonialDTO>> saveTestimonial(@RequestBody SaveTestimonialDTO saveTestimonialDTO) {
        Testimonial testimonial = testimonialMapper.mapToEntity(saveTestimonialDTO);
        Testimonial newTestimonial = testimonialService.save(testimonial);
        TestimonialDTO newTestimonialDTO = testimonialMapper.mapFromEntity(newTestimonial);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newTestimonialDTO);
    }

    @PutMapping("/{testimonialId}")
    public ResponseEntity<DomainSuccessResponse<TestimonialDTO>> updateTestimonialById(
            @PathVariable("testimonialId") UUID testimonialId,
            @RequestBody SaveTestimonialDTO saveTestimonialDTO
    ) {
        Testimonial testimonial = testimonialMapper.mapToEntity(saveTestimonialDTO);
        Testimonial updateTestimonial = testimonialService.updateById(testimonialId, testimonial);
        TestimonialDTO updateTestimonialDTO = testimonialMapper.mapFromEntity(updateTestimonial);

        return DomainSuccessResponse.get(HttpStatus.OK, updateTestimonialDTO);
    }

    @DeleteMapping("/{testimonialId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteTestimonialById(@PathVariable("testimonialId") UUID testimonialId) {
        String message = testimonialService.deleteById(testimonialId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
