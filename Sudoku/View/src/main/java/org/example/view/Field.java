package org.example.view;

import javafx.scene.control.TextField;

public class Field {
    private final int xValue;
    private final int yValue;
    private TextField fieldValue;

    public Field(int xValue, int yValue, TextField fieldValue) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.fieldValue = fieldValue;
    }

    public int getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public TextField getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(TextField fieldValue) {
        this.fieldValue = fieldValue;
    }
}
