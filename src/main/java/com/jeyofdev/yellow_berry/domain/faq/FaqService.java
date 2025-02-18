package com.jeyofdev.yellow_berry.domain.faq;

import com.jeyofdev.yellow_berry.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;

    public List<Faq> findAll() {
        return faqRepository.findAll();
    }

    public Faq findById(UUID faqId) throws NotFoundException {
        return faqRepository.findById(faqId).orElseThrow(
            () -> new NotFoundException(MessageFormat.format("Entity Faq with id {0} cannot be found", faqId)));
    }

    public Faq save(Faq faq) {
        return faqRepository.save(faq);
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

        return "Faq question and answer has been successfully deleted.";
    }
}
