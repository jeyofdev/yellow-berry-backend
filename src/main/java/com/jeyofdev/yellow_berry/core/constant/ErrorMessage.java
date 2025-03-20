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
    public static final String EMAIL_ALREADY_TAKEN = "Email already taken";
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

    // Fake datas
    public static final String FAKE_RESPONSE_NO_RESULT = "The response does not contain any results.";
    public static final String FAKE_RESPONSE_NO_VALID_ID = "The response does not contain a valid ID.";
    public static final String FAKE_RESPONSE_FAILED_CREATED_ENTITY = "Failed to create entity";


    // Domain fields
    public static final String REQUIRED_NAME = "The name field is required.";
    public static final String VALID_NAME = "The name field must contain between {min} and {max} characters.";

    public static final String REQUIRED_FIRSTNAME = "The firstname field is required.";
    public static final String VALID_FIRSTNAME = "The firstname field must contain between {min} and {max} characters.";

    public static final String REQUIRED_LASTNAME = "The lastname field is required.";
    public static final String VALID_LASTNAME = "The lastname field must contain between {min} and {max} characters.";

    public static final String REQUIRED_TITLE = "The title field is required.";
    public static final String VALID_TITLE = "The title field must contain between {min} and {max} characters.";

    public static final String REQUIRED_SUBTITLE = "The subtitle field is required.";
    public static final String VALID_SUBTITLE = "The subtitle field must contain between {min} and {max} characters.";

    public static final String REQUIRED_DESCRIPTION = "The description field is required.";
    public static final String VALID_DESCRIPTION = "The description field must contain between {min} and {max} characters.";
    public static final String REQUIRED_BODY = "The body field is required.";
    public static final String REQUIRED_MESSAGE = "The message field is required.";

    public static final String REQUIRED_RATING = "The rating field is required.";
    public static final String MIN_RATING = "The minimum rating must be at least {value}.";
    public static final String MAX_RATING = "The maximum rating must be {value}.";

    public static final String REQUIRED_QUESTION = "The question field is required.";
    public static final String REQUIRED_ANSWER = "The answer field is required.";

    public static final String REQUIRED_PRICE = "The price field is required.";
    public static final String MIN_PRICE = "The price must be at least {value}.";

    public static final String REQUIRED_PRICE_DISCOUNT = "The price discount field is required.";
    public static final String MIN_PRICE_DISCOUNT = "The price discount must be at least {value}.";

    public static final String MIN_DISCOUNT = "The discount field is required.";
    public static final String MAX_DISCOUNT = "The discount must be at least {value}.";

    public static final String REQUIRED_STOCK = "The stock field is required.";
    public static final String VALID_STOCK = "The stock field must be a valid stock.";

    public static final String REQUIRED_JOB = "The job field is required.";
    public static final String VALID_JOB = "The job field must be a valid job.";

    public static final String REQUIRED_WEIGHT = "The weight field is required.";
    public static final String VALID_WEIGHT = "The weight field must be a valid weight.";

    public static final String REQUIRED_SELLER = "The seller field is required.";
    public static final String VALID_SELLER = "The seller field must contain between {min} and {max} characters.";

    public static final String REQUIRED_SERVICE = "The service field is required.";
    public static final String VALID_SERVICE = "The service field must contain between {min} and {max} characters.";

    public static final String REQUIRED_DIMENSIONS = "The dimension field is required.";
    public static final String VALID_DIMENSIONS = "The dimension field must contain between {min} and {max} characters.";

    public static final String REQUIRED_BRAND = "The brand field is required.";
    public static final String VALID_BRAND = "The brand field must contain between {min} and {max} characters.";

    public static final String REQUIRED_QUANTITY = "The quantity field is required.";
    public static final String MIN_QUANTITY = "The minimum quantity must be at least {value}.";
    public static final String MAX_QUANTITY = "The maximum quantity must be {value}.";

    public static final String REQUIRED_COLOR = "The color field is required.";
    public static final String VALID_COLOR = "The color field must be a valid color.";

    public static final String REQUIRED_PHONE = "The phone number field is required.";
    public static final String VALID_PHONE = "Please provide a valid phone number.";

    public static final String REQUIRED_ADDRESS = "The address field is required.";
    public static final String VALID_ADDRESS = "The address field must contain between {min} and {max} characters.";

    public static final String REQUIRED_REGION = "The region field is required.";
    public static final String VALID_REGION = "The region field must contain between {min} and {max} characters.";

    public static final String REQUIRED_DEPARTMENT = "The department field is required.";
    public static final String VALID_DEPARTMENT = "The department field must contain between {min} and {max} characters.";

    public static final String REQUIRED_ZIP_CODE = "The zip code field is required.";
    public static final String VALID_ZIP_CODE = "Please provide a valid zip code number.";

    public static final String REQUIRED_CITY = "The city field is required.";
    public static final String VALID_CITY = "The city field must contain between {min} and {max} characters.";

    public static final String REQUIRED_TWITTER = "The twitter field is required.";
    public static final String VALID_TWITTER = "The twitter field must contain between {min} and {max} characters.";

    public static final String REQUIRED_INSTAGRAM = "The instagram field is required.";
    public static final String VALID_INSTAGRAM = "The instagram field must contain between {min} and {max} characters.";

    public static final String REQUIRED_LINKEDIN = "The linkedin field is required.";
    public static final String VALID_LINKEDIN = "The linkedin field must contain between {min} and {max} characters.";

    // relation
    public static final String PROFILE_NOT_NULL_CART = "An profile must be associated with entity cart.";
    public static final String PROFILE_NOT_NULL_COMMENT = "An profile must be associated with entity comment.";
    public static final String PROFILE_NOT_NULL_WISHLIST = "An profile must be associated with entity wishlist.";
    public static final String PRODUCT_NOT_NULL_PRODUCT_DETAILS = "An product must be associated with entity product details.";
    public static final String PRODUCT_NOT_NULL_PRODUCT_INFORMATION = "An product must be associated with entity product information.";
    public static final String AUTH_USER_NOT_NULL_PROFILE = "An user must be associated with entity profile.";

    public static final String ALREADY_TAKEN = "Entity {0} with {1} {2} already taken";
    public static final String ALREADY_ASSOCIATED = "This {0} already has associated {1}.";

}
