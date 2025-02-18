package com.jeyofdev.yellow_berry.domain.faq;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FaqService extends AbstractDomainService<Faq, FaqRepository> {
    private final FaqRepository faqRepository;

    @Autowired
    public FaqService(FaqRepository faqRepository) {
        super(faqRepository, "Faq");
        this.faqRepository = faqRepository;
    }

    public Faq updateById(UUID faqId, Faq updatedFaq) {
        Faq existingFaq = findById(faqId);
        Faq existingFaqUpdated = Faq.builder()
                .id(faqId)
                .question(updatedFaq.getQuestion() != null ? updatedFaq.getQuestion() : existingFaq.getQuestion())
                .answer(updatedFaq.getAnswer() != null ? updatedFaq.getAnswer() : existingFaq.getAnswer())
                .build();

        return faqRepository.save(existingFaqUpdated);
    }

    public String deleteById(UUID faqId) {
        findById(faqId);
        faqRepository.deleteById(faqId);

        return ConfirmMessage.FAQ_DELETE;
    }
}
