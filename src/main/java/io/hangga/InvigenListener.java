package io.hangga;

public interface InvigenListener {
    void onSuccess(String outputPath);
    void onProgress(int progress, int max);
}
