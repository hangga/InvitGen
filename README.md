# Invigen
Tools untuk mengenerate surat undangan secara semi otomatis

### Flow
1. Buat Template, perhatikan formatnya.
2. Masukkan List Nama - nama orang yang diundang.
3. Call method, contoh

```Kotlin
String name = "Budi Cahyo Nugroho,Yuli Priyanto,Kiki,Ipul bin Fakhrul,Sutrianto,Romaidi,Hidayat Sugiharto,Sukidjan,Supriyanto (RT.14),Refli Wulanto,Paeran M,Isham bin Fakhrul,Heri Supriyanto,Rahmat Budi S,Marno,Sudibyo Purnomo,Juni,Afit,Suwardjono M,Kuswardi,Amrul";

new Invigen().generate(name, template, outputPath,
    outputPath1 -> System.out.println("Download disini : " + outputPath1)
);
```