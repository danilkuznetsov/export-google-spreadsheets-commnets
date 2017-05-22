package io.github.danilkuznetsov.google.service;

import io.github.danilkuznetsov.google.model.XlsxComment;
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
public class XlsxService {

    public List<XlsxComment> fetchComments(InputStream file) throws IOException {

        List<XlsxComment> xlsxComments = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(file);

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
