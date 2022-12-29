package org.example.view;

import javafx.util.StringConverter;

public class InputConverter extends StringConverter<String> {
    @Override
    public String toString(String s) {
        return s;
    }

    @Override
    public String fromString(String s) {
        return s;
    }
}
