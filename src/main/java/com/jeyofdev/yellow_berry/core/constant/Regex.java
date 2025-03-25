package com.jeyofdev.yellow_berry.core.constant;

public class Regex {
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$";
    public static final String PHONE_PATTERN = "^\\(\\+33\\)\\s\\d\\s\\d{2}\\s\\d{2}\\s\\d{2}\\s\\d{2}$";
    public static final String ZIPCODE_PATTERN = "\\d{5}";
    public static final String ZIPCODE_REFERENCE = "^[A-Z]{2}[A-Z0-9][0-9]{2}$";
}