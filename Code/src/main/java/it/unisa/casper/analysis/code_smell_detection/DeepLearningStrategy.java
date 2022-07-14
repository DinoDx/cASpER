package it.unisa.casper.analysis.code_smell_detection;

import it.unisa.casper.analysis.code_smell_detection.strategy.ClassSmellDetectionStrategy;
import it.unisa.casper.storage.beans.ClassBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

public class DeepLearningStrategy implements ClassSmellDetectionStrategy {

    private Vector<String> metrics = new Vector<String>();
    private String result = "";

    public DeepLearningStrategy(Vector<String> metrics){
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
                switch (content.toString()) {
                    case "[0. 0. 0. 0. 0.]":
                        result = "No Code Smell";
                        return true;
                    case "[1. 0. 0. 0. 0.]":
                        result = "Complex Class";
                        return true;
                    case "[0. 1. 0. 0. 0.]":
                        result = "Blob Class";
                        return true;
                    case "[0. 0. 1. 0. 0.]":
                        result = "Lazy Class";
                        return true;
                    case "[0. 0. 0. 1. 0.]":
                        result = "Refused Bequest";
                        return true;
                    case "[0. 0. 0. 0. 1.]":
                        result = "Spaghetti Code";
                        return true;
                    default:
                        result = "Wrong parameter";
                        return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Web service non disponibile");
        }
        return false;
    }
    @Override
    public HashMap<String, Double> getThresold(ClassBean pClass) {
        return null;
    }

    public String getResult() {
        return result;
    }
}

