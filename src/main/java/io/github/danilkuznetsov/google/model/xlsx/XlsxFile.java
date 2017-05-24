package io.github.danilkuznetsov.google.model.xlsx;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Danil Kuznetsov
 */
@RequiredArgsConstructor
public class XlsxFile {
    private final InputStream dataFile;

    public List<XlsxSheet> sheets() throws IOException {
        List<XlsxSheet> resultSheets = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(dataFile);
        for (Sheet sheet : workbook) {
            List<XlsxRow> rows = extractRows(sheet);
            resultSheets.add(new XlsxSheet(sheet.getSheetName(), rows));
        }
        return resultSheets;
    }

    private List<XlsxRow> extractRows(Sheet sheet) {
        List<XlsxRow> rows = new ArrayList<>();
        Iterator<Row> rowInterator = sheet.rowIterator();
        while (rowInterator.hasNext()) {
            Row currentRow = rowInterator.next();
            List<XlsxCell> cells = extractCell(currentRow,sheet.getSheetName());
            rows.add(new XlsxRow(currentRow.getRowNum(), sheet.getSheetName(), cells));
        }
        return rows;
    }

    private List<XlsxCell> extractCell(Row row, String sheetName) {
        Iterator<Cell> cells = row.cellIterator();

        List<XlsxCell> resultCells = new ArrayList<>();

        while (cells.hasNext()) {
            Cell currentCell = cells.next();
            Comment comment = currentCell.getCellComment();
            if (comment != null) {
                RichTextString commentText = currentCell.getCellComment().getString();
                commentText.clearFormatting();
                        resultCells.add(new XlsxCell(sheetName,
                                currentCell.getRowIndex(),
                                currentCell.getColumnIndex(),
                                commentText.getString(),
                                getCellValue(currentCell)
                        ));
            } else {
                        resultCells.add(new XlsxCell(sheetName,
                                currentCell.getRowIndex(),
                                currentCell.getColumnIndex(),
                                "",
                                getCellValue(currentCell)
                        ));
            }

        }
        return resultCells;
    }

    private String getCellValue(Cell cell) {

        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case NUMERIC:
                    return String.valueOf(cell.getNumericCellValue());
                case STRING:
                    return cell.getStringCellValue();
                case BLANK:
                    return "blank";
                case _NONE:
                    return "none";
                case FORMULA:
                    return "formula";
                case ERROR:

            }
        }

        return "";
    }

}
