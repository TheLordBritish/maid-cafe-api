package com.sparkystudios.traklibrary.authentication.service.validation;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * The {@link PasswordConstraintValidator} is a validator that is used by the {@link ValidPassword} annotation,
 * which when annotated on a field, will ensure that the field matches the password criteria specified within
 * this validator. The validator will ensure that the password field is between 8 and 30 letters, contains at
 * least one upper-case character, at least one lower-case character, at least one number and no whitespace.
 *
 * @since 0.1.0
 * @author Sparky Studios
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Checks if the field that has been annotated with the {@link ValidPassword} annotation, passes
     * the password rules for Trak.
     *
     * @param s The value to check for validity.
     * @param constraintValidatorContext The context for all validators.
     *
     * @return <code>true</code> if the field provided passes all of the defined password rules.
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()
        ));

        var ruleResult = passwordValidator.validate(new PasswordData(Strings.isNullOrEmpty(s) ? "" : s));
        if (ruleResult.isValid()) {
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                Joiner.on(",").join(passwordValidator.getMessages(ruleResult)))
                .addConstraintViolation();

        return false;
    }
}
