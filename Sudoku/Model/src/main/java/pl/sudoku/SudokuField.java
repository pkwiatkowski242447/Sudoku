package pl.sudoku;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

<<<<<<< HEAD
public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
=======
public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
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
<<<<<<< HEAD
    public int compareTo(SudokuField object) {
        if (object != null) {
            if (this.getFieldValue() == object.getFieldValue()) {
                return 0;
            } else if (this.getFieldValue() > object.getFieldValue()) {
                return 1;
=======
    public int compareTo(SudokuField o) {
        if (o != null) {
            if (o.getFieldValue() < this.getFieldValue()) {
                return 1;
            } else if (o.getFieldValue() == this.getFieldValue()) {
                return 0;
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608
            } else {
                return -1;
            }
        } else {
<<<<<<< HEAD
            throw new NullPointerException("Podany argument jest referencją do null'a.");
        }
    }


    @Override
    public SudokuField clone() throws CloneNotSupportedException {
=======
            throw new NullPointerException("Obiekt jest nullem");
        }
    }

    @Override
    protected SudokuField clone() throws CloneNotSupportedException {
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608
        return (SudokuField) super.clone();
    }
}
