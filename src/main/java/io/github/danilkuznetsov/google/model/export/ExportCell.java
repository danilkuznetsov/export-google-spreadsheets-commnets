package io.github.danilkuznetsov.google.model.export;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Danil Kuznetsov
 */
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class ExportCell {

    private int col;
    private int row;

    private String sheetName;
    private String cellContent;
    private ExportComment comment;
}
