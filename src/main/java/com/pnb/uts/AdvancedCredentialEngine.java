package com.pnb.uts;

import java.util.Scanner;

public class AdvancedCredentialEngine {

    public static class SecurityPolicyException extends RuntimeException {
        public SecurityPolicyException(String msg) {
            super(msg);
        }
    }

    public void cek(String s, String e) {
        // Cek input kosong
        if (s == null || s.trim().isEmpty() || e == null || e.trim().isEmpty()) {
            throw new SecurityPolicyException("Email and Password must be filled.");
        }

        // Panjang 12-20
        if (s.length() < 12 || s.length() > 20) {
            throw new SecurityPolicyException("Password length must be between 12 and 20 characters.");
        }

        boolean n = false;
        boolean h = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Blokir spasi
            if (Character.isWhitespace(c)) {
                throw new SecurityPolicyException("Password cannot contain spaces.");
            }

            if (Character.isDigit(c)) {
                n = true;
                if (i % 2 != 0) throw new SecurityPolicyException("Numbers are only allowed at even indices.");
            } else if (Character.isLetter(c)) {
                h = true;
                if (i % 2 == 0) throw new SecurityPolicyException("Letters are only allowed at odd indices.");
            } else {
                // Blokir simbol
                throw new SecurityPolicyException("Symbols are not allowed.");
            }
        }

        if (!n || !h) throw new SecurityPolicyException("Must contain both letters and numbers.");

        // Cek potongan email (tanpa format @ tetap dicek)
        String p = e.contains("@") ? e.split("@")[0] : e;
        if (s.toLowerCase().contains(p.toLowerCase())) {
            throw new SecurityPolicyException("Password contains parts of email/username.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdvancedCredentialEngine engine = new AdvancedCredentialEngine();

        while (true) {
            System.out.print("\nEmail    : ");
            String email = sc.nextLine();
            if (email.equalsIgnoreCase("exit")) break;

            System.out.print("Password : ");
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