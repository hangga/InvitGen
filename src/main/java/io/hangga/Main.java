package io.hangga;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String template = System.getProperty("user.dir")+"/Undangan 2.docx";
        String outputPath = System.getProperty("user.dir")+"/output.docx";

        String[] names = {
                "Budi Cahyo Nugroho",
                "Yuli Priyanto",
                "Kiki",
                "Ipul bin Fakhrul",
                "Sutrianto",
                "Romaidi",
                "Hidayat Sugiharto",
                "Sukidjan",
                "Supriyanto (RT.14)",
                "Refli Wulanto",
                "Paeran M",
                "Isham bin Fakhrul",
                "Heri Supriyanto",
                "Rahmat Budi S",
                "Marno",
                "Sudibyo Purnomo",
                "Juni",
                "Afit",
                "Suwardjono M",
                "Kuswardi",
                "Amrul"};

        new DocProcessor().doing(names, template, outputPath);
    }

}