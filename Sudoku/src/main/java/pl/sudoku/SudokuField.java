package pl.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuField {

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
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        stringBuilder.append(this.getFieldValue());
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(final Object someObject) {
        if (someObject == this) {
            return true;
        }
        if (someObject == null) {
            return false;
        }
        if (someObject.getClass() != this.getClass()) {
            return false;
        }

        EqualsBuilder equalsBuilder = new EqualsBuilder();
        return equalsBuilder.append(this.getFieldValue(),
                ((SudokuField)someObject).getFieldValue()).isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(41,97);
        return hashCodeBuilder.append(this.getFieldValue()).hashCode();
    }

}
