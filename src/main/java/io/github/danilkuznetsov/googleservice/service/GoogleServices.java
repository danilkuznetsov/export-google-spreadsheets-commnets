package io.github.danilkuznetsov.googleservice.service;

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

public class GoogleServices {

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    private String applicationName = "Example Application";

    private File dataStoreDir = new File(System.getProperty("user.home"),
            ".credentials/google.service.data");

    private List<String> scopes = new ArrayList<>();

    private InputStream clientSecrets;

    public GoogleServices() throws GeneralSecurityException, IOException {}

    public static GoogleServices newGoogleService() throws GeneralSecurityException, IOException {
        return new GoogleServices();
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

    public GoogleServices clientSecret(InputStream clientSecrets) {
        this.clientSecrets = clientSecrets;
        return this;
    }

    public GoogleServices applicationName(String appName) {
        applicationName = appName;
        return this;
    }

    public GoogleServices scope(String scope) {
        scopes.add(scope);
        return this;
    }

    public GoogleServices dataStoreDir(File dir) {
        this.dataStoreDir = dir;
        return this;
    }
}
