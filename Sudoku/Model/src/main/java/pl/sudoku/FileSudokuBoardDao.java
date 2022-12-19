package pl.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final Logger log = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    private String fileName;

    public FileSudokuBoardDao(final String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() throws FileSudokuBoardDaoOutputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        SudokuBoard objectFile;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            objectFile = (SudokuBoard) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new FileSudokuBoardDaoOutputException(resourceBundle.getString(
                    "FileSudokuBoardDaoOutputException"));
        }
        return objectFile;
    }

    @Override
    public void write(SudokuBoard exampleSudokuBoard) throws FileSudokuBoardDaoInputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);) {
            objectOut.writeObject(exampleSudokuBoard);
        } catch (IOException ioException) {
            throw new
                    FileSudokuBoardDaoInputException(resourceBundle.getString(
                            "FileSudokuBoardDaoInputException"));
        }
    }

    @Override
    public void close() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        log.info(resourceBundle.getString("FileSudokuBoardDaoClose"));
    }
}
