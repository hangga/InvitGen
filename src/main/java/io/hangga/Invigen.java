package io.hangga;

import io.hangga.tools.*;

public class Invigen {

    public static String userDir = System.getProperty("user.dir");
    public static String templatePath = userDir + "/template.docx";
    public static String excelPath = userDir + "/namelist.xlsx";

    public static String tmp = userDir + "/output.docx";

    public void generate(String names, String template, String outputPath, InvigenListener listener) {

        String[] arrNames = names.split(",");
        new Generator().setPath(template, outputPath, arrNames.length, new GeneratorListener() {
            @Override
            public void OnCopyProgress(int progress, String status) {

            }

            @Override
            public void OnCopyFinish(String copyOutput) {
                new DocProcessor().replaceName(arrNames, copyOutput, outputPath, new DocListener() {
                    @Override
                    public void onProgress(int progress, String status) {
                        listener.onProgress(progress, status);
                    }

                    @Override
                    public void onFinished() {

                    }
                }).execute();
                listener.onSuccess(outputPath);
            }


            @Override
            public void OnError(String errMsg) {

            }
        });
    }

    public void generate(String[] names, String template, String outputPath, InvigenListener listener) {
        new Generator().setPath(template, outputPath, names.length, new GeneratorListener() {
            @Override
            public void OnCopyProgress(int progress, String status) {

            }

            @Override
            public void OnCopyFinish(String copyOutput) {
                new DocProcessor().replaceName(names, copyOutput, outputPath, new DocListener() {
                    @Override
                    public void onProgress(int progress, String status) {
                        listener.onProgress(progress, status);
                    }

                    @Override
                    public void onFinished() {

                    }
                }).execute();
                listener.onSuccess(outputPath);
            }


            @Override
            public void OnError(String errMsg) {

            }
        });
    }
}
