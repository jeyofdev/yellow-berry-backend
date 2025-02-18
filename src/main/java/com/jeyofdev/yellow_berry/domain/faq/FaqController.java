package com.jeyofdev.yellow_berry.domain.faq;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.faq.dto.FaqDTO;
import com.jeyofdev.yellow_berry.domain.faq.dto.SaveFaqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;
    private final FaqMapper faqMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<FaqDTO>>> findAllFaq() {
        List<Faq> faqList = faqService.findAll();
        List<FaqDTO> faqDTOList = faqList.stream().map(faqMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, faqDTOList);
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<DomainSuccessResponse<FaqDTO>> findFaqById(@PathVariable("faqId") UUID faqId) {
        Faq faq = faqService.findById(faqId);
        FaqDTO faqDTO = faqMapper.mapFromEntity(faq);

        return DomainSuccessResponse.get(HttpStatus.OK, faqDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<FaqDTO>> saveFaq(@RequestBody SaveFaqDTO saveFaqDTO) {
        Faq faq = faqMapper.mapToEntity(saveFaqDTO);
        Faq newFaq = faqService.save(faq);
        FaqDTO newFaqDTO = faqMapper.mapFromEntity(newFaq);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newFaqDTO);
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<DomainSuccessResponse<FaqDTO>> updateFaqById(
            @PathVariable("faqId") UUID faqId,
            @RequestBody SaveFaqDTO saveFaqDTO
    ) {
        Faq faq = faqMapper.mapToEntity(saveFaqDTO);
        Faq updateFaq = faqService.updateById(faqId, faq);
        FaqDTO updateFaqDTO = faqMapper.mapFromEntity(updateFaq);

        return DomainSuccessResponse.get(HttpStatus.OK, updateFaqDTO);
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteFaqById(@PathVariable("faqId") UUID faqId) {
        String message = faqService.deleteById(faqId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
