package com.jeyofdev.yellow_berry.domain.faq;

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
    public ResponseEntity<List<FaqDTO>> findAllFaq() {
        List<Faq> faqList = faqService.findAll();
        List<FaqDTO> faqDTOList = faqList.stream().map(faqMapper::mapFromEntity).toList();

        return new ResponseEntity<>(faqDTOList, HttpStatus.OK);
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<FaqDTO> findFaqById(@PathVariable("faqId") UUID faqId) {
        Faq faq = faqService.findById(faqId);
        FaqDTO cityDTO = faqMapper.mapFromEntity(faq);

        return new ResponseEntity<>(cityDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FaqDTO> saveFaq(@RequestBody SaveFaqDTO saveFaqDTO) {
        Faq faq = faqMapper.mapToEntity(saveFaqDTO);
        Faq newFaq = faqService.save(faq);
        FaqDTO newFaqDTO = faqMapper.mapFromEntity(newFaq);

        return new ResponseEntity<>(newFaqDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<FaqDTO> updateFaqById(
            @PathVariable("faqId") UUID faqId,
            @RequestBody SaveFaqDTO saveFaqDTO
    ) {
        Faq faq = faqMapper.mapToEntity(saveFaqDTO);
        Faq updateFaq = faqService.updateById(faqId, faq);
        FaqDTO updateFaqDTO = faqMapper.mapFromEntity(updateFaq);

        return new ResponseEntity<>(updateFaqDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<Object> deleteFaqById(@PathVariable("faqId") UUID faqId) {
        String message = faqService.deleteById(faqId);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
