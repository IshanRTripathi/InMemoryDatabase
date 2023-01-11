package driver;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import service.Database;
import service.Row;
import service.Table;

public class MainDriver {
    private static Database database;
    public static void main(String[] args) {
        database = new Database("db", Map.of("employee", getDefaultTableData()), LocalDateTime.now());
        SQLParser parser = new SQLParser(database);
        parser.parseSQL("select * from employee where employee.empid = 1");
        parser.parseSQL("select * from employee where employee.salary >= 100");
        parser.parseSQL("update employee set employee.salary = 1000 where employee.salary <= 100");
        parser.parseSQL("delete from employee where employee.salary = 100");
    }

    private static Table getDefaultTableData() {
        return new Table()
            .createdAt(Date.from(Instant.now()))
            .tableId("employee")
            .columns(Set.of("empid", "empName", "designation", "salary"))
            .rows(new HashMap<>(Map.of("1", new Row().rowId("1")
                    .columnValuesMap(new HashMap<>(Map.of("empid", "1", "empName", "ishan","designation", "SSE", "salary", "100"))))));
    }

    public static Database getDatabase() {
        return database;
    }
}
