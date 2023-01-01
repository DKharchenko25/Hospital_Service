package com.epam.hospital.facades.validators;

public class InputValidator {

    public static boolean validateEmail(String email) {
        return email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }

    public static boolean validatePassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    }

    public static boolean validateUsername(String username) {
        return username.matches("^[A-Za-z][A-Za-z0-9_]{5,29}$");
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^((8|\\+38)-?)?(\\(?0\\d{2}\\)?)?-?\\d{3}-?\\d{2}-?\\d{2}$");
    }


    public static boolean validateDate(String date) {
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean validateName(String name) {
        return name.matches("[a-zA-ZА-ЩЬЮЯҐЄІЇа-щьюяґєії']+");
    }

}
