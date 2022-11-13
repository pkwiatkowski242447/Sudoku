/*
 * Project name: Programowanie komponentowe - Sudoku
 *
 * Copyright © ${inceptionYear} ${organization.name}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sudoku;

public class NonAutomaticBoardChangeBoardChangeObserver extends BoardChangeObserver {

    public NonAutomaticBoardChangeBoardChangeObserver(final SudokuBoard board) {
        super(board);
    }

    @Override
    public void update(final SudokuBoard boardAfterChange) {
        if (super.checkBoard(boardAfterChange)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j,boardAfterChange.convertToIntList().get(i * 9 + j));
                }
            }
        }
    }
}
