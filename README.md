# UTS Pengujian dan implementasi Sistem - Kelompok 1

## Proyek: Advanced Credential Engine
Aplikasi ini merupakan mesin validasi kredensial (password) yang menerapkan kebijakan keamanan khusus sesuai dengan aturan bisnis yang telah ditetapkan.

## Aturan Validasi:
1. **Panjang Password**: Minimum 12 karakter dan maksimum 20 karakter.
2. **Kombinasi Karakter**: Wajib mengandung campuran angka dan huruf.
3. **Pengecekan Indeks Genap**: Angka hanya diperbolehkan berada pada posisi indeks genap (0, 2, 4, ...).
4. **Pengecekan Indeks Ganjil**: Huruf hanya diperbolehkan berada pada posisi indeks ganjil (1, 3, 5, ...).
5. **Kebijakan Email**: Password tidak boleh mengandung bagian prefix dari alamat email pengguna.

## Penanganan Kesalahan:
Sistem akan melemparkan `SecurityPolicyException` jika ditemukan pelanggaran terhadap salah satu aturan di atas.

## TEST
Untuk Melakukan pengetesan kamu perlu clone dengan cara **open with dekstop**  setelah itu buka terminal:
**bash**
mvn compile
dan
java -cp target/classes com.pnb.uts.AdvancedCredentialEngine

## Struktur Proyek:
* src/main/java: Berisi logika utama aplikasi (AdvancedCredentialEngine.java).
* pom.xml: Konfigurasi Maven dan dependensi JUnit 5.

## Kelompok 1 
1. Brian Bactiar Sitompul
2. Pande Putu Aby Gotama
3. I Nyoman Sabda Reka Sahadana
4. Ibrahim Maulana Arifin
5. Mario Wahyu Pratama
6.  Ni Kadek Junya Dwi Lestari
