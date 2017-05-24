package io.github.danilkuznetsov.google;

import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.model.export.ExportCell;
import io.github.danilkuznetsov.google.model.export.ExportRow;
import io.github.danilkuznetsov.google.model.export.ExportSheet;
import io.github.danilkuznetsov.google.model.google.GoogleSpreadSheetsFile;
import io.github.danilkuznetsov.google.service.DataExportService;
import io.github.danilkuznetsov.google.service.GoogleServicesFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * @author Danil Kuznetsov
 */
public class AppStarter {

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        String fileId = "1yVo6LB_D4Fdq2zxjhUyvjjHm7XU_T9SBacTrB3m0Isc";

        GoogleServicesFactory googleServicesFactory = GoogleServicesFactory.newGoogleServicesFactory()
                .applicationName("Google spreadsheets comments expoter")
                .clientSecret(AppStarter.class.getResourceAsStream("/client_secret.json"))
                .scope(DriveScopes.DRIVE)
                .build();

        GoogleSpreadSheetsFile googleFile = new GoogleSpreadSheetsFile(fileId, googleServicesFactory.googleDrive());

        DataExportService service = new DataExportService();

        List<ExportSheet> sheets = service.exportDataForGoogleSpreadsheet(googleFile);

        OutputStream f = new FileOutputStream("Result.txt", true);
        OutputStreamWriter writer = new OutputStreamWriter(f);
        BufferedWriter out = new BufferedWriter(writer);

        for (ExportSheet sheet : sheets) {
            out.write(sheet.getSheetName()  + "\n");
            for (ExportRow row : sheet.getRows()) {
                out.write("  " +row.getRowNumber() +  "\n");
                for(ExportCell cell : row.getCells()){
                    out.write("    " + cell + "\n");
                }
            }
        }

        out.flush();
    }
}
