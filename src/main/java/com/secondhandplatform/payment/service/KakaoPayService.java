package com.secondhandplatform.payment.service;

import com.secondhandplatform.payment.dto.request.KakaoPayApproveRequest;
import com.secondhandplatform.payment.dto.request.KakaoPayReadyRequest;
import com.secondhandplatform.payment.dto.response.KakaoPayApproveResponse;
import com.secondhandplatform.payment.dto.response.KakaoPayReadyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class KakaoPayService {

    @Value("${kakao-key}")
    private String SECRET_KEY;
    private final String CONTENT_TYPE_JSON = "application/json";

    public KakaoPayReadyResponse payReady(String name, int totalAmount) {
        KakaoPayReadyRequest readyRequest = KakaoPayReadyRequest.createPayReadyRequest(name, totalAmount);
        HttpEntity<KakaoPayReadyRequest> requestEntity = new HttpEntity<>(readyRequest, getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        ResponseEntity<KakaoPayReadyResponse> responseEntity = template.postForEntity(url, requestEntity, KakaoPayReadyResponse.class);

        return responseEntity.getBody();
    }

    public KakaoPayApproveResponse payApprove(String tid, String pgToken) {
        KakaoPayApproveRequest approveRequest = KakaoPayApproveRequest.createApproveRequest(tid, pgToken);
        HttpEntity<KakaoPayApproveRequest> requestEntity = new HttpEntity<>(approveRequest, getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";

        ResponseEntity<KakaoPayApproveResponse> responseEntity = template.postForEntity(url, requestEntity, KakaoPayApproveResponse.class);
        return responseEntity.getBody();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + SECRET_KEY);
        headers.set("Content-Type", CONTENT_TYPE_JSON);
        return headers;
    }
}
