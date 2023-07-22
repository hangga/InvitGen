package io.hangga;

public interface OnCopying {

    void OnCopyProgress(int progress, String status);
    void OnCopyFinish(String copyOutput);
    void OnError(String errMsg);
}
