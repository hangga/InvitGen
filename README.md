# Invigen
Tools untuk mengenerate surat undangan secara semi otomatis

### Flow
1. Buat Template, perhatikan formatnya.
<img src="https://raw.githubusercontent.com/hangga/invigen/main/template.png" width="100%"/>
2. Init nama - nama orang yang diundang, bisa `String` atau `String[]`.
```kotlin
String names = "Budi Cahyo Nugroho,Yuli Priyanto,Kiki,Ipul bin Fakhrul,Sutrianto,Romaidi,Hidayat Sugiharto,Sukidjan,Supriyanto (RT.14),Refli Wulanto,Paeran M,Isham bin Fakhrul,Heri Supriyanto,Rahmat Budi S,Marno,Sudibyo Purnomo,Juni,Afit,Suwardjono M,Kuswardi,Amrul";
```
3. Lalu tinggal panggil method `new Invigen().generate()`. Lihat contoh.


```kotlin
new Invigen().generate(name, template, outputPath, new InvigenListener() {
    @Override
    public void onSuccess(String outputPath) {
        System.out.println("Download disini : " + outputPath);
        }
    }
);
```

Pake gaya lambda expressions, biar keren


```Kotlin
new Invigen().generate(names, template, outputPath,
    outputPath1 -> System.out.println("Download disini : " + outputPath1)
);
```