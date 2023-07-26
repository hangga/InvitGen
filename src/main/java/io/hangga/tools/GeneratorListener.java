package io.hangga.tools;

public interface GeneratorListener {

    void OnCopyProgress(int progress, String status);
    void OnCopyFinish(String copyOutput);
    void OnError(String errMsg);
}
