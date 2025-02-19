package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestimonialService extends AbstractDomainService<Testimonial, TestimonialRepository> {
    private final TestimonialRepository testimonialRepository;

    @Autowired
    public TestimonialService(TestimonialRepository testimonialRepository) {
        super(testimonialRepository, "Testimonial");
        this.testimonialRepository = testimonialRepository;
    }

    public Testimonial updateById(UUID testimonialId, Testimonial updatedTestimonial) {
        Testimonial existingTestimonial = findById(testimonialId);
        Testimonial existingTestimonialUpdated = Testimonial.builder()
                .id(testimonialId)
                .firstname(updatedTestimonial.getFirstname() != null ? updatedTestimonial.getFirstname() : existingTestimonial.getFirstname())
                .lastname(updatedTestimonial.getLastname() != null ? updatedTestimonial.getLastname() : existingTestimonial.getLastname())
                .job(updatedTestimonial.getJob() != null ? updatedTestimonial.getJob() : existingTestimonial.getJob())
                .message(updatedTestimonial.getMessage() != null ? updatedTestimonial.getMessage() : existingTestimonial.getMessage())
                .build();

        return testimonialRepository.save(existingTestimonialUpdated);
    }

    public String deleteById(UUID teamMemberId) {
        findById(teamMemberId);
        testimonialRepository.deleteById(teamMemberId);

        return ConfirmMessage.TESTIMONIAL_DELETE;
    }
}
