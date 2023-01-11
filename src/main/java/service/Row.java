package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Row {
    private String rowId;
    private HashMap<String, String> columnValuesMap;
    private Date createdAt;
    private Date updatedAt;

    public String getRowId() {
        return rowId;
    }

    public Map<String, String> getColumnValuesMap() {
        return columnValuesMap;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Row rowId(String rowId) {
        this.rowId = rowId;
        return this;
    }

    public Row columnValuesMap(HashMap<String, String> columnValuesMap) {
        this.columnValuesMap = columnValuesMap;
        return this;
    }

    public Row createdAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Row updatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return "rowId='" + rowId + ", columnValuesMap=" + columnValuesMap + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt;
    }
}
