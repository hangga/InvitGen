package io.hangga.tools;

public interface OnWriting {
    void onProgress(int progress, String status);
    void onFinished();
}
