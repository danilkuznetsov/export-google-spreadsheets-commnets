package io.github.danilkuznetsov.google.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.AppStarter;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author Danil Kuznetsov
 */
public class GoogleServicesIntegrationTest {

    @Test
    public void shouldCreateGoogleServiceInstance() throws GeneralSecurityException, IOException {
        InputStream clientSecret = AppStarter.class.getResourceAsStream("/client_secret.json");

        Drive drive;
        GoogleServices googleServices = GoogleServices.newGoogleService()
                .clientSecret(clientSecret)
                .applicationName("Export Google Spreadsheets Comments")
                .scope(DriveScopes.DRIVE)
                .build();

        drive = googleServices.googleDrive();
        assertThat(drive, is(notNullValue(Drive.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenClientSecretNotFound() throws GeneralSecurityException, IOException {
        GoogleServices.newGoogleService()
                .applicationName("Export Google Spreadsheets Comments")
                .scope(DriveScopes.DRIVE)
                .build();
    }
}
