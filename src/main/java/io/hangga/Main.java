package io.hangga;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        String template = userDir + "/template.docx";
        String outputPath = userDir + "/output.docx";

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

        new CopyContent().doing(template, outputPath, names.length, new OnCopyFinish() {
            @Override
            public void OnSuccess(String output) {
                new DocProcessor().doing(names, output, outputPath);
            }

            @Override
            public void OnError(String errMsg) {

            }
        });
    }


}