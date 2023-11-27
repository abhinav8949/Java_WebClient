package com.java_mini2.validation;

import com.java_mini2.exception.UserCustomException;

public interface IEnglishAlphabetValidator {
    void validateAlphabet(String input) throws UserCustomException;
}
