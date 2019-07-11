package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

    private HttpURLConnection connection = null;
    private InputStream is = null;
    private OutputStream os = null;
    private BufferedReader br = null;
    private String result = null;

    public String doGet(String httpUrl){
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            getResponse();
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public String doPost(String httpUrl, String param){
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);

            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            os = connection.getOutputStream();
            os.write(param.getBytes());
            getResponse();
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            closeConnection();
        }
        return result;
    }

    public void getResponse(){
        try {
            System.out.println("GetResponse:");
            if (connection.getResponseCode() == 200) {
                System.out.println("GetResponse: is OK?");
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                    System.out.println("GetResponse:" + sb.toString());
                }
                result = sb.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if(br != null){
            try{
                br.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if(is != null){
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        connection.disconnect();
    }
}
