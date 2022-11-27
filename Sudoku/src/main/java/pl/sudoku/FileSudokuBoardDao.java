package pl.sudoku;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard>{

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
       }
       catch (IOException | ClassNotFoundException ex) {
           ex.printStackTrace();
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
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
