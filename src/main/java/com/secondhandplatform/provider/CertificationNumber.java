package com.secondhandplatform.provider;

import lombok.Getter;

public class CertificationNumber {

    public static String createCertificationNumber() {

        StringBuilder certificationNumber = new StringBuilder();

        for (int count = 0; count < 4; count++) {
            certificationNumber.append((int) (Math.random() * 10));
        }

        return certificationNumber.toString();
    }
}
