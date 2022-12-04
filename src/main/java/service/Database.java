package service;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class Database {
    private String name;
    private Map<String, Table> tableHashMap;
    private Date createdAt;

    public void createTable(String tableName) {
        if(tableHashMap.containsKey(tableName)) {
            System.out.println("Table already exists");
        } else {
            tableHashMap.put(tableName, Table.builder().tableId(tableName).createdAt(new Date()).updatedAt(new Date()).build());
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
