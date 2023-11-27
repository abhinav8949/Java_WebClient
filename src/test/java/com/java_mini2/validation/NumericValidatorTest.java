package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NumericValidatorTest {

    @Test
    void validateNumericLimitShouldNotThrowExceptionForValidLimit() throws UserCustomException {
        NumericValidator validator = NumericValidator.getInstance();
        validator.validateNumericLimit(1);
        validator.validateNumericLimit(3);
        validator.validateNumericLimit(5);
    }

    @Test
    void validateNumericLimitShouldThrowExceptionForInvalidLimit() {
        NumericValidator validator = NumericValidator.getInstance();
        assertThrows(UserCustomException.class, () -> validator.validateNumericLimit(0));
        assertThrows(UserCustomException.class, () -> validator.validateNumericLimit(6));
    }

    @Test
    void validateNumericOffsetShouldNotThrowExceptionForValidOffset() throws UserCustomException {
        NumericValidator validator = NumericValidator.getInstance();
        validator.validateNumericOffset(0);
        validator.validateNumericOffset(5);
        validator.validateNumericOffset(10);
    }

    @Test
    void validateNumericOffsetShouldThrowExceptionForInvalidOffset() {
        NumericValidator validator = NumericValidator.getInstance();
        assertThrows(UserCustomException.class, () -> validator.validateNumericOffset(-1));
    }
}
