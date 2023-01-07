package pl.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.DataBaseErrorException;
import pl.sudoku.exceptions.DataBaseNameException;
import pl.sudoku.exceptions.StatementExecutionException;
import pl.sudoku.exceptions.SudokuBoardNameDuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>{
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private String boardName;

    public JdbcSudokuBoardDao() throws DataBaseErrorException {
        try {
            String DB_URL = "jdbc:derby:SudokuBoardDataBase;create=true";
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            createDatabaseTables();
        } catch (SQLException e) {
            throw new DataBaseErrorException(resourceBundle.getString("ProKomBundle"),e);
        }
    }

    @Override
    public SudokuBoard read() throws DataBaseNameException, StatementExecutionException {
        int [][] SudokuTable = new int[9][9];
        if(this.boardName == null) {
            logger.debug(resourceBundle.getString("dataBaseIsNull"));
            throw new DataBaseNameException(resourceBundle.getString("dataBaseIsNull"),null);
        }
        String getBoardId = "SELECT f.fieldValue, f.xValue, f.yValue "
                + "FROM fields f "
                + "JOIN boards b ON b.boardID = f.boardId "
                + "WHERE boardName = ?";
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(getBoardId);
            while(rs.next()) {
                int x = rs.getInt("xValue");
                int y = rs.getInt("yValue");
                SudokuTable[x][y] = rs.getInt("fieldValue");
            }
            return new SudokuBoard(SudokuTable);
        } catch (SQLException boardIdGet){
            throw new StatementExecutionException(resourceBundle.getString("statementExecutionException"),boardIdGet);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws DataBaseNameException, StatementExecutionException {
        int currentId = 0;
        if(this.boardName == null) {
            throw new DataBaseNameException(resourceBundle.getString("dataBaseNameException"),null);
        }
        String insertBoard = "INSERT INTO boards(boardName) VALUE (?)";
        String getCurrentId = "SELECT boardID " + "FROM boards";
        String insertFields = "INSERT INTO fields (boardId, fieldValue, xValue, yValue) " + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement boardInsert = conn.prepareStatement(insertBoard)) {
            boardInsert.setString(1, this.boardName);
            boardInsert.execute();
            try (Statement getId = conn.createStatement()) {
                ResultSet results = getId.executeQuery(getCurrentId);
                while (results.next()) {
                    currentId = results.getInt("boardID");
                }
                try {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            PreparedStatement fieldsInsert =
                                    conn.prepareStatement(insertFields);
                            fieldsInsert.setInt(1, currentId);
                            fieldsInsert.setInt(2, obj.get(i, j));
                            fieldsInsert.setInt(3, i);
                            fieldsInsert.setInt(4, j);
                            fieldsInsert.executeUpdate();
                        }
                    }
                    conn.commit();
                    logger.debug(resourceBundle.getString("commitMessage"));
                } catch (SQLException fieldInsert) {
                    throw new StatementExecutionException(
                            resourceBundle.getString("statementExecutionException"), fieldInsert);
                }
            } catch (SQLException idException) {
                throw new StatementExecutionException(
                        resourceBundle.getString("statementExecutionException"), idException);
            }
        } catch (SQLException boardInsertException) {
            if (boardInsertException.getSQLState().equals("23505")) {
                throw new SudokuBoardNameDuplicateException(
                        resourceBundle.getString("sBoardAlreadyExist"), null);
            }
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                logger.debug(resourceBundle.getString("rollbackException"));
                throw new StatementExecutionException(
                        resourceBundle.getString("statementExecutionException"), rollbackException);
            }
        }
    }

    @Override
    public void close() {
        try {
            conn.commit();
            logger.debug("commitChanges");
            conn.close();
            if(conn.isClosed()) logger.debug("connectionClosed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabaseTables() throws StatementExecutionException{
        String createBoards = "CREATE TABLE boards"
                + "(boardID INTEGER GENERATED ALWAYS AS IDENTITY, "
                + "boardName VARCHAR(20),"
                + "CONSTRAINT pkBOARDS PRIMARY KEY (boardID))";
        String createFields = "CREATE TABLE fields"
                + "fieldValue INT NOT NULL CHECK (fieldValue BETWEEN 0 AND 8),"
                + "xValue INT NOT NULL CHECK (xValue BETWEEN 0 AND 8),"
                + "yValue INT NOT NULL CHECK (yValue BETWEEN 0 AND 8),"
                + "CONSTRAINT fkFields FOREIGN KEY (boardID) REFERENCES boards(boardID),"
                + "CONSTRAINT pkFields PRIMARY KEY (boardID, fieldvalue, xValue, yValue))";
        try(Statement statement_1 = conn.createStatement()) {
            statement_1.execute(createBoards);
            logger.debug("createBoards");
            conn.commit();
            logger.debug("commitChanges");
        } catch (SQLException boardsCreationException) {
            throw new StatementExecutionException(resourceBundle.getString("boardsTableCreationException"),boardsCreationException);
        }
        try(Statement statement_2 = conn.createStatement()) {
            statement_2.execute(createFields);
            logger.debug("createFields");
            conn.commit();
            logger.debug("commitChanges");
        } catch (SQLException fieldsCreationException) {
            throw new StatementExecutionException(resourceBundle.getString("fieldsTableCreationException"),fieldsCreationException);
        }
    }

    public List<String> retrieveUsedBoardNames() throws StatementExecutionException {
        List<String> namesList = new ArrayList<>();
        String getBoardNames = "SELECT boardName "
                + "FROM boards";
        try (PreparedStatement getNames = conn.prepareStatement(getBoardNames);
             ResultSet results = getNames.executeQuery()) {
            while (results.next()) {
                logger.debug(results.getString("boardName"));
                namesList.add(results.getString("boardName"));
            }
        } catch (SQLException sqlStatement) {
            throw new StatementExecutionException(
                    resourceBundle.getString("statementExecutionException"), sqlStatement);
        }
        return namesList;
    }

    public void deleteAddedElements(String nameOfTheBoard) throws DataBaseErrorException {
        int idOfTheBoard = 0;
        if (nameOfTheBoard == null) {
            throw new DataBaseNameException(
                    resourceBundle.getString("databaseNameException"), null);
        }
        String getBoardId = "SELECT boardID "
                + "FROM boards "
                + "WHERE boardName = ?";
        String deleteFields = "DELETE FROM fields f "
                + "WHERE boardId = ?";
        String deleteBoard = "DELETE FROM boards b "
                + "WHERE boardName = ?";
        try (PreparedStatement boardId = conn.prepareStatement(getBoardId)) {
            // Getting boardId from database
            boardId.setString(1, nameOfTheBoard);
            ResultSet results = boardId.executeQuery();
            if (results.next()) {
                idOfTheBoard = results.getInt("boardID");
            }
            // Removing fields from fields table
            PreparedStatement fieldsDelete = conn.prepareStatement(deleteFields);
            fieldsDelete.setInt(1, idOfTheBoard);
            fieldsDelete.executeUpdate();
            // Removing board entry in boards row
            PreparedStatement boardDelete = conn.prepareStatement(deleteBoard);
            boardDelete.setString(1, nameOfTheBoard);
            boardDelete.executeUpdate();
            conn.commit();
            logger.debug(resourceBundle.getString("commitMessage"));
        } catch (SQLException gettingBoardID) {
            logger.info(resourceBundle.getString("deleteBoardFromDBException"));
            try {
                conn.rollback();
                logger.debug(resourceBundle.getString("rollbackException"));
            } catch (SQLException exception) {
                throw new StatementExecutionException(
                        resourceBundle.getString("statementExecutionException"), exception);
            }
        }
    }
}
