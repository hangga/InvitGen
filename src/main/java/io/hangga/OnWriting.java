package io.hangga;

public interface OnWriting {
    void onProgress(int progress, String status);
    void onFinished();
}
