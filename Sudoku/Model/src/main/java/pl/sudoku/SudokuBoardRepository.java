package pl.sudoku;


public class SudokuBoardRepository implements Repository<SudokuBoard> {
    private final SudokuBoard obj;

    public SudokuBoardRepository(SudokuBoard obj) {
        this.obj = obj;
    }

    @Override
    public SudokuBoard createInstance() {
        return obj.clone();
    }
}
