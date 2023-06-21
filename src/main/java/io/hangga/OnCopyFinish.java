package io.hangga;

public interface OnCopyFinish {
    void OnSuccess(String output);
    void OnError(String errMsg);
}
