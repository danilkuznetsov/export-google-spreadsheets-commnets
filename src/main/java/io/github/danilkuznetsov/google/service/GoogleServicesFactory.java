package io.github.danilkuznetsov.google.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil Kuznetsov
 */

public class GoogleServicesFactory {

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    private final String applicationName;

    private final File dataStoreDir;

    private final List<String> scopes;

    private final InputStream clientSecrets;

    private GoogleServicesFactory(String applicationName,
                                  File dataStoreDir,
                                  List<String> scopes,
                                  InputStream clientSecrets) throws GeneralSecurityException, IOException {
        this.applicationName = applicationName;
        this.dataStoreDir = dataStoreDir;
        this.scopes = scopes;
        this.clientSecrets = clientSecrets;
    }


    private Credential authorize(FileDataStoreFactory dataStoreFactory) throws IOException {

        GoogleClientSecrets secrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecrets));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, secrets, scopes)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();

        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, localServerReceiver).authorize("user");
        System.out.println(
                "Credentials saved to " + dataStoreDir.getAbsolutePath());
        return credential;
    }

    public Drive googleDrive() throws IOException {
        Credential credential = authorize(new FileDataStoreFactory(dataStoreDir));
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(applicationName).build();
    }

    public static GoogleServicesFactoryBuilder newGoogleServicesFactory() throws GeneralSecurityException, IOException {
        return new GoogleServicesFactoryBuilder();
    }

    public static class GoogleServicesFactoryBuilder {

        private String applicationName = "Example Application";

        private File dataStoreDir = new File(System.getProperty("user.home"), ".credentials/google.service.data");

        private List<String> scopes = new ArrayList<>();

        private InputStream clientSecrets;

        public GoogleServicesFactoryBuilder clientSecret(InputStream clientSecrets) {
            this.clientSecrets = clientSecrets;
            return this;
        }

        public GoogleServicesFactoryBuilder applicationName(String appName) {
            this.applicationName = appName;
            return this;
        }

        public GoogleServicesFactoryBuilder scope(String scope) {
            this.scopes.add(scope);
            return this;
        }


        public GoogleServicesFactoryBuilder dataStoreDir(File dir) {
            this.dataStoreDir = dir;
            return this;
        }

        public GoogleServicesFactory build() throws GeneralSecurityException, IOException {
            if (clientSecrets == null) {
                throw new IllegalArgumentException("Not found client secret");
            }
            return new GoogleServicesFactory(applicationName, dataStoreDir, scopes, clientSecrets);
        }
    }

}
