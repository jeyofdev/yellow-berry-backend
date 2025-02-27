package com.jeyofdev.yellow_berry.auth_user;

import com.jeyofdev.yellow_berry.auth_user.dto.AuthUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthUserServiceImpl authUserServiceImpl;
    private final AuthUserMapper authUserMapper;

    @GetMapping
    public ResponseEntity<List<AuthUserDTO>> getAllUsers() {
        List<AuthUser> authUserList = authUserServiceImpl.findAll();
        List<AuthUserDTO> authUserDTOList = authUserList.stream()
                .map(authUserMapper::mapFromEntity)
                .toList();

        return new ResponseEntity<>(authUserDTOList, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthUserDTO> getUserByEmail(@PathVariable("email") String email) {
        AuthUser authUser = authUserServiceImpl.findUserByEmail(email);
        AuthUserDTO authUserDTO = authUserMapper.mapFromEntity(authUser);

        return new ResponseEntity<>(authUserDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AuthUserDTO> getUserById(@PathVariable("userId") UUID userId) {
        AuthUser authUser = authUserServiceImpl.findUserById(userId);
        AuthUserDTO authUserDTO = authUserMapper.mapFromEntity(authUser);
        return new ResponseEntity<>(authUserDTO, HttpStatus.OK);
    }
}