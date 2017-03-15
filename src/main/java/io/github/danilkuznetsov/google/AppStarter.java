package io.github.danilkuznetsov.google;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.service.GoogleServices;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

/**
 * @author Danil Kuznetsov
 */
public class AppStarter {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        InputStream clientSecret = AppStarter.class.getResourceAsStream("/client_secret.json");

        GoogleServices googleServices = GoogleServices.newGoogleService()
                .clientSecret(clientSecret)
                .applicationName("Export Google Spreadsheets Comments")
                .scope(DriveScopes.DRIVE);

        Drive drive = googleServices.googleDrive();

    }
}
