package io.github.danilkuznetsov.google.service;

import io.github.danilkuznetsov.google.model.FullComments;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Danil Kuznetsov
 */
public class XlsxService {

    public List<FullComments> fetchFullComments(FileInputStream file) throws IOException {

        List<FullComments> result = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
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
                    result.add(new FullComments(cell.getRowIndex(), cell.getColumnIndex(), commentText.getString()));
                }

            }
        }
        return result;
    }
}
