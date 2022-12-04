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
public class Row {
    private String rowId;
    private Map<String, String> columnValuesMap;
    private Date createdAt;
    private Date updatedAt;
}
