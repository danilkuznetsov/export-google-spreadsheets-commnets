package io.github.danilkuznetsov.google;

import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.model.DirtyComments;
import io.github.danilkuznetsov.google.model.GoogleSpreadSheetsFile;
import io.github.danilkuznetsov.google.service.GoogleServices;
import io.github.danilkuznetsov.google.service.XlsxService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * @author Danil Kuznetsov
 */
public class AppStarter {

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        String fileId = "1l6CgfeUWBFVM88CPloI20OPdUmOb4JdlKdasTmkCDzU";

        GoogleServices googleServices = GoogleServices.newGoogleService()
                .applicationName("Google spreadsheets comments expoter")
                .clientSecret(AppStarter.class.getResourceAsStream("/client_secret.json"))
                .scope(DriveScopes.DRIVE)
                .build();

        GoogleSpreadSheetsFile gsrsFile = new GoogleSpreadSheetsFile(fileId, googleServices.googleDrive());

        Map<String, String> replies = gsrsFile.exportIdCommentsToReplies();
        byte[] file = gsrsFile.exportToXlsx();
        ByteArrayInputStream is = new ByteArrayInputStream(file);

        XlsxService service = new XlsxService();
        List<DirtyComments> comments = service.fetchFullComments(is);

        gsrsFile.removeRepliesByCommentId(replies);

        for (DirtyComments comment : comments) {
            System.out.println(comment);
        }

    }
}
