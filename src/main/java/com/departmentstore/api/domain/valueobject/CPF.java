package com.departmentstore.api.domain.valueobject;

import java.util.Objects;

public final class CPF {
    private static final int CPF_LENGTH = 11;
    private final String value;

    public CPF(final String value) {
        final String normalized = normalize(value);
        validate(normalized);
        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    public String getFormatted(){
        return value.replace(
                "(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4");
    }

    private String normalize(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("CPF cannot be null");
        }
        return value.replaceAll("\\D", "");
    }

    private void validate(final String cpf) {
        if (cpf.length() != CPF_LENGTH) {
            throw new IllegalArgumentException(
                    "CPF must contain 11 digits"
            );
        }
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException(
                    "Invalid CPF"
            );
        }
        validateDigits(cpf);
    }

    private void validateDigits(final String cpf) {
        int digit1 = calculateDigit(cpf, 10);
        int digit2 = calculateDigit(cpf, 11);
        if (digit1 != Character.getNumericValue(cpf.charAt(9))
                || digit2 != Character.getNumericValue(cpf.charAt(10))) {
            throw new IllegalArgumentException(
                    "Invalid CPF verification digits"
            );
        }
    }

    private int calculateDigit(final String cpf, final int weightStart) {
        int sum = 0;
        int weight = weightStart;
        for (int i = 0; i < weightStart - 1; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight;
            weight--;
        }
        int remainder = 11 - (sum % 11);
        return (remainder >= 10) ? 0 : remainder;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CPF cpf)) {
            return false;
        }

        return value.equals(cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getFormatted();
    }

}
