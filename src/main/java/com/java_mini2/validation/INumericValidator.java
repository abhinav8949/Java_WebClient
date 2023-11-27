package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;

public interface INumericValidator {
    void validateNumericLimit(int limit) throws UserCustomException;

    void validateNumericOffset(int offset) throws UserCustomException;
}
