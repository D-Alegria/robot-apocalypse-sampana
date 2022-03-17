package com.sampana.robotapocalypsesampana.annotation.validator;


import com.sampana.robotapocalypsesampana.annotation.NotEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Created by Demilade Oladugba on 3/17/2022
 **/

public class EnumValidator implements ConstraintValidator<NotEnum, Enum<?>> {
    private Pattern pattern;
    private boolean nullable;

    @Override
    public void initialize(NotEnum annotation) {
        try {
            pattern = Pattern.compile(annotation.regexp(), CASE_INSENSITIVE);
            nullable = annotation.nullable();
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null)
            return nullable;

        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
}