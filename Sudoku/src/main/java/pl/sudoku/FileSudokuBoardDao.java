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
        SudokuBoard objectFile;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            objectFile = (SudokuBoard) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return objectFile;
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
