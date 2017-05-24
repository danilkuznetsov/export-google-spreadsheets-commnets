package io.github.danilkuznetsov.google.model.xlsx;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Danil Kuznetsov
 */
public class XlsxFileIntegrationTest {

    @Test
    public void shouldFetchAllSheetsFromXlsxDocs() throws IOException, URISyntaxException {

        FileInputStream fis = new FileInputStream(new File("src/test/resources/test_export.xlsx"));
        XlsxFile xlsxService = new XlsxFile(fis);
        List<XlsxSheet> sheets = xlsxService.sheets();

        List<XlsxCell> actualCells = new ArrayList<>();



        for(XlsxSheet sheet: sheets){
            for(XlsxRow row : sheet.getRows()){
                actualCells.addAll(row.getCells());
            }
        }

        assertThat(sheets, hasSize(2));
        assertThat(actualCells, everyItem(hasProperty("commentContent", notNullValue())));
        assertThat(actualCells, hasItem(hasProperty("col", equalTo(1))));
        assertThat(actualCells, hasItem(hasProperty("col", equalTo(2))));
        assertThat(actualCells, everyItem(hasProperty("row", notNullValue())));
        assertThat(actualCells,hasItem(hasProperty("sheetName",equalTo("TestSheet"))));
        assertThat(actualCells,hasItem(hasProperty("sheetName",equalTo("ClassData"))));
    }
}
