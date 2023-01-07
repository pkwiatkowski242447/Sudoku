package pl.sudoku;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.sudoku.exceptions.NullObserverException;
import pl.sudoku.exceptions.SudokuBoardCloneException;
import pl.sudoku.exceptions.SudokuBoardInvalidIndexException;
import pl.sudoku.exceptions.SudokuBoardInvalidValueException;
import pl.sudoku.exceptions.SudokuBoxInvalidIndexException;
import pl.sudoku.exceptions.SudokuColumnInvalidIndexException;
import pl.sudoku.exceptions.SudokuRowInvalidIndexException;

public class SudokuBoard implements Serializable, Cloneable {

    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver solver;
    private final Set<Observer> setOfObservers = new HashSet<>();


    private void generateSudokuFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public SudokuBoard(int[][] sudokuBoard) {
        solver = new BacktrackingSudokuSolver();
        boolean correctBoard = true;
        generateSudokuFields();

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (sudokuBoard[i][z] < 0) {
                    correctBoard = false;
                } else if (sudokuBoard[i][z] > 9) {
                    correctBoard = false;
                }
            }
        }

        if (correctBoard) {
            for (int i = 0; i < 9; i++) {
                for (int z = 0; z < 9; z++) {
                    this.set(i, z, sudokuBoard[i][z]);
                }
            }
        } else {
            this.solveGame();
        }

    }

    public SudokuBoard(SudokuSolver solver1) {
        generateSudokuFields();
        solver = solver1;
    }

    public SudokuSolver getSolver() {
        return solver;
    }

    public void solveGame() {
        solver.solve(this);
        checkBoard();
    }

    public int get(int x, int y) throws SudokuBoardInvalidIndexException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (x >= 9 || y >= 9 || x < 0 || y < 0) {
            throw new SudokuBoardInvalidIndexException(resourceBundle.getString(
                    "incorrectFieldCoordinates"));
        } else {
            return board[x][y].getFieldValue();
        }
    }

    public void set(int x, int y, int value) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (value >= 0 && value <= 9) {
            board[x][y].setFieldValue(value);
        } else {
            throw new SudokuBoardInvalidValueException(
                    resourceBundle.getString("valueOutOfBounds"));
        }
        notifyObservers();
    }

    public Set<Observer> getSetOfObservers() {
        return Collections.unmodifiableSet(setOfObservers);
    }

    public int[][] convertToIntArray() {
        int[][] finalArray = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                finalArray[i][j] = board[i][j].getFieldValue();
            }
        }
        return finalArray;
    }

    public Method getCheckBoard() throws NoSuchMethodException {
        Method method = this.getClass().getDeclaredMethod("checkBoard");
        method.setAccessible(true);
        return method;
    }

    public boolean checkBoard() {
        boolean correctBoard = true;
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify()) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(3 * i, 3 * j).verify()) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    public SudokuRow getRow(int y) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (y >= 0 & y < 9) {
            List<SudokuField> row = Arrays.asList(new SudokuField[9]);
            for (int i = 0; i < 9; i++) {
                row.set(i, new SudokuField());
            }
            for (int i = 0; i < 9; i++) {
                row.get(i).setFieldValue(get(y, i));
            }
            return new SudokuRow(row);
        } else {
            throw new SudokuRowInvalidIndexException(
                    resourceBundle.getString("exceptionSudokuRow"));
        }
    }

    public SudokuColumn getColumn(int x) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (x >= 0 & x < 9) {
            List<SudokuField> column = Arrays.asList(new SudokuField[9]);
            for (int i = 0; i < 9; i++) {
                column.set(i, new SudokuField());
            }
            for (int i = 0; i < 9; i++) {
                column.get(i).setFieldValue(get(i, x));
            }
            return new SudokuColumn(column);
        } else {
            throw new SudokuColumnInvalidIndexException(
                    resourceBundle.getString("exceptionSudokuColumn"));
        }
    }

    public SudokuBox getBox(int x, int y) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if ((x >= 0 & y >= 0) & (x < 9 & y < 9)) {
            List<SudokuField> box = Arrays.asList(new SudokuField[9]);
            int matrixFirstLine = 3 * (x / 3);
            int matrixFirstColumn = 3 * (y / 3);
            for (int i = 0; i < 9; i++) {
                box.set(i, new SudokuField());
            }
            for (int i = 0; i < 3; i++) {
                for (int f = 0; f < 3; f++) {
                    box.get(i * 3 + f)
                            .setFieldValue(get(matrixFirstLine + i, matrixFirstColumn + f));
                }
            }
            return new SudokuBox(box);
        } else {
            throw new SudokuBoxInvalidIndexException(
                    resourceBundle.getString("exceptionSudokuBox"));
        }
    }

    public void addObserver(Observer observer) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (observer != null) {
            setOfObservers.add(observer);
        } else {
            throw new NullObserverException(
                    resourceBundle.getString("addingNullObserver"));
        }
    }

    public void removeObserver(Observer observer) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (observer != null) {
            setOfObservers.remove(observer);
        } else {
            throw new NullObserverException(
                    resourceBundle.getString("removingNullObserver"));
        }
    }

    public void notifyObservers() {
        for (Observer observer : setOfObservers) {
            observer.update(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        stringBuilder.append('\n');
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < 3; z++) {
                        stringBuilder.append(get(i * 3 + l, j * 3 + z));
                    }
                }
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public SudokuBoard clone() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);

            objectOutput.writeObject(this);
            for (Observer observer : this.getSetOfObservers()) {
                objectOutput.writeObject(observer);
            }

            ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);

            SudokuBoard sudokuBoard = (SudokuBoard) objectInput.readObject();
            for (int i = 0; i < this.getSetOfObservers().size(); i++) {
                sudokuBoard.addObserver((Observer) objectInput.readObject());
            }

            byteInput.close();
            objectInput.close();
            byteOutput.close();
            objectOutput.close();

            return sudokuBoard;
        } catch (IOException | ClassNotFoundException exception) {
            throw new SudokuBoardCloneException(
                    resourceBundle.getString("sudokuBoardCloneException"), exception.getCause());
        }
    }
}
