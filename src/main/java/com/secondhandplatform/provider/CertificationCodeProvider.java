package com.secondhandplatform.provider;

public class CertificationCodeProvider {

    public static String createCertificationNumber() {

        StringBuilder certificationNumber = new StringBuilder();

        for (int count = 0; count < 4; count++) {
            certificationNumber.append((int) (Math.random() * 10));
        }

        return certificationNumber.toString();
    }
}
