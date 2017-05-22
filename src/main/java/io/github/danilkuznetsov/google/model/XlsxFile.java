package io.github.danilkuznetsov.google.model;

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
public class XlsxFile {

    private InputStream dataFile;

    public XlsxFile(InputStream file) {this.dataFile = file;}

    public List<XlsxComment> fetchComments() throws IOException {

        List<XlsxComment> xlsxComments = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(dataFile);

        Iterator<Sheet> sheets = workbook.iterator();

        while (sheets.hasNext()) {
            Sheet sheet = sheets.next();
            Iterator<Row> rows = sheet.rowIterator();

            while (rows.hasNext()) {
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    Comment comment = cell.getCellComment();
                    if (comment != null) {
                        RichTextString commentText = cell.getCellComment().getString();
                        commentText.clearFormatting();
                        xlsxComments.add(new XlsxComment(sheet.getSheetName(),
                                cell.getRowIndex(),
                                cell.getColumnIndex(),
                                commentText.getString()
                        ));
                    }

                }
            }
        }


        return xlsxComments;
    }
}
