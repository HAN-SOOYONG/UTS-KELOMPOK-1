package com.pnb.uts;

import java.util.Scanner;

public class AdvancedCredentialEngine {

    public static class SecurityPolicyException extends RuntimeException {
        public SecurityPolicyException(String msg) {
            super(msg);
        }
    }

    public void cek(String s, String e) {
        // Check empty input
        if (s == null || s.trim().isEmpty() || e == null || e.trim().isEmpty()) {
            throw new SecurityPolicyException("Email and Password are required.");
        }

        // Length 12-20
        if (s.length() < 12 || s.length() > 20) {
            throw new SecurityPolicyException("Password length must be 12-20 characters.");
        }

        boolean hasNum = false;
        boolean hasLet = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Block spaces
            if (Character.isWhitespace(c)) {
                throw new SecurityPolicyException("No spaces allowed.");
            }

            if (Character.isDigit(c)) {
                hasNum = true;
                // Digit at even index only
                if (i % 2 != 0) throw new SecurityPolicyException("Numbers only at even indices.");
            } else if (Character.isLetter(c)) {
                hasLet = true;
                // Letter at odd index only
                if (i % 2 == 0) throw new SecurityPolicyException("Letters only at odd indices.");
            } else {
                // Block symbols
                throw new SecurityPolicyException("No symbols allowed.");
            }
        }

        // Must have both
        if (!hasNum || !hasLet) throw new SecurityPolicyException("Must contain both letters and numbers.");

        // Check email prefix or full username
        String p = e.contains("@") ? e.split("@")[0] : e;
        if (s.toLowerCase().contains(p.toLowerCase())) {
            throw new SecurityPolicyException("Password contains parts of email/username.");
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
        System.out.println("- No symbols or spaces");
        System.out.println("- No email/username parts");
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