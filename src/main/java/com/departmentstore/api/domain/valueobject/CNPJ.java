package com.departmentstore.api.domain.valueobject;

import java.util.Objects;

public final class CNPJ {

    private static final int CNPJ_LENGTH = 14;
    private final String value;

    public CNPJ(final String value) {
        final String normalized = normalize(value);
        validate(normalized);
        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    public String getFormatted() {
        return value.replaceAll(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
        );
    }

    private String normalize(final String value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "CNPJ cannot be null"
            );
        }
        return value.replaceAll("\\D", "");
    }

    private void validate(final String cnpj) {
        if (cnpj.length() != CNPJ_LENGTH) {
            throw new IllegalArgumentException(
                    "CNPJ must contain 14 digits"
            );
        }
        if (cnpj.matches("(\\d)\\1{13}")) {
            throw new IllegalArgumentException(
                    "Invalid CNPJ"
            );
        }
        validateDigits(cnpj);
    }

    private void validateDigits(final String cnpj) {
        int digit1 = calculateDigit(
                cnpj,
                new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
        );

        int digit2 = calculateDigit(
                cnpj,
                new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
        );

        if (digit1 != Character.getNumericValue(cnpj.charAt(12))
                || digit2 != Character.getNumericValue(cnpj.charAt(13))) {
            throw new IllegalArgumentException(
                    "Invalid CNPJ verification digits"
            );
        }
    }

    private int calculateDigit( final String cnpj, final int[] weights
    ) {

        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i))
                    * weights[i];
        }

        int remainder = sum % 11;
        return (remainder < 2)
                ? 0
                : 11 - remainder;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CNPJ cnpj)) {
            return false;
        }

        return value.equals(cnpj.value);
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
