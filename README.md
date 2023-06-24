# Invigen
Tools untuk mengenerate surat undangan secara semi otomatis

### Story
Jadi di tahun 1444H ini saya kembali diamanahi sebagai Sekertaris di Panitia Qurban Masjid Muhtadien.   
Nah, saat mau bikin undangan untuk 118 personil, sy kepikiran bgmn caranya agar tidak capek nulis tangan nama-nama kepada Yth-nya.   

Akhirnya ba'da Maghrib bikin tools ini sembari menunggu waktu 'Isya.  

### Flow
#### 1. Buat Template
Buat template dokumen pake aplikasi Office(bisa MS Word atau Libreoffice silahkan) dengan format .docx.  

**Catatan**  
- Tool ini hanya bisa mendeteksi tabel.
- Sehingga semua paragraf pada template **harus masuk dalam tabel**, meskipun hanya 1 column dan 1 row.
- Contoh :<br/><br/>
<img src="https://raw.githubusercontent.com/hangga/invigen/main/template.png" width="80%"/>

#### 2. Pilih Template
Pilih template yang telah anda buat tadi dengan cara klik tombol **Pilih Template**.

#### 3. Ketik nama-nama pada Text Area
Ketik nama-nama yang akan anda undang.
<img width="400" src="https://github.com/hangga/invigen/blob/main/pilih-template.png?raw=true"/>

#### 4. Generate
Klik tombol **Generate**. Jika proses berhasil, maka akan muncul dialog untuk menyimpan file hasil generate bernama **output-invigen.docx**.
<br/><br/>
<img width="400" src="https://github.com/hangga/invigen/blob/main/save-output.png?raw=true"/>
<br/>
Contoh Outputnya

<img src="https://github.com/hangga/invigen/blob/main/output.png?raw=true" width="80%">