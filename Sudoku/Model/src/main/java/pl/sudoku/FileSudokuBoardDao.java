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

    public FileSudokuBoardDao(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws FileSudokuBoardDaoInputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        SudokuBoard objectFile;
        try {
            if (fileInputStream == null) {
                fileInputStream = new FileInputStream(this.fileName);
                objectInputStream = new ObjectInputStream(fileInputStream);
            }
            objectFile = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            logger.error(exception.getClass().toString());
            throw new FileSudokuBoardDaoInputException(
                    resourceBundle.getString("fileDaoReadException"), exception.getCause());
        }
        return objectFile;
    }

    @Override
    public void write(SudokuBoard exampleSudokuBoard) throws FileSudokuBoardDaoOutputException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try {
            if (fileOutputStream == null) {
                fileOutputStream = new FileOutputStream(this.fileName);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
            }
            objectOutputStream.writeObject(exampleSudokuBoard);
        } catch (IOException exception) {
            logger.error(exception.getClass().toString());
            throw new FileSudokuBoardDaoOutputException(
                    resourceBundle.getString("fileDaoWriteException"), exception.getCause());
        }
    }

    @Override
    public void close() throws InputOutputOperationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try {
            if (fileInputStream != null) {
                fileInputStream.close();
                objectInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
                objectOutputStream.close();
            }
        } catch (IOException ioException) {
            logger.error(ioException.getClass().toString());
            throw new InputOutputOperationException(
                    resourceBundle.getString("resourcesException"), ioException.getCause());
        }
        logger.info(resourceBundle.getString("resourcesClosed"));
    }
}
