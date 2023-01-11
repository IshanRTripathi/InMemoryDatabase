package driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import service.Database;
import service.Row;
import service.Table;

public class SQLParser {
    private Database database;

    public SQLParser(Database database) {
        this.database = database;
    }

    public SQLParser() {
    }

    void parseSQL(String query) {
        String[] queries = query.split("\\s++");
        List<Row> response = null;
        switch (queries[0]) {
            case "SELECT":
            case "select":
                response = performSelectOperation(queries);
                break;
            case "UPDATE":
            case "update":
                response = performAlterOperation(queries);
                break;
            case "DELETE":
            case "delete":
                response = performDeleteOperation(queries);
                break;
            default:
                System.out.println("Only supporting select, alter, delete");
        }
        System.out.println(response);
    }

    private List<Row> performDeleteOperation(String[] queries) {

        System.out.println("Performing select * ");
        if (queries[1].equalsIgnoreCase("from")) {
            System.out.println(" from table => " + queries[2]);
            Table currentTable = database.getTableHashMap().get(queries[2]);
            if (currentTable == null) {
                System.out.println("Table doesn't exists");
            } else {
                if (queries[3].equalsIgnoreCase("where")) {
                    String[] subQueries = queries[4].split("\\.");
                    String fieldName = subQueries[subQueries.length - 1];
                    String operator = queries[5];
                    String value = queries[6];
                    List<String> rowIds = new ArrayList<>();
                    int size = 0;
                    switch (operator) {
                        case "=":
                            rowIds = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).equals(value))
                                .map(row -> row.getRowId()).collect(Collectors.toList());
                            size = rowIds.size();
                            rowIds.forEach(rowId -> currentTable.deleteEntry(rowId));
                            break;
                        case "<":
                            rowIds = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) < 0)
                                .map(row -> row.getRowId()).collect(Collectors.toList());
                            size = rowIds.size();
                            rowIds.forEach(rowId -> currentTable.deleteEntry(rowId));
                            break;
                        case ">":
                            rowIds = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) > 0)
                                .map(row -> row.getRowId()).collect(Collectors.toList());
                            size = rowIds.size();
                            rowIds.forEach(rowId -> currentTable.deleteEntry(rowId));
                            break;
                        case "<=":
                            rowIds = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) <= 0)
                                .map(row -> row.getRowId()).collect(Collectors.toList());
                            size = rowIds.size();
                            rowIds.forEach(rowId -> currentTable.deleteEntry(rowId));
                            break;
                        case ">=":
                            rowIds = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) >= 0)
                                .map(row -> row.getRowId()).collect(Collectors.toList());
                            size = rowIds.size();
                            rowIds.forEach(rowId -> currentTable.deleteEntry(rowId));
                            break;
                        default:
                            System.out.println("Invalid operator, use =, <, <=, >, >=");
                    }
                    System.out.println("Deleted " + size + " rows !");
                } else {
                    System.out.println("invalid syntax");
                    return null;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<Row> performAlterOperation(String[] queries) {
        System.out.println("Performing update on table " + queries[1]);
        String[] subQueriesParent = queries[1].split("\\.");
        Table currentTable = database.getTableHashMap().get(subQueriesParent[0]);
        if (currentTable == null) {
            System.out.println("Table doesn't exists");
        } else {
            if (queries[2].equalsIgnoreCase("set")) {
                String[] subQueries = queries[3].split("\\.");
                String fieldName = subQueries[subQueries.length - 1];
                String operator = queries[4];
                String value = queries[5];
                if (queries[6].equalsIgnoreCase("where")) {
                    String[] whereFieldArray = queries[7].split("\\.");
                    String whereField = whereFieldArray[whereFieldArray.length - 1];
                    String whereOperator = queries[8];
                    String whereValue = queries[9];
                    if ("=".equals(operator)) {
                        if ("=".equals(whereOperator)) {
                            var x1 = currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(whereField).equals(whereValue))
                                .collect(Collectors.toList());
                            x1.forEach(row -> currentTable.getRows().get(row.getRowId()).getColumnValuesMap().put(fieldName, value));
                        } else if ("<=".equals(whereOperator)) {
                            currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(whereField).compareTo(whereValue) >= 0)
                                .forEach(row -> row.getColumnValuesMap().put(fieldName, value));
                        } else if ("<".equals(whereOperator)) {
                            currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(whereField).compareTo(whereValue) > 0)
                                .forEach(row -> row.getColumnValuesMap().put(fieldName, value));
                        } else if (">=".equals(whereOperator)) {
                            var rows =
                                currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(whereField).compareTo(whereValue) <= 0)
                                    .map(row -> currentTable.getRows()).collect(Collectors.toList());
                        } else if (">".equals(whereOperator)) {
                            currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(whereField).compareTo(whereValue) < 0)
                                .forEach(row -> row.getColumnValuesMap().put(fieldName, value));
                        } else {
                            System.out.println("Unsupported filter operator");
                            return new ArrayList<>();
                        }
                        System.out.println("Updated column => " + fieldName + " to " + value);
                    } else {
                        System.out.println("Invalid operator, use = only for update");
                    }
                } else {
                    System.out.println("where clause expected");
                }
            } else {
                System.out.println("invalid syntax");
                return null;
            }
        }
        return new ArrayList<>();
    }

    private List<Row> performSelectOperation(String[] queries) {
        if (queries[1].equals("*")) {
            System.out.println("Performing select * ");
            if (queries[2].equalsIgnoreCase("from")) {
                System.out.println(" from table => " + queries[3]);
                Table currentTable = database.getTableHashMap().get(queries[3]);
                if (currentTable == null) {
                    System.out.println("Table doesn't exists");
                } else {
                    if (queries.length > 4) {
                        if (queries[4].equalsIgnoreCase("where")) {
                            String[] subQueries = queries[5].split("\\.");
                            String fieldName = subQueries[subQueries.length - 1];
                            String operator = queries[6];
                            String value = queries[7];
                            switch (operator) {
                                case "=":
                                    return currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).equals(value))
                                        .collect(Collectors.toList());
                                case "<":
                                    return currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) < 0)
                                        .collect(Collectors.toList());
                                case ">":
                                    return currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) > 0)
                                        .collect(Collectors.toList());
                                case "<=":
                                    return currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) <= 0)
                                        .collect(Collectors.toList());
                                case ">=":
                                    return currentTable.getRows().values().stream().filter(row -> row.getColumnValuesMap().get(fieldName).compareTo(value) >= 0)
                                        .collect(Collectors.toList());
                                default:
                                    System.out.println("Invalid operator, use =, <, <=, >, >=");
                            }
                        } else {
                            System.out.println("invalid syntax");
                            return null;
                        }
                    } else {
                        return new ArrayList<>(currentTable.getRows().values());
                    }
                }
            }
        } else {
            System.out.println("Only select * supported");
        }
        return new ArrayList<>();
    }
}
