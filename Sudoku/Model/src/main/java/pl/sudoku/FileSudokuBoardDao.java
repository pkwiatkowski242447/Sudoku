package pl.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.InputOutputOperationException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    private String fileName;

    public FileSudokuBoardDao(final String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() throws InputOutputOperationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        SudokuBoard objectFile;
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            objectFile = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            throw new InputOutputOperationException(
                    resourceBundle.getString("fileDaoReadException"));
        }
        return objectFile;
    }

    @Override
    public void write(SudokuBoard exampleSudokuBoard) throws InputOutputOperationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(exampleSudokuBoard);
        } catch (IOException | NullPointerException exception) {
            throw new InputOutputOperationException(
                    resourceBundle.getString("fileDaoWriteException"));
        }
    }

    @Override
    public void close() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        logger.info(resourceBundle.getString("resourcesClosed"));
    }
}
