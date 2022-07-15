package it.unisa.casper.analysis.code_smell_detection.blob;

import it.unisa.casper.analysis.code_smell_detection.strategy.ClassSmellDetectionStrategy;
import it.unisa.casper.storage.beans.ClassBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

public class DeepLearningBlobStrategy implements ClassSmellDetectionStrategy {

    private Vector<String> metrics = new Vector<String>();
    private String risultato;

    public DeepLearningBlobStrategy(Vector<String> metrics){
        this.metrics = metrics;
    }

    @Override
    public boolean isSmelly(ClassBean aClass) {
        try {
            URL url = new URL("http://localhost:5000?metrics="+ metrics.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                if(content.toString().equals("[0. 1. 0. 0. 0.]")) {
                    risultato = content.toString();
                    return true;
                }
                else if(content.toString().contains("Error"))
                    risultato = "Error";
            }
        } catch (IOException e) {
            throw new RuntimeException("Web Service non raggiungibile");
        }
        return false;
    }



    @Override
    public HashMap<String, Double> getThresold(ClassBean aClass) {
        return null;
    }

    public String getRisultato(){
        return risultato;
    }
}
