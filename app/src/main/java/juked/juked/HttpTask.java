package juked.juked;


import android.os.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import android.util.Log;


import java.io.IOException;

/**
 * Created by jgmil on 4/1/2018.
 */

public class HttpTask extends AsyncTask<String, Void, String>{

   /* @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }*/


    @Override
    protected String doInBackground(String...params){

        String result = "";
        BufferedReader rd;
        StringBuilder sb = new StringBuilder();

        try{

            URL url = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer "+ params[1]);
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            if(conn.getResponseCode() == 200){
                Log.d("Response", "I connected with no problem");
            }else
                Log.d("Response", "Could not connect");



            while ((result = rd.readLine()) != null) {
                sb.append(result);
            }
            rd.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        Log.d("Response", ">>> " + sb.toString());
        return sb.toString();

    }//end doInBackground

    /*@Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }*/

}