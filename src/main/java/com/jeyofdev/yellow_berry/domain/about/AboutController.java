package com.jeyofdev.yellow_berry.domain.about;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.about.dto.SaveAboutDTO;
import com.jeyofdev.yellow_berry.domain.about.dto.AboutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/about")
@RequiredArgsConstructor
public class AboutController {
    private final AboutService aboutService;
    private final AboutMapper aboutMapper;

    @GetMapping("/{aboutId}")
    public ResponseEntity<DomainSuccessResponse<AboutDTO>> findAboutById(@PathVariable("aboutId") UUID aboutId) {
        About about = aboutService.findById(aboutId);
        AboutDTO aboutDTO = aboutMapper.mapFromEntity(about);

        return DomainSuccessResponse.get(HttpStatus.OK, aboutDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<AboutDTO>> saveAbout(@RequestBody SaveAboutDTO saveAboutDTO) {
        About about = aboutMapper.mapToEntity(saveAboutDTO);
        About newAbout = aboutService.save(about);
        AboutDTO newAboutDTO = aboutMapper.mapFromEntity(newAbout);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newAboutDTO);
    }

    @PutMapping("/{aboutId}")
    public ResponseEntity<DomainSuccessResponse<AboutDTO>> updateAboutById(
            @PathVariable("aboutId") UUID aboutId,
            @RequestBody SaveAboutDTO saveAboutDTO
    ) {
        About about = aboutMapper.mapToEntity(saveAboutDTO);
        About updateAbout = aboutService.updateById(aboutId, about);
        AboutDTO updateAboutDTO = aboutMapper.mapFromEntity(updateAbout);

        return DomainSuccessResponse.get(HttpStatus.OK, updateAboutDTO);
    }
}
