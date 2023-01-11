package service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Database {
    private String name;
    private Map<String, Table> tableHashMap;
    private LocalDateTime createdAt;

    public String getName() {
        return name;
    }

    public Map<String, Table> getTableHashMap() {
        return tableHashMap;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Database(String name, Map<String, Table> tableHashMap, LocalDateTime createdAt) {
        this.name = name;
        this.tableHashMap = tableHashMap;
        this.createdAt = createdAt;
    }

    public void createTable(String tableName) {
        if(tableHashMap.containsKey(tableName)) {
            System.out.println("Table already exists");
        } else {
            tableHashMap.put(tableName, new Table().tableId(tableName).createdAt(new Date()).updatedAt(new Date()));
            System.out.println("Table create success");
        }
    }

    public void deleteTable(String tableName) {
        if(!tableHashMap.containsKey(tableName)) {
            System.out.println("Table doesn't exists");
        } else {
            tableHashMap.remove(tableName);
            System.out.println("Table delete success");
        }
    }
}
