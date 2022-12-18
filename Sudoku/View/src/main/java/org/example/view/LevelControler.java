package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import pl.sudoku.*;
import java.util.*;


public class LevelControler {

    @FXML
    private GridPane siatka;
    @FXML
    private Label testLabel;
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuSolver sudokuSolver;
    private Difficulty difficulty;

    public void initialize() {
        sudokuSolver =  new BacktrackingSudokuSolver();
        sudokuBoard1 = new SudokuBoard(sudokuSolver);
        sudokuBoard1.solveGame();
        sudokuBoard2 = sudokuBoard1.clone();

        difficulty = UserActionHandling.getDifficulty();
        difficulty.removeSomeFields(sudokuBoard2);

        fillBoard();
    }

    private void fillBoard() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Field field = new Field(i,j,new TextField());
                field.getFieldValue().setMaxHeight(50.0);
                field.getFieldValue().setMaxWidth(50.0);
                field.getFieldValue().setFont(new Font(20));
                if (sudokuBoard2.get(i,j) != 0) {
                    field.getFieldValue().setDisable(true);
                    field.getFieldValue().setText(String.valueOf(sudokuBoard2.get(i,j)));
                }
                field.getFieldValue().textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String prevVal, String nextVal) {
                        if (nextVal.matches("[1-9]")) {
                            int fieldValue = Integer.parseInt(field.getFieldValue().getText());
                            int xCoordinate = field.getxValue();
                            int yCoordinate = field.getyValue();

                            sudokuBoard2.set(xCoordinate, yCoordinate, fieldValue);
                            if (sudokuBoard1.get(xCoordinate, yCoordinate) != fieldValue) {
                                testLabel.setText(resourceBundle.getString("incorrectFieldValue"));
                            } else {
                                testLabel.setText("");
                            }
                        }
                    }
                });
                siatka.add(field.getFieldValue(),i,j);
            }
        }
    }

    public boolean validateInput() {
        boolean validBoard = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String valueInsideTextField = ((TextField) siatka.getChildren().get(i)).getText();
                if (!(valueInsideTextField.matches("[1-9]") || valueInsideTextField.equals(""))) {
                    validBoard = false;
                }
            }
        }
        return validBoard;
    }

    @FXML
    public void saveToFile() throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(resourceBundle.getString("fileSave"));
        dialog.setContentText(resourceBundle.getString("fileCtxText"));
        dialog.setHeaderText(resourceBundle.getString("fileSave"));
        dialog.show();
        String path = dialog.getEditor().getText();
        /*
        try (Dao<SudokuBoard> someDao = getFileDao(path)) {
            someDao.write();
        } catch () {

        }

         */
    }


}