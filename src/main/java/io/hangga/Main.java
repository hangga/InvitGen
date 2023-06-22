package io.hangga;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static String userDir = System.getProperty("user.dir");
    public static String template = userDir + "/template.docx";
    public static String outputPath = userDir + "/output.docx";
    public static void main(String[] args) {




        /*String[] names = {
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

        new Invigen().generate(names, template, outputPath, new InvigenListener() {
            @Override
            public void onSuccess(String outputPath) {
                System.out.println("Download disini : " + outputPath);
            }
        });*/

        /*String name = "Budi Cahyo Nugroho,Yuli Priyanto,Kiki,Ipul bin Fakhrul,Sutrianto,Romaidi,Hidayat Sugiharto,Sukidjan,Supriyanto (RT.14),Refli Wulanto,Paeran M,Isham bin Fakhrul,Heri Supriyanto,Rahmat Budi S,Marno,Sudibyo Purnomo,Juni,Afit,Suwardjono M,Kuswardi,Amrul";

        new Invigen().generate(name, template, outputPath, new InvigenListener() {
            @Override
            public void onSuccess(String outputPath) {
                    System.out.println("Download disini : " + outputPath);
                }
            }
        );*/

        MainForm mainForm = null;
        if (mainForm == null) {
            mainForm = new MainForm();
        }

        mainForm.setVisible(true);

        //new MainForm();
    }


}