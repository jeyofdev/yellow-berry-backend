package com.jeyofdev.yellow_berry.core.constant;

public class ErrorMessage {
    // validation email
    public static final String EMAIL_REQUIRED = "The email field is required.";
    public static final String EMAIL_LENGTH = "The email must be at most 100 characters.";
    public static final String EMAIL_FORMAT = "The email is not in the correct format.";

    // validation password
    public static final String PASSWORD_REQUIRED = "The password field is required.";
    public static final String PASSWORD_LENGTH = "The password must be contain between 8 and 16 characters.";
    public static final String PASSWORD_MINLENGTH = "The password must be contain between 8 and 16 characters.";
    public static final String PASSWORD_FORMAT = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.";

    // validation role
    public static final String ROLE_VALUE = "The role must be either admin or user";

    // user
    public static final String USERNAME_ALREADY_TAKEN = "Username already taken";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String OLD_BAD_PASSWORD = "Old password is incorrect.";
    public static final String NO_USER_ASSOCIATED_EMAIL = "No account was found associated with this email address. Please check the email you provided or consider creating a new account.";

    // token
    public static final String TOKEN_MUST_BE_PROVIDED = "The verification token must be provided";
    public static final String TOKEN_VERIFICATION_INVALID = "Invalid verification token";
    public static final String TOKEN_VERIFICATION_EXPIRED = "Verification token has expired";
    public static final String TOKEN_RESET_INVALID = "Invalid or missing reset token";
    public static final String TOKEN_RESET_EXPIRED = "Verification token has expired";

    // access not authorized
    public static final String LOGIN_FAILED = "Login failed. Please verify your credentials and try again.";
    public static final String LIMIT_ACCESS = "User does not have the correct rights to access to this resource";
    public static final String NO_AUTHORIZED = "You are not authorized to access this resource";
    public static final String NO_SUFFICIENT_RIGHT = "You do not have sufficient rights";

    // JWT
    public static final String JWT_EXPIRED = "JWT has expired. Please log in again.";
    public static final String JWT_MALFORMED = "JWT is malformed. Please verify its integrity.";
    public static final String JWT_MUST_BE_PROVIDED = "A jwt token must be provided.";

}
