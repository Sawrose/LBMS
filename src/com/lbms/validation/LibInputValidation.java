package com.lbms.validation;

public class LibInputValidation {

    private static final String USERNAME_REGEX = "^[a-z]+@[a-z]+\\.librarian$";
    private static final String PHONE_REGEX = "^\\+977\\d{10}$";

    // ---------- Password Rules ----------
    public static boolean hasMinLength(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean hasLowercase(String password) {
        return password != null && password.matches(".*[a-z].*");
    }

    public static boolean hasUppercase(String password) {
        return password != null && password.matches(".*[A-Z].*");
    }

    public static boolean hasDigit(String password) {
        return password != null && password.matches(".*\\d.*");
    }

    public static boolean hasSpecialChar(String password) {
        return password != null && password.matches(".*[@$!%*?&\\-_.].*");
    }

    // ---------- Password Rules Result ----------
    public static class PasswordRulesResult {
        public boolean length;
        public boolean lowercase;
        public boolean uppercase;
        public boolean digit;
        public boolean specialChar;

        public PasswordRulesResult(boolean length, boolean lowercase, boolean uppercase, boolean digit, boolean specialChar) {
            this.length = length;
            this.lowercase = lowercase;
            this.uppercase = uppercase;
            this.digit = digit;
            this.specialChar = specialChar;
        }

        public boolean allOk() {
            return length && lowercase && uppercase && digit && specialChar;
        }

        // Returns the first unmet rule in order as a string
        public String firstUnmetRule() {
            if (!length) return "length";
            if (!lowercase) return "lowercase";
            if (!uppercase) return "uppercase";
            if (!digit) return "digit";
            if (!specialChar) return "specialChar";
            return null; // all rules met
        }
    }

    // ---------- Check rules ----------
    public static PasswordRulesResult checkPasswordRules(String password) {
        return new PasswordRulesResult(
                hasMinLength(password),
                hasLowercase(password),
                hasUppercase(password),
                hasDigit(password),
                hasSpecialChar(password)
        );
    }

    // ---------- Full Input Validation ----------
    public static String validate(String username, String password, String phone) {
        if (username == null || username.trim().isEmpty()) return "Please enter username";
        if (!username.matches(USERNAME_REGEX)) return "Username must be like name@branch.librarian";

        if (password == null || password.trim().isEmpty()) return "Please enter password";
        if (!checkPasswordRules(password).allOk())
            return "Password must be 8+ chars with upper, lower, digit, special char";

        if (phone == null || phone.trim().isEmpty()) return "Please enter phone number";
        if (!phone.matches(PHONE_REGEX)) return "Phone number must be +977 followed by 10 digits";

        return "OK";
    }
}
