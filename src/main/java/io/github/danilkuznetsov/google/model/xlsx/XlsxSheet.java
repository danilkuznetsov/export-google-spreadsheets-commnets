package io.github.danilkuznetsov.google.model.xlsx;

import lombok.*;

import java.util.List;

/**
 * @author Danil Kuznetsov
 */
@Data
public class XlsxSheet {
    private final String sheetName;
    private final List<XlsxRow> rows;
}
