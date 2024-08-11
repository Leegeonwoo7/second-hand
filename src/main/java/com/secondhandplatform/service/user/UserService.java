package com.secondhandplatform.service.user;

import com.secondhandplatform.api.request.user.IdCheckRequest;
import com.secondhandplatform.repository.UserRepository;
import com.secondhandplatform.service.user.response.IdCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public IdCheckResponse checkLoginId(IdCheckRequest request) {
        String loginId = request.getLoginId();

        boolean isDuplicate = userRepository.existsByLoginId(loginId);
        if (isDuplicate) {
            return IdCheckResponse.duplicate(loginId);
        }

        return IdCheckResponse.success(loginId);
    }

    
}
