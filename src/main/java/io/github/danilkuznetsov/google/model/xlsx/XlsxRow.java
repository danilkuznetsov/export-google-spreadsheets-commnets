package io.github.danilkuznetsov.google.model.xlsx;

import lombok.*;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */
@Data
public class XlsxRow {
    private final int rowNumber;
    private final String sheetName;
    private final List<XlsxCell> cells;
}
