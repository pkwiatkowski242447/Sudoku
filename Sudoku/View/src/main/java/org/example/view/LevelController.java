package org.example.view;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import pl.sudoku.Dao;
import pl.sudoku.JdbcSudokuBoardDao;
import pl.sudoku.SudokuBoard;
import pl.sudoku.SudokuSolver;
import pl.sudoku.BacktrackingSudokuSolver;
import pl.sudoku.SudokuBox;
import pl.sudoku.exceptions.DatabaseErrorException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;
import pl.sudoku.exceptions.GeneralDaoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;
import static pl.sudoku.SudokuBoardDaoFactory.getJdbcDao;


public class LevelController {

    @FXML
    private GridPane mesh;
    @FXML
    private Label testLabel;
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuBoard sudokuBoard3;

    public void initialize() {
        if (UserActionHandling.getUserStartBoard() == null
                || UserActionHandling.getFilledPartiallyBoard() == null) {
            SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
            sudokuBoard1 = new SudokuBoard(sudokuSolver);
            sudokuBoard1.solveGame();
            sudokuBoard2 = sudokuBoard1.clone();

            Difficulty difficulty = UserActionHandling.getDifficulty();
            difficulty.removeSomeFields(sudokuBoard2);
            sudokuBoard3 = sudokuBoard2.clone();

            fillBoard();
        } else {
            sudokuBoard2 = UserActionHandling.getUserStartBoard();
            sudokuBoard3 = UserActionHandling.getFilledPartiallyBoard();

            loadBoards();
        }
    }

    private void fillBoard() {
        ResourceBundle bundle = ResourceBundle.getBundle("ViewBundle");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Field field = new Field(i, j, new TextField());
                field.getFieldValue().setMaxHeight(50.0);
                field.getFieldValue().setMaxWidth(50.0);
                field.getFieldValue().setFont(new Font(20));
                TextFormatter<String> newTextFormat = new TextFormatter<>(new InputConverter(), "" ,new InputFilter());
                field.getFieldValue().setTextFormatter(newTextFormat);
                if (sudokuBoard2.get(i, j) != 0) {
                    field.getFieldValue().setDisable(true);
                    field.getFieldValue().setText(String.valueOf(sudokuBoard2.get(i, j)));
                }
                SudokuFieldWrapper someField = new SudokuFieldWrapper(i, j, sudokuBoard2);
                Bindings.bindBidirectional(field.getFieldValue().textProperty(),
                        someField.fieldValueProperty(), new ValueConverter());

                someField.fieldValueProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        field.getFieldValue().setText(new ValueConverter().toString(t1.intValue()));
                        if (!someField.getSudokuBoard().checkBoard()) {
                            testLabel.setText(bundle.getString("incorrectFieldValue"));
                        } else {
                            testLabel.setText(bundle.getString("printNothing"));
                        }
                    }
                });

                field.getFieldValue().textProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        someField.setFieldValue(new ValueConverter().fromString(t1).intValue());
                        if (!someField.getSudokuBoard().checkBoard()) {
                            testLabel.setText(bundle.getString("incorrectFieldValue"));
                        } else {
                            testLabel.setText(bundle.getString("printNothing"));
                            isTheGameFinished();
                        }
                    }
                });

                mesh.add(field.getFieldValue(),j,i);
            }
        }
    }

    private void loadBoards() {
        ResourceBundle bundle = ResourceBundle.getBundle("ViewBundle");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Field field = new Field(i, j, new TextField());
                field.getFieldValue().setMaxHeight(50.0);
                field.getFieldValue().setMaxWidth(50.0);
                field.getFieldValue().setFont(new Font(20));
                TextFormatter<String> newTextFormat = new TextFormatter<>(new InputConverter(), "" ,new InputFilter());
                field.getFieldValue().setTextFormatter(newTextFormat);
                if (sudokuBoard3.get(i, j) != 0) {
                    field.getFieldValue().setDisable(true);
                    field.getFieldValue().setText(String.valueOf(sudokuBoard3.get(i,j)));
                } else {
                    if (sudokuBoard2.get(i,j) != 0) {
                        field.getFieldValue().setText(String.valueOf(sudokuBoard2.get(i,j)));
                    }
                    SudokuFieldWrapper someField = new SudokuFieldWrapper(i, j, sudokuBoard2);
                    Bindings.bindBidirectional(field.getFieldValue().textProperty(),
                            someField.fieldValueProperty(), new ValueConverter());

                    someField.fieldValueProperty().addListener(new ChangeListener<>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            field.getFieldValue().setText(new ValueConverter().toString(t1.intValue()));
                            if (!someField.getSudokuBoard().checkBoard()) {
                                testLabel.setText(bundle.getString("incorrectFieldValue"));
                            } else {
                                testLabel.setText(bundle.getString("printNothing"));
                            }
                        }
                    });

                    field.getFieldValue().textProperty().addListener(new ChangeListener<>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                            someField.setFieldValue(new ValueConverter().fromString(t1).intValue());
                            if (!someField.getSudokuBoard().checkBoard()) {
                                testLabel.setText(bundle.getString("incorrectFieldValue"));
                            } else {
                                testLabel.setText(bundle.getString("printNothing"));
                                isTheGameFinished();
                            }
                        }
                    });
                }
                mesh.add(field.getFieldValue(), j, i);
            }
        }
    }

    private void isTheGameFinished() {
        ResourceBundle bundle = ResourceBundle.getBundle("ViewBundle");
        if (checkBoardCorrectness()) {
            System.out.println(bundle.getString("theGameIsWon"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gratulacje");
            alert.setHeaderText("Zwycięstwo!");
            alert.setContentText("Brawo! Udało ci się rozwiązać sudoku.");
            alert.showAndWait();
            goBackToMainMenu();
        }
    }

    private boolean checkBoardCorrectness() {
        boolean correctBoard = true;
        List<Integer> listOfNums = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sudokuBoard2.getRow(i);
            for (int j = 0; j < 9; j++) {
                int structureValue = sudokuBoard2.getRow(i).getValue(j);
                if (listOfNums.contains(structureValue) || structureValue == 0) {
                    correctBoard = false;
                } else {
                    listOfNums.add(structureValue);
                }
            }
            listOfNums.removeAll(listOfNums);
            for (int k = 0; k < 9; k++) {
                int structureValue = sudokuBoard2.getColumn(i).getValue(k);
                if (listOfNums.contains(structureValue) || structureValue == 0) {
                    correctBoard = false;
                }  else {
                    listOfNums.add(structureValue);
                }
            }
            listOfNums.removeAll(listOfNums);
        }
        listOfNums.removeAll(listOfNums);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                SudokuBox box = sudokuBoard2.getBox(3 * i, 3 * j);
                for (int k = 0; k < 9; k++) {
                    int structureValue = box.getValue(k);
                    if (listOfNums.contains(structureValue) || structureValue == 0) {
                        correctBoard = false;
                    }  else {
                        listOfNums.add(structureValue);
                    }
                }
                listOfNums.removeAll(listOfNums);
            }
        }
        return correctBoard;
    }

    @FXML
    public void goBackToMainMenu() {
        UserActionHandling.setUserStartBoard(null);
        UserActionHandling.setFilledPartiallyBoard(null);
        ResourceBundle bundle = ResourceBundle.getBundle("ViewBundle");
        try {
            StageSetup.buildStage("main-form.fxml",
                    bundle.getString("gameTitle"), bundle);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void saveToFile() throws Exception {
        FileChooser chooseFile = new FileChooser();
        File file = chooseFile.showSaveDialog(StageSetup.getStage());
        try(Dao<SudokuBoard> fileFileDao = getFileDao(file.getAbsolutePath())) {
            fileFileDao.write(sudokuBoard2);
            fileFileDao.write(sudokuBoard3);
        } catch (FileSudokuBoardDaoOutputException daoException) {
            throw new GeneralDaoException(
                    daoException.getMessage(), daoException.getCause());
        }
    }

    @FXML
    public void saveToDatabase() throws Exception {
        String boardName = null;
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Podaj nazwę planszy, która zostanie zapisana do bazy danych");
        td.setHeaderText("Wybór nazwy");
        td.setContentText("Nazwa planszy: ");
        boardName = td.showAndWait().get();
        try (JdbcSudokuBoardDao databaseDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            databaseDao.setBoardName(boardName);
            databaseDao.write(sudokuBoard2);
            databaseDao.setBoardName("_" + boardName);
            databaseDao.write(sudokuBoard3);
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(
                    daoException.getMessage(), daoException.getCause());
        }
    }
}