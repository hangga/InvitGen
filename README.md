# InviGen
InviGen adalah tools untuk mengenerate surat undangan(berupa dokumen Word dengan format .docx) dengan jumlah banyak (sesuai daftar nama dalam Excel) secara semi otomatis.

<img src="https://github.com/hangga/invigen/blob/main/invigen-gui-load-xls.png?raw=true" />

### Cerita dikit
Jadi di tahun 1444H ini saya kembali diamanahi sebagai Sekertaris di Panitia Qurban Masjid Muhtadien.   
Nah, saat mau bikin undangan untuk 100-an personil, saya kepikiran bagaimana caranya agar tidak perlu capek nulis tangan nama-namanya. Saya juga tidak mau capek copy-paste layout paragraf 100 kali Hahaha.   

Akhirnya ba'da Maghrib buka IDE meluncur coding bikin tools ini sembari menunggu waktu 'Isya.<br/><br/>Alhamdulillah begitu selesai langsung release(menyusul sedikit optimasi & improvement di hari berikutnya) sehingga pekerjaan sekertaris tahun-tahun berikutnya sedikit lebih ringan.

### System Requirement
- Min Jdk 1.8 atau Java 8 Keatas.
### Cara Menggunakan InviGen
#### 1. Membuat Template Undangan
Buatlah template dokumen undangan pake aplikasi Office(bisa MS Word atau Libreoffice silahkan) dengan format .docx.

**Catatan**  
1. Karena InviGen hanya bisa mendeteksi tabel sehingga semua paragraf pada template **harus masuk dalam tabel**, meskipun hanya 1 column dan 1 row.
2. Pada isian nama, ketiklah **--nama**  👈🏽(harus persis seperti ini, penting & perhatikan).
- Contoh :<br/><br/>
<img src="https://raw.githubusercontent.com/hangga/invigen/main/template.png" width="100%"/>

#### 2. Pilih Template
Pilih template yang telah anda buat tadi dengan cara klik tombol **Pilih Template**.

#### 3. Load .xls file.
Pilih file berisi daftar nama undangan dalam format .xls
<br/>
<img width="70%" src="https://raw.githubusercontent.com/hangga/invigen/main/invigen-gui-pilih-file-xls.png.png"/>

#### 4. Generate
Klik tombol **Generate**. Jika proses berhasil, maka akan muncul dialog untuk menyimpan file hasil generate bernama **output-invigen.docx**.
<br/><br/>
<img width="70%" src="https://github.com/hangga/invigen/blob/main/invigen-gui-save-output.png?raw=true"/>
<br/>

#### 5. Selesai
Buka file **output-invigen.docx** lalu rapihkan sendiri jika kurang rapih.<br/><br/>
Contoh Outputnya

<img src="https://github.com/hangga/invigen/blob/main/output.png?raw=true" width="80%">

Tools ini beserta source code boleh didownload secara bebas (lihat link **Release** di sidebar kanan dibawah **About**).
