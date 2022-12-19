package pl.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.FileException;
import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;
import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    private String fileName;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public FileSudokuBoardDao(final String fileName) throws GeneralDaoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        this.fileName = fileName;
        try {
            fileOutputStream = new FileOutputStream(this.fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            fileInputStream = new FileInputStream(this.fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (FileNotFoundException exception) {
            throw new FileException(
                    resourceBundle.getString("fileNotFound"), exception.getCause());
        } catch (IOException exception) {
            throw new InputOutputOperationException(
                    resourceBundle.getString("IOException"), exception.getCause());
        }
    }

    @Override
    public SudokuBoard read() throws FileSudokuBoardDaoInputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        SudokuBoard objectFile;
        try {
            objectFile = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new FileSudokuBoardDaoInputException(
                    resourceBundle.getString("fileDaoReadException"), exception.getCause());
        }
        return objectFile;
    }

    @Override
    public void write(SudokuBoard exampleSudokuBoard) throws FileSudokuBoardDaoOutputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try {
            objectOutputStream.writeObject(exampleSudokuBoard);
        } catch (IOException exception) {
            throw new FileSudokuBoardDaoOutputException(
                    resourceBundle.getString("fileDaoWriteException"), exception.getCause());
        }
    }

    @Override
    public void close() throws InputOutputOperationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try {
            fileInputStream.close();
            fileOutputStream.close();
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException ioException) {
            throw new InputOutputOperationException(
                    resourceBundle.getString("resourcesException"), ioException.getCause());
        }
        logger.info(resourceBundle.getString("resourcesClosed"));
    }
}
