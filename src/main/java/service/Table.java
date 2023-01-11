package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import exception.ColumnNotFoundException;

public class Table {
    private String tableId;
    private Set<String> columns;
    private HashMap<String, Row> rows;
    private Date createdAt;
    private Date updatedAt;

    public String getTableId() {
        return tableId;
    }

    public Set<String> getColumns() {
        return columns;
    }

    public HashMap<String, Row> getRows() {
        return rows;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Table tableId(String tableId) {
        this.tableId = tableId;
        return this;
    }

    public Table columns(Set<String> columns) {
        this.columns = columns;
        return this;
    }

    public Table rows(HashMap<String, Row> rows) {
        this.rows = rows;
        return this;
    }

    public Table createdAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Table updatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
    public Table() {}
    public Table(Table table) {
        this.tableId = table.tableId;
        this.columns = table.columns;
        this.rows = table.rows;
        this.createdAt = table.createdAt;
        this.updatedAt = table.updatedAt;
    }

    public void insertIntoTableValues(String rowId, HashMap<String, String> values) {
        if(rows.containsKey(rowId)) {
            System.out.println("RowId already exists");
        } else {
            columns.addAll(values.keySet());
            rows.put(rowId, new Row().rowId(rowId).columnValuesMap(values).createdAt(new Date()).updatedAt(new Date()));
            System.out.println("Insertion success!");
        }
    }

    public void updateEntry(String rowId, Map<String, String> values) {
        if(!rows.containsKey(rowId)) {
            System.out.println("RowId doesn't exists");
        } else {
            Row data = rows.get(rowId);
            for(var value: values.entrySet()) {
                if(!columns.contains(value.getKey())){
                    System.out.println("Column not present");
                    throw new ColumnNotFoundException("Column not present");
                }
                data.getColumnValuesMap().put(value.getKey(), value.getValue());
            }
            data.updatedAt(new Date());
            System.out.println("Update success!");
        }
    }

    public void deleteEntry(String rowId) {
        if(!rows.containsKey(rowId)) {
            System.out.println("RowId doesn't exists");
        } else {
            rows.remove(rowId);
            System.out.println("Delete success!");
        }
    }

    public void getEntry(String rowId) {
        if(!rows.containsKey(rowId)) {
            System.out.println("RowId doesn't exists");
        } else {
            Row data = rows.get(rowId);
            System.out.println(data);
        }
    }
}
