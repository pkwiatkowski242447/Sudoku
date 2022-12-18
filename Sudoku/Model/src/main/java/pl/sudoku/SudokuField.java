package pl.sudoku;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.sudoku.exceptions.SudokuFieldInvalidIndexException;
import pl.sudoku.exceptions.SudokuFieldNullObjectException;


public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
        } else {
            throw new SudokuFieldInvalidIndexException("Wartość poza zakresem");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        stringBuilder.append(this.getFieldValue());
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o != null) {
            if (o.getFieldValue() < this.getFieldValue()) {
                return 1;
            } else if (o.getFieldValue() == this.getFieldValue()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            throw new SudokuFieldNullObjectException("Podany argument jest referencją do null'a.");
        }
    }


    @Override
    protected SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
