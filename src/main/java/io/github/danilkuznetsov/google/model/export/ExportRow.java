package io.github.danilkuznetsov.google.model.export;

import lombok.*;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */
@Data
public class ExportRow {
    private final int rowNumber;
    private final String sheetName;
    private final String sheetGoogleId;
    private final List<ExportCell> cells;
}
