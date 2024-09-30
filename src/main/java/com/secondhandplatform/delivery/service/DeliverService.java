package com.secondhandplatform.delivery.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.delivery.domain.Address;
import com.secondhandplatform.delivery.domain.DeliveryRepository;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliverService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;

    // 회원의 배송지 정보 조회
    public String findUserAddress(Long userId) {
        return null;
    }
}
