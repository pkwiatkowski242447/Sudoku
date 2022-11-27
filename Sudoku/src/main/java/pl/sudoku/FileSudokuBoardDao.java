package pl.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String fileName;

    public FileSudokuBoardDao(final String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard readObject;
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            readObject = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException classException) {
            throw new RuntimeException(classException);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return readObject;
    }

    @Override
    public void write(SudokuBoard exampleSudokuBoard) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(exampleSudokuBoard);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
