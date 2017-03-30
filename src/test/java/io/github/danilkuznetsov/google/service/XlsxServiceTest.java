package io.github.danilkuznetsov.google.service;

import io.github.danilkuznetsov.google.model.FullComments;
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
        List<FullComments> actualComments = xlsxService.fetchFullComments(fis);

        assertThat(actualComments, hasSize(2));
        assertThat(actualComments, everyItem(hasProperty("comments", notNullValue())));
        assertThat(actualComments, everyItem(hasProperty("col", is(1))));
        assertThat(actualComments, everyItem(hasProperty("row", notNullValue())));
    }
}