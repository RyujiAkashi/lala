package com.anastacio.property;

import javax.swing.table.DefaultTableModel;

public class PropertyModel extends DefaultTableModel {
    public PropertyModel(String[] headers) {
        super(null, headers);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return (column == 1);
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount()) {
            super.setValueAt(aValue, row, column);
        }
    }

    public void clear() {
        int rowCount = getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            if (i < getRowCount()) {
                removeRow(i);
            }
        }
    }
}

