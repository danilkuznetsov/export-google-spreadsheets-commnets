package io.github.danilkuznetsov.google.model.export;

import lombok.*;

/**
 * @author Danil Kuznetsov
 */
@Data
public class ExportCell {
    private final int col;
    private final int row;
    private final String sheetName;
    private final String cellContent;
    private final ExportComment comment;
}
