package org.example.view;

import javafx.util.StringConverter;

public class ValueConverter extends StringConverter<Number> {
    public ValueConverter() {
        super();
    }

    @Override
    public String toString(Number number) {
        if (number.intValue() == 0) {
            return "";
        } else {
            return String.valueOf(number.intValue());
        }
    }

    @Override
    public Number fromString(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }
}
