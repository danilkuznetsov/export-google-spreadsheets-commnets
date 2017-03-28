import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import io.github.danilkuznetsov.google.AppStarter;
import io.github.danilkuznetsov.google.service.GoogleServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author Danil Kuznetsov
 */
@RunWith(JUnit4.class)
public class GoogleServicesUnitTest {

    @Test
    public void shouldCreateGoogleServiceInstance() throws GeneralSecurityException, IOException {
        InputStream clientSecret = AppStarter.class.getResourceAsStream("/client_secret.json");

        Drive drive = null;
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

        GoogleServices googleServices = GoogleServices.newGoogleService()
                .applicationName("Export Google Spreadsheets Comments")
                .scope(DriveScopes.DRIVE)
                .build();

        Drive drive = googleServices.googleDrive();
    }
}
