package io.hangga;

public class Invigen {

    void generate(String names, String template, String outputPath, InvigenListener listener){

        String[] arrNames = names.split(",");
        new Generator().doing(template, outputPath, arrNames.length, new OnCopyFinish() {
            @Override
            public void OnSuccess(String output) {
                new DocProcessor().doing(arrNames, output, outputPath);
                listener.onSuccess(outputPath);
            }

            @Override
            public void OnError(String errMsg) {

            }
        });
    }
    void generate(String[] names, String template, String outputPath, InvigenListener listener){
        new Generator().doing(template, outputPath, names.length, new OnCopyFinish() {
            @Override
            public void OnSuccess(String output) {
                new DocProcessor().doing(names, output, outputPath);
                listener.onSuccess(outputPath);
            }

            @Override
            public void OnError(String errMsg) {

            }
        });
    }
}
