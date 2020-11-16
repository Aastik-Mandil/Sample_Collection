package com.game.samplecollection;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataTask extends AsyncTask<String, Void, String> {

    private MainActivityDataTaskNotification mainActivityInterface;

    public DataTask(MainActivityDataTaskNotification mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
    }

    public String doInBackground(String... urlParams) {
        String connectionInstruction = "";
        String stringBufferData = "";
        URL passedInURL;
        HttpURLConnection connection = null;
        try {
            if (urlParams.length > 0) {
                passedInURL = new URL(urlParams[0]);
                connection = (HttpURLConnection) passedInURL.openConnection();
                InputStream fd = connection.getInputStream();
                InputStreamReader fdRead = new InputStreamReader(fd);
                int buffer = fdRead.read();
                while (buffer != -1) {
                    char bufferedCharacter = (char) buffer;
                    stringBufferData += bufferedCharacter;
                    buffer = fdRead.read();
                }
            }

            connectionInstruction = "Based upon your last network request, your connectivity is good.";
        } catch (Exception e) {
            connectionInstruction = e.toString();
            e.printStackTrace();
        }
        return connectionInstruction;
    }

    public void onPreExecute() {
        this.mainActivityInterface.notifyMainActivity("Connection Status: Checking",
                "Waiting for connection to finish");
    }

    public void onPostExecute(String response) {
        String status = "Online";
        if (response.contains("UnknownHostException")) {
            status = "Offline";
        }
        this.mainActivityInterface.notifyMainActivity("Connection Status: " + status,
                response);
    }
}
