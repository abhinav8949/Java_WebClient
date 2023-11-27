package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;

import static com.java_mini2.constants.UserConstant.*;

public class EnglishAlphabetValidator implements IEnglishAlphabetValidator {
    private static EnglishAlphabetValidator instance;

    private EnglishAlphabetValidator() {
    }

    public static synchronized EnglishAlphabetValidator getInstance() {
        if (instance == null) {
            instance = new EnglishAlphabetValidator();
        }
        return instance;
    }

    @Override
    public void validateAlphabet(String input) throws UserCustomException {
        if (!SORT_BY_NAME.equalsIgnoreCase(input) && !SORT_BY_AGE.equalsIgnoreCase(input)
                && !SORT_ORDER_EVEN.equalsIgnoreCase(input) && !SORT_ORDER_ODD.equalsIgnoreCase(input)) {
            throw new UserCustomException(INVALID_SORT_TYPE_ORDER);
        }
    }
}
