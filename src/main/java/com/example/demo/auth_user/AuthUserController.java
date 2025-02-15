package com.example.demo.auth_user;

import com.example.demo.auth_user.dto.AuthUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthUserService authUserService;
    private final AuthUserMapper authUserMapper;

    @GetMapping
    public ResponseEntity<List<AuthUserDTO>> getAllUsers() {

        AuthUser authUser = AuthUser.builder()
                .id(1L)
                .username("bobby")
                .build();

        System.out.println(authUser.toString());

        List<AuthUser> authUserList = authUserService.findAll();
        List<AuthUserDTO> authUserDTOList = authUserList.stream()
                .map(authUserMapper::mapFromEntity)
                .toList();

        return new ResponseEntity<>(authUserDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthUserDTO> addNewUser(@RequestBody AuthUser authUser) {
        AuthUser newAuthUser = authUserService.add(authUser);
        AuthUserDTO authUserDTO = authUserMapper.mapFromEntity(newAuthUser);

        return new ResponseEntity<>(authUserDTO, HttpStatus.CREATED);
    }
}
