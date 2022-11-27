package pl.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String file;

    public FileSudokuBoardDao(String file) {
        this.file = file + ".txt";
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard objectFile = null;
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            objectFile = (SudokuBoard) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return objectFile;
    }

    @Override
    public void write(SudokuBoard object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
