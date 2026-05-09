package com.pnb.uts;

import java.util.Scanner;

public class AdvancedCredentialEngine {

    public static class SecurityPolicyException extends RuntimeException {
        public SecurityPolicyException(String msg) {
            super(msg);
        }
    }

    public void cek(String s, String e) {
        // Validasi panjang string (12-20 karakter)
        if (s == null || s.length() < 12 || s.length() > 20) {
            throw new SecurityPolicyException("Password length must be between 12 and 20 characters.");
        }

        boolean n = false; // penanda angka
        boolean h = false; // penanda huruf

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                n = true;
                // Angka cuma boleh di indeks genap
                if (i % 2 != 0) {
                    throw new SecurityPolicyException("Numbers are only allowed at even indices.");
                }
            } else if (Character.isLetter(c)) {
                h = true;
                // Huruf cuma boleh di indeks ganjil
                if (i % 2 == 0) {
                    throw new SecurityPolicyException("Letters are only allowed at odd indices.");
                }
            }
        }

        // Harus ada campuran huruf dan angka
        if (!n || !h) {
            throw new SecurityPolicyException("Password must contain a combination of letters and numbers.");
        }

        // Cek bagian email (prefix)
        if (e != null && e.contains("@")) {
            String p = e.split("@")[0].toLowerCase();
            if (s.toLowerCase().contains(p)) {
                throw new SecurityPolicyException("Password cannot contain parts of the user's email.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdvancedCredentialEngine engine = new AdvancedCredentialEngine();

        System.out.println("=== Advanced Credential Engine Tool ===");
        System.out.println("(Ketik 'exit' pada email untuk keluar)");

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