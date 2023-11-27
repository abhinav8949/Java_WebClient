package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;

import static com.java_mini2.constants.UserConstant.INVALID_LIMIT_VALUE;
import static com.java_mini2.constants.UserConstant.INVALID_OFFSET_VALUE;

public class NumericValidator implements INumericValidator {
    private static NumericValidator instance;

    private NumericValidator() {
    }

    public static synchronized NumericValidator getInstance() {
        if (instance == null) {
            instance = new NumericValidator();
        }
        return instance;
    }

    @Override
    public void validateNumericLimit(int limit) throws UserCustomException {
        if (limit < 1 || limit > 5) {
            throw new UserCustomException(INVALID_LIMIT_VALUE);
        }
    }

    @Override
    public void validateNumericOffset(int offset) throws UserCustomException {
        if (offset < 0) {
            throw new UserCustomException(INVALID_OFFSET_VALUE);
        }
    }
}
