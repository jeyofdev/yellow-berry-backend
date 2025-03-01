package com.jeyofdev.yellow_berry.util;

import com.jeyofdev.yellow_berry.auth.AuthServiceImpl;
import com.jeyofdev.yellow_berry.auth.model.AuthResponse;
import com.jeyofdev.yellow_berry.auth.model.LoginRequest;
import com.jeyofdev.yellow_berry.auth.model.RegisterRequest;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.enums.RoleEnum;
import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class FakeData {
    private final AuthServiceImpl authServiceImpl;

    public void generate(
            int count,
            Supplier<List<String>> dataSupplier,
            Predicate<List<String>> existsCheck,
            int maxRange,
            Consumer<List<String>> saveDTO
    ) {
        if (count == 0) {
            IntStream.range(0, maxRange).forEach((_) -> {
                List<String> data;
                do {
                    data = dataSupplier.get();
                } while (existsCheck.test(data));

                saveDTO.accept(data);
            });
        }
    }

    public void registerFakeUser(String email, String password, RoleEnum role) {
        RegisterRequest user = new RegisterRequest(email, password, role.toString());
        authServiceImpl.register(user, new BeanPropertyBindingResult(user, "user"));
    }

    public String getAuthenticationToken(String email, String password) {
        AuthResponse authenticatedUser = authServiceImpl.login(
                new LoginRequest(email, password),
                null
        );

        return authenticatedUser.getToken();

    }

    public <T, R> UUID runSaveRequestWithAuthentication(String authenticationToken, T requestBody, String url, Class<R> responseType) {
        RestTemplate restTemplate = new RestTemplate();

        // configure headers with the authentication token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authenticationToken);

        // request and response
        HttpEntity<T> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<DomainSuccessResponse<R>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        // check if request is success and extract response id
        if (response.getStatusCode().is2xxSuccessful()) {
            R result = response.getBody().getResult();

            if (result == null) {
                throw new RuntimeException(ErrorMessage.FAKE_RESPONSE_NO_RESULT);
            }

            if (result instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Object> resultMap = (Map<String, Object>) result;

                if (resultMap.containsKey("id")) {
                    return UUID.fromString(resultMap.get("id").toString());
                }
            }

            if (result instanceof ProfileDTO profileDTO) {
                return profileDTO.id();
            }

            throw new RuntimeException(ErrorMessage.FAKE_RESPONSE_NO_VALID_ID);
        }

        throw new RuntimeException(ErrorMessage.FAKE_RESPONSE_FAILED_CREATED_ENTITY);
    }
}
