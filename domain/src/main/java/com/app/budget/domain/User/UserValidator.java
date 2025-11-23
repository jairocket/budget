package com.app.budget.domain.User;

import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;
import com.app.budget.domain.validation.Validator;

import java.util.regex.Pattern;

public class UserValidator extends Validator {
    private final User user;

    public UserValidator(final ValidationHandler handler, final User user) {
        super(handler);
        this.user = user;
    }

    @Override
    public void validate() {
        validateName();
        validateEmail();
        validatePassword();
        validateRole();
    }

    private boolean passwordIsValid(String password) {
        return Pattern.compile(
                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
                        Pattern.CASE_INSENSITIVE
                ).matcher(password)
                .matches();
    }

    private boolean emailIsValid(String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email)
                .matches();
    }

    private void validateName() {
        if (this.user.getName() == null) {
            this.validationHandler().append(new Error("User name cannot be null"));
        }

        if (this.user.getName().length() < 3) {
            this.validationHandler().append(new Error("User name should have at least three characters"));
        }

        if (this.user.getName().length() > 60) {
            this.validationHandler().append(new Error("User name should have less than sixty characters"));
        }
    }

    private void validatePassword() {
        if (this.user.getPassword() == null) {
            this.validationHandler().append(new Error("User password cannot be null"));
        }

        if (!passwordIsValid(this.user.getPassword())) {
            this.validationHandler().append(new Error("Inform a valid password"));
        }
    }

    private void validateEmail() {
        if (this.user.getEmail() == null) {
            this.validationHandler().append(new Error("User email cannot be null"));
        }

        if (!emailIsValid(this.user.getEmail())) {
            this.validationHandler().append(new Error("User email is invalid"));
        }
    }

    private void validateRole() {
        if (this.user.getRole() == null) {
            this.validationHandler().append(new Error("User role cannot be null"));
        }
    }
}
