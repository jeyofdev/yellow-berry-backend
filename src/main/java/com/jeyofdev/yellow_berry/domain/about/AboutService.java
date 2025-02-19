package com.jeyofdev.yellow_berry.domain.about;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@org.springframework.stereotype.Service
public class AboutService extends AbstractDomainService<About, AboutRepository> {
    private final AboutRepository aboutRepository;

    @Autowired
    public AboutService(AboutRepository aboutRepository) {
        super(aboutRepository, "Service");
        this.aboutRepository = aboutRepository;
    }

    public About updateById(UUID serviceId, About updatedAbout) {
        About existingAbout = findById(serviceId);
        About existingAboutUpdated = About.builder()
                .id(serviceId)
                .title(updatedAbout.getTitle() != null ? updatedAbout.getTitle() : existingAbout.getTitle())
                .subtitle(updatedAbout.getSubtitle() != null ? updatedAbout.getSubtitle() : existingAbout.getSubtitle())
                .description(updatedAbout.getDescription() != null ? updatedAbout.getDescription() : existingAbout.getDescription())
                .build();

        return aboutRepository.save(existingAboutUpdated);
    }
}
