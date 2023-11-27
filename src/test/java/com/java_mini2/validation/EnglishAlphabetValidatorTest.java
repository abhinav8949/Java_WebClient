package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;
import org.junit.jupiter.api.Test;

import static com.java_mini2.constants.UserConstant.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnglishAlphabetValidatorTest {

    @Test
    void validateAlphabetShouldNotThrowExceptionForValidInput() throws UserCustomException {
        EnglishAlphabetValidator validator = EnglishAlphabetValidator.getInstance();
        validator.validateAlphabet(SORT_BY_NAME);
        validator.validateAlphabet(SORT_BY_AGE);
        validator.validateAlphabet(SORT_ORDER_EVEN);
        validator.validateAlphabet(SORT_ORDER_ODD);
    }

    @Test
    void validateAlphabetShouldThrowExceptionForInvalidInput() {
        EnglishAlphabetValidator validator = EnglishAlphabetValidator.getInstance();
        assertThrows(UserCustomException.class, () -> validator.validateAlphabet("invalidSortType"));
    }
}
