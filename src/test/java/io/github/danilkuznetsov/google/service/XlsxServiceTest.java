package io.github.danilkuznetsov.google.service;

import io.github.danilkuznetsov.google.model.XlsxComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
@RunWith(JUnit4.class)
public class XlsxServiceTest {

    @Test
    public void shouldFetchAllCommentsFromXlsxDocs() throws IOException, URISyntaxException {

        FileInputStream fis = new FileInputStream(new File("src/test/resources/test_export.xlsx"));
        XlsxService xlsxService = new XlsxService();
        List<XlsxComment> actualComments = xlsxService.fetchComments(fis);

        assertThat(actualComments, hasSize(5));
        assertThat(actualComments, everyItem(hasProperty("commentContent", notNullValue())));
        assertThat(actualComments, hasItem(hasProperty("col", equalTo(1))));
        assertThat(actualComments, hasItem(hasProperty("col", equalTo(2))));
        assertThat(actualComments, everyItem(hasProperty("row", notNullValue())));
        assertThat(actualComments,hasItem(hasProperty("sheetName",equalTo("TestSheet"))));
        assertThat(actualComments,hasItem(hasProperty("sheetName",equalTo("ClassData"))));
    }
}
