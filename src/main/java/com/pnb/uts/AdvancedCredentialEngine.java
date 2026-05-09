package com.pnb.uts;

import java.util.Scanner;

public class AdvancedCredentialEngine {

    public static class SecurityPolicyException extends RuntimeException {
        public SecurityPolicyException(String msg) {
            super(msg);
        }
    }

    public void cek(String s, String e) {
        // Required input check
        if (s == null || s.trim().isEmpty() || e == null || e.trim().isEmpty()) {
            throw new SecurityPolicyException("Email and Password are required.");
        }

        // Length check (12-20)
        if (s.length() < 12 || s.length() > 20) {
            throw new SecurityPolicyException("Length must be 12-20 characters.");
        }

        boolean n = false;
        boolean h = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Space check
            if (Character.isWhitespace(c)) {
                throw new SecurityPolicyException("Spaces are not allowed.");
            }

            if (Character.isDigit(c)) {
                n = true;
                // Even index validation
                if (i % 2 != 0) throw new SecurityPolicyException("Numbers only at even indices.");
            } else if (Character.isLetter(c)) {
                h = true;
                // Odd index validation
                if (i % 2 == 0) throw new SecurityPolicyException("Letters only at odd indices.");
            } else {
                // Symbol check
                throw new SecurityPolicyException("Symbols are not allowed.");
            }
        }

        // Mix check
        if (!n || !h) throw new SecurityPolicyException("Must contain letters and numbers.");

        // Email part check
        String p = e.contains("@") ? e.split("@")[0] : e;
        if (s.toLowerCase().contains(p.toLowerCase())) {
            throw new SecurityPolicyException("Password contains email parts.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdvancedCredentialEngine engine = new AdvancedCredentialEngine();

        System.out.println("=== Advanced Credential Engine ===");
        System.out.println("Rules:");
        System.out.println("- 12-20 characters");
        System.out.println("- Even index: Numbers");
        System.out.println("- Odd index: Letters");
        System.out.println("- No symbols/spaces");
        System.out.println("- No email parts");
        System.out.println("==================================");

        while (true) {
            System.out.print("\nEmail/User : ");
            String email = sc.nextLine();
            if (email.equalsIgnoreCase("exit")) break;

            System.out.print("Password   : ");
            String pass = sc.nextLine();

            try {
                engine.cek(pass, email);
                System.out.println(">> VALID");
            } catch (SecurityPolicyException ex) {
                System.out.println(">> INVALID - " + ex.getMessage());
            }
        }
        sc.close();
    }
}