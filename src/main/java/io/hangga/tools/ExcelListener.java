package io.hangga.tools;

import java.util.List;

public interface ExcelListener {
    void OnGetNames(List<String> arrNames, String names);
    void OnGetNameAt(String name);
}
