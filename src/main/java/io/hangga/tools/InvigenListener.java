package io.hangga.tools;

public interface InvigenListener {
    void onSuccess(String outputPath);
    void onProgress(int progress, String status);
}
