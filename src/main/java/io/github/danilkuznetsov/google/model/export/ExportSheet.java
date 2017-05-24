package io.github.danilkuznetsov.google.model.export;

import lombok.*;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */
@Data
public class ExportSheet {
    private final String sheetName;
    private final String sheetGoogleId;
    private final List<ExportRow> rows;
}
