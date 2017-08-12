package com.philvr.jacksonxmltest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Philip Van Raalte
 * @date 2017-08-12
 */

public class GetDataFromUrl extends AsyncTask<String, Void, String>{

    public interface TaskListener {
        void onFinished(String result);
    }

    private final TaskListener taskListener;
    private String urlPath;
    private String requestResult = "";

    public GetDataFromUrl(TaskListener taskListener){
        this.taskListener = taskListener;
    }

    @Override
    protected String doInBackground(String... params) {

        if(params.length < 1)
            return null;
        urlPath = params[0];

        try {
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);//.append("\n");
//                }
//                bufferedReader.close();
//                requestResult = stringBuilder.toString();
//                return requestResult;

                InputStream is = new URL(urlPath).openStream();
                try {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    return jsonText;
                } finally {
                    is.close();
                }

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if(this.taskListener != null){
            this.taskListener.onFinished(result);
        }
    }
}
