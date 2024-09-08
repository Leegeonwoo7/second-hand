package com.secondhandplatform.provider;

public class CertificationCodeProvider {

    public static String createCertificationCode() {

        StringBuilder certificationCode = new StringBuilder();

        for (int count = 0; count < 4; count++) {
            certificationCode.append((int) (Math.random() * 10));
        }

        return certificationCode.toString();
    }
}
