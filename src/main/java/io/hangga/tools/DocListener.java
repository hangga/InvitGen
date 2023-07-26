package io.hangga.tools;

public interface DocListener {
    void onProgress(int progress, String status);
    void onFinished();
}
