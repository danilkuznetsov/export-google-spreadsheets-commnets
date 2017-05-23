package io.github.danilkuznetsov.google.model.xlsx;

import io.github.danilkuznetsov.google.model.xlsx.XlsxCell;
import io.github.danilkuznetsov.google.model.xlsx.XlsxFile;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Danil Kuznetsov
 */
public class XlsxFileIntegrationTest {

    @Test
    public void shouldFetchAllCommentsFromXlsxDocs() throws IOException, URISyntaxException {

        FileInputStream fis = new FileInputStream(new File("src/test/resources/test_export.xlsx"));
        XlsxFile xlsxService = new XlsxFile(fis);
        List<XlsxCell> actualComments = xlsxService.fetchXlsxCells();

        assertThat(actualComments, hasSize(5));
        assertThat(actualComments, everyItem(hasProperty("commentContent", notNullValue())));
        assertThat(actualComments, hasItem(hasProperty("col", equalTo(1))));
        assertThat(actualComments, hasItem(hasProperty("col", equalTo(2))));
        assertThat(actualComments, everyItem(hasProperty("row", notNullValue())));
        assertThat(actualComments,hasItem(hasProperty("sheetName",equalTo("TestSheet"))));
        assertThat(actualComments,hasItem(hasProperty("sheetName",equalTo("ClassData"))));
    }
}
