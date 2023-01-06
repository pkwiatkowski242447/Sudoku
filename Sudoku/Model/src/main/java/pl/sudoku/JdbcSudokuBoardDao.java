package pl.sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.CloseDatabaseException;
import pl.sudoku.exceptions.DatabaseErrorException;
import pl.sudoku.exceptions.DatabaseNameException;
import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.StatementExecutionException;
import pl.sudoku.exceptions.SudokuBoardNameDuplicateException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    private final Logger log = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private String boardName;
    private final ResourceBundle bundle = ResourceBundle.getBundle("ProKomBundle");
    private final String javaDatabaseUrl = "jdbc:derby:SudokuBoardDataBase;create=true";
    private Connection connection;

    public JdbcSudokuBoardDao() throws GeneralDaoException {
        try {
            connection = DriverManager.getConnection(this.javaDatabaseUrl);
            connection.setAutoCommit(false);
            log.info(bundle.getString("connectionEstablished"));
            createDatabaseTables();
        } catch (SQLException sql) {
            throw new DatabaseErrorException(
                    bundle.getString("dataBaseErrorException"), sql);
        }
    }

    @Override
    public SudokuBoard read() throws DatabaseErrorException {
        int[][] sudokuTable = new int[9][9];
        if (this.boardName == null) {
            log.debug(bundle.getString("databaseNameIsNull"));
            throw new DatabaseNameException(
                    bundle.getString("databaseNameException"), null);
        }
        log.debug(bundle.getString("databaseNameIsNotNull"));
        String getBoardId = "SELECT f.fieldValue, f.xValue, f.yValue "
                + "FROM fields f "
                + "JOIN boards b ON b.boardID = f.boardId "
                + "WHERE boardName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(getBoardId)) {
            stmt.setString(1, this.boardName);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int x = results.getInt("xValue");
                int y = results.getInt("yValue");
                sudokuTable[x][y] = results.getInt("fieldValue");
            }
            return new SudokuBoard(sudokuTable);
        } catch (SQLException boardIdGet) {
            throw new StatementExecutionException(
                    bundle.getString("statementExecutionException"), boardIdGet);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws DatabaseErrorException {
        int currentId = 0;
        if (this.boardName == null) {
            throw new DatabaseNameException(bundle.getString("databaseNameException"), null);
        }
        String insertBoard = "INSERT INTO boards(boardName) VALUES (?)";
        String getCurrentId = "SELECT boardID "
                + "FROM boards";
        String insertFields = "INSERT INTO fields (boardId, fieldValue, xValue, yValue) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement boardInsert = connection.prepareStatement(insertBoard)) {
            boardInsert.setString(1, this.boardName);
            boardInsert.execute();
            try (Statement getId = connection.createStatement()) {
                ResultSet results = getId.executeQuery(getCurrentId);
                while (results.next()) {
                    currentId = results.getInt("boardID");
                }
                try {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            PreparedStatement fieldsInsert =
                                    connection.prepareStatement(insertFields);
                            fieldsInsert.setInt(1, currentId);
                            fieldsInsert.setInt(2, obj.get(i, j));
                            fieldsInsert.setInt(3, i);
                            fieldsInsert.setInt(4, j);
                            fieldsInsert.executeUpdate();
                        }
                    }
                    connection.commit();
                    log.debug(bundle.getString("commitMessage"));
                } catch (SQLException fieldInsert) {
                    throw new StatementExecutionException(
                            bundle.getString("statementExecutionException"), fieldInsert);
                }
            } catch (SQLException idException) {
                throw new StatementExecutionException(
                        bundle.getString("statementExecutionException"), idException);
            }
        } catch (SQLException boardInsertException) {
            if (boardInsertException.getSQLState().equals("23505")) {
                throw new SudokuBoardNameDuplicateException(
                        bundle.getString("sBoardAlreadyExist"), null);
            }
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                log.debug(bundle.getString("rollbackException"));
                throw new StatementExecutionException(
                        bundle.getString("statementExecutionException"), rollbackException);
            }
        }
    }

    private void createDatabaseTables() throws StatementExecutionException {
        String createBoards = "CREATE TABLE boards "
                + "(boardID INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
                + "boardName VARCHAR(50) NOT NULL, "
                + "CONSTRAINT pkBoards PRIMARY KEY (boardID), "
                + "CONSTRAINT boardNameConst UNIQUE (boardName))";
        String createFields = "CREATE TABLE fields "
                + "(boardId INT NOT NULL, "
                + "fieldValue INT NOT NULL CHECK ( fieldValue BETWEEN 0 AND 9 ), "
                + "xValue INT NOT NULL CHECK ( xValue BETWEEN 0 AND 8 ), "
                + "yValue INT NOT NULL CHECK ( yValue BETWEEN 0 AND 8 ), "
                + "CONSTRAINT fkFields FOREIGN KEY (boardId) REFERENCES boards(boardID), "
                + "CONSTRAINT pkFields PRIMARY KEY (boardId, fieldValue, xValue, yValue))";
        try (Statement boardsCreate = connection.createStatement()) {
            boardsCreate.execute(createBoards);
            log.debug(bundle.getString("boardTableCreated"));
            connection.commit();
            log.debug(bundle.getString("commitMessage"));
        } catch (SQLException boardsCreationException) {
            if (boardsCreationException.getSQLState().equals("X0Y32")) {
                log.debug(bundle.getString("boardsTableExist"));
            } else {
                log.debug(bundle.getString("boardsTableCreationException"));
                throw new StatementExecutionException(
                        bundle.getString("boardsTableCreationException"), boardsCreationException);
            }
        }
        try (Statement fieldsCreate = connection.createStatement()) {
            fieldsCreate.execute(createFields);
            log.debug(bundle.getString("fieldsTableCreated"));
            connection.commit();
            log.debug(bundle.getString("commitMessage"));
        } catch (SQLException fieldsCreationException) {
            if (fieldsCreationException.getSQLState().equals("X0Y32")) {
                log.debug(bundle.getString("fieldsTableExist"));
            } else {
                log.debug(bundle.getString("fieldsTableCreationException"));
                throw new StatementExecutionException(
                        bundle.getString("fieldsTableCreationException"), fieldsCreationException);
            }
        }
    }

    public List<String> retrieveUsedBoardNames() throws StatementExecutionException {
        List<String> namesList = new ArrayList<>();
        String getBoardNames = "SELECT boardName "
                + "FROM boards";
        try (PreparedStatement getNames = connection.prepareStatement(getBoardNames);
            ResultSet results = getNames.executeQuery()) {
            while (results.next()) {
                log.debug(results.getString("boardName"));
                namesList.add(results.getString("boardName"));
            }
        } catch (SQLException sqlStatement) {
            throw new StatementExecutionException(
                    bundle.getString("statementExecutionException"), sqlStatement);
        }
        return namesList;
    }

    public void deleteAddedElements(String nameOfTheBoard) throws DatabaseErrorException {
        int idOfTheBoard = 0;
        if (nameOfTheBoard == null) {
            throw new DatabaseNameException(
                    bundle.getString("databaseNameException"), null);
        }
        String getBoardId = "SELECT boardID "
                + "FROM boards "
                + "WHERE boardName = ?";
        String deleteFields = "DELETE FROM fields f "
                + "WHERE boardId = ?";
        String deleteBoard = "DELETE FROM boards b "
                + "WHERE boardName = ?";
        try (PreparedStatement boardId = connection.prepareStatement(getBoardId)) {
            // Getting boardId from database
            boardId.setString(1, nameOfTheBoard);
            ResultSet results = boardId.executeQuery();
            if (results.next()) {
                idOfTheBoard = results.getInt("boardID");
            }
            // Removing fields from fields table
            PreparedStatement fieldsDelete = connection.prepareStatement(deleteFields);
            fieldsDelete.setInt(1, idOfTheBoard);
            fieldsDelete.executeUpdate();
            // Removing board entry in boards row
            PreparedStatement boardDelete = connection.prepareStatement(deleteBoard);
            boardDelete.setString(1, nameOfTheBoard);
            boardDelete.executeUpdate();
            connection.commit();
            log.debug(bundle.getString("commitMessage"));
        } catch (SQLException gettingBoardID) {
            log.info(bundle.getString("deleteBoardFromDBException"));
            try {
                connection.rollback();
                log.debug(bundle.getString("rollbackException"));
            } catch (SQLException exception) {
                throw new StatementExecutionException(
                        bundle.getString("statementExecutionException"), exception);
            }
        }
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public void close() throws CloseDatabaseException {
        try {
            connection.commit();
            log.debug(bundle.getString("commitMessage"));
            connection.close();
        } catch (SQLException sql) {
            if (!sql.getSQLState().equals("08006")) {
                throw new CloseDatabaseException(
                        bundle.getString("closeDataBaseException"), sql);
            }
        }
        log.info(bundle.getString("jdbcDaoResourcesClosed"));
    }
}
