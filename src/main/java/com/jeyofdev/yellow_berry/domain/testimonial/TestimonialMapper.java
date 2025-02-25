package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.domain.testimonial.dto.SaveTestimonialDTO;
import com.jeyofdev.yellow_berry.domain.testimonial.dto.TestimonialDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestimonialMapper {
    @Mapping(target = "name", expression = "java(testimonial.getFirstname() + \" \" + testimonial.getLastname())")
    TestimonialDTO mapFromEntity(Testimonial testimonial);
    Testimonial mapToEntity(SaveTestimonialDTO saveTestimonialDTO);
}
