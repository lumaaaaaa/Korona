package com.korp.korona;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    static URL url;
    static URL url2;
    static String fullJSONded;
    static String fullJSONcase;
    static int deathparse;
    static int caseparse;
    static JFrame frame = new JFrame("Korona v0.1");
    static JPanel panel = new JPanel();
    static JLabel gaming = new JLabel();
    static JLabel muerto = new JLabel();
    static JLabel muerto2 = new JLabel();
    static JLabel info = new JLabel();
    static {
        try {
            url2 = new URL("https://services1.arcgis.com/0MSEUqKaxRlEPj5g/arcgis/rest/services/ncov_cases/FeatureServer/1/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&outStatistics=%5B%7B%22statisticType%22%3A%22sum%22%2C%22onStatisticField%22%3A%22Confirmed%22%2C%22outStatisticFieldName%22%3A%22value%22%7D%5D&outSR=102100&cacheHint=true");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            url = new URL("https://services1.arcgis.com/0MSEUqKaxRlEPj5g/arcgis/rest/services/ncov_cases/FeatureServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=*&outStatistics=%5B%7B%22statisticType%22%3A%22sum%22%2C%22onStatisticField%22%3A%22Deaths%22%2C%22outStatisticFieldName%22%3A%22value%22%7D%5D&cacheHint=true");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Color dm = new Color(18, 18, 18);
        String[] options = {"Dark", "Light"};
        int x = JOptionPane.showOptionDialog(null, "What theme would you like to use?",
                "Theme",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(x == 0) {
            panel.setBackground(dm);
            gaming.setForeground(Color.white);
            muerto.setForeground(Color.white);
            muerto2.setForeground(Color.white);
            info.setForeground(Color.white);
        }
        try (var br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            var sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            fullJSONded = sb.toString();
        }
        try (var br = new BufferedReader(new InputStreamReader(url2.openStream()))) {
            String line;
            var sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            fullJSONcase = sb.toString();
        }
        JSONObject obj = new JSONObject(fullJSONded);
        JSONObject obj2 = new JSONObject(fullJSONcase);
        JSONArray arr = obj.getJSONArray("features");
        for (int i = 0; i < arr.length(); i++) {
            deathparse = arr.getJSONObject(i).getJSONObject("attributes").getInt("value");
        }
        JSONArray arr2 = obj2.getJSONArray("features");
        for (int i = 0; i < arr2.length(); i++) {
            caseparse = arr2.getJSONObject(i).getJSONObject("attributes").getInt("value");
        }
        gaming.setText("--      |       Korona, your Coronavirus tracker       |      --");
        muerto.setText("Confirmed Cases: " + caseparse);
        muerto2.setText("Deaths: " + deathparse);
        info.setText("<html>The data shown will refresh automatically every minute.<br>Huge thanks to John Hopkins University for the data.</html>");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LayoutManager layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(gaming);
        panel.add(muerto);
        panel.add(muerto2);
        panel.add(info);
        frame.add(panel);
        frame.setSize(355, 103);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.toFront();
        refresh();
    }
    public static void refresh() throws IOException, InterruptedException {
        while(true) {
            Thread.sleep(60000);
            try (var br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                var sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                fullJSONded = sb.toString();
            }
            try (var br = new BufferedReader(new InputStreamReader(url2.openStream()))) {
                String line;
                var sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                fullJSONcase = sb.toString();
            }
            JSONObject obj = new JSONObject(fullJSONded);
            JSONObject obj2 = new JSONObject(fullJSONcase);
            JSONArray arr = obj.getJSONArray("features");
            for (int i = 0; i < arr.length(); i++) {
                deathparse = arr.getJSONObject(i).getJSONObject("attributes").getInt("value");
            }
            JSONArray arr2 = obj2.getJSONArray("features");
            for (int i = 0; i < arr2.length(); i++) {
                caseparse = arr2.getJSONObject(i).getJSONObject("attributes").getInt("value");
            }
            muerto.setText("Confirmed Cases: " + caseparse);
            muerto2.setText("Deaths: " + deathparse);
        }
    }
}

