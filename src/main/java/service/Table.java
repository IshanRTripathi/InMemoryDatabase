package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import exception.ColumnNotFoundException;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Table {
    private String tableId;
    private Set<String> columns;
    private HashMap<String, Row> rows;
    private Date createdAt;
    private Date updatedAt;

    public void insertIntoTableValues(String rowId, Map<String, String> values) {
        if(rows.containsKey(rowId)) {
            System.out.println("RowId already exists");
        } else {
            columns.addAll(values.keySet());
            rows.put(rowId, Row.builder().rowId(rowId).columnValuesMap(values).createdAt(new Date()).updatedAt(new Date()).build());
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
            data.setUpdatedAt(new Date());
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
