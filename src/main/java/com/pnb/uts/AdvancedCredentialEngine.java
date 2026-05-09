package com.pnb.uts;

import java.util.Scanner;

public class AdvancedCredentialEngine {

    public static class SecurityPolicyException extends RuntimeException {
        public SecurityPolicyException(String msg) {
            super(msg);
        }
    }

    public void cek(String s, String e) {
        // 1. Validasi Input Dasar
        if (s == null || s.isEmpty()) {
            throw new SecurityPolicyException("Password cannot be empty.");
        }

        // 2. Validasi panjang string (12-20 karakter)
        if (s.length() < 12 || s.length() > 20) {
            throw new SecurityPolicyException("Password length must be between 12 and 20 characters.");
        }

        boolean n = false; // penanda angka
        boolean h = false; // penanda huruf

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                n = true;
                // Angka cuma boleh di indeks genap (0, 2, 4...)
                if (i % 2 != 0) {
                    throw new SecurityPolicyException("Numbers are only allowed at even indices.");
                }
            } else if (Character.isLetter(c)) {
                h = true;
                // Huruf cuma boleh di indeks ganjil (1, 3, 5...)
                if (i % 2 == 0) {
                    throw new SecurityPolicyException("Letters are only allowed at odd indices.");
                }
            } else {
                // Jika ada karakter selain huruf/angka (simbol/spasi)
                throw new SecurityPolicyException("Password must only contain letters and numbers (no symbols or spaces).");
            }
        }

        // 3. Harus ada campuran huruf dan angka
        if (!n || !h) {
            throw new SecurityPolicyException("Password must contain a combination of letters and numbers.");
        }

        // 4. Cek bagian email (Proteksi Ketat)
        if (e != null && !e.isEmpty()) {
            // Ambil bagian sebelum @, atau ambil semua jika tidak ada @
            String forbiddenPart = e.contains("@") ? e.split("@")[0] : e;
            forbiddenPart = forbiddenPart.toLowerCase();
            
            if (s.toLowerCase().contains(forbiddenPart)) {
                throw new SecurityPolicyException("Password cannot contain parts of the user's email or username.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdvancedCredentialEngine engine = new AdvancedCredentialEngine();

        System.out.println("==========================================");
        System.out.println("   ADVANCED CREDENTIAL ENGINE - KLP 1");
        System.out.println("==========================================");
        System.out.println("Aturan:");
        System.out.println("- 12 s/d 20 Karakter");
        System.out.println("- Indeks GENAP (0,2,4..): Angka");
        System.out.println("- Indeks GANJIL (1,3,5..): Huruf");
        System.out.println("- Tanpa Simbol/Spasi");
        System.out.println("- Tidak boleh mengandung Email");
        System.out.println("==========================================");
        System.out.println("(Ketik 'exit' pada email untuk keluar)");

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