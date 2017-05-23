package io.github.danilkuznetsov.google.model;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.AppStarter;
import io.github.danilkuznetsov.google.service.GoogleServicesFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Danil Kuznetsov
 */
public class GoogleSpreadSheetsIntegrationFileTest {

    Drive drive;
    // Test Google spreadsheets file
    // link https://docs.google.com/spreadsheets/d/1l6CgfeUWBFVM88CPloI20OPdUmOb4JdlKdasTmkCDzU/edit#gid=0

    String FILE_ID = "1l6CgfeUWBFVM88CPloI20OPdUmOb4JdlKdasTmkCDzU";

    @Before
    public void setup() throws IOException, GeneralSecurityException {
        InputStream clientSecret = AppStarter.class.getResourceAsStream("/client_secret.json");

        GoogleServicesFactory googleServicesFactory = GoogleServicesFactory.newGoogleServicesFactory()
                .clientSecret(clientSecret)
                .applicationName("Export Google Spreadsheets Comments")
                .scope(DriveScopes.DRIVE)
                .build();

        drive = googleServicesFactory.googleDrive();
    }

    @Test
    public void shouldAddReplies() throws IOException {

        String expectedCommentId1 = "AAAABIMj-kE";
        String expectedCommentId2 = "AAAABIMj-j4";
        GoogleSpreadSheetsFile file = new GoogleSpreadSheetsFile(FILE_ID, drive);

        Map<String, String> actualResult = file.exportIdCommentsToReplies();

        file.removeRepliesByCommentId(actualResult);

        System.out.println(actualResult.toString());

        assertThat(actualResult.size(), equalTo(2));
        assertTrue(actualResult.containsKey(expectedCommentId1));
        assertTrue(actualResult.containsKey(expectedCommentId2));

    }

    @Test
    public void shouldDeleteReplies() throws IOException {
        GoogleSpreadSheetsFile file = new GoogleSpreadSheetsFile(FILE_ID, drive);
        Map<String, String> replies = file.exportIdCommentsToReplies();

        List<String> removedReplies = file.removeRepliesByCommentId(replies);

        assertThat(removedReplies.size(), equalTo(removedReplies.size()));
        assertThat(removedReplies.toArray(), equalTo(replies.values().toArray()));
    }
}
