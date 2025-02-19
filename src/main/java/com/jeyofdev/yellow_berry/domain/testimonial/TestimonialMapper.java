package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.domain.testimonial.dto.SaveTestimonialDTO;
import com.jeyofdev.yellow_berry.domain.testimonial.dto.TestimonialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestimonialMapper {
    TestimonialDTO mapFromEntity(Testimonial testimonial);
    Testimonial mapToEntity(SaveTestimonialDTO saveTestimonialDTO);
}
