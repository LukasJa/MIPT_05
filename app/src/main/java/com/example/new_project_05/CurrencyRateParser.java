package com.example.new_project_05;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CurrencyRateParser {

    public static ArrayList<CurrencyRate> parse(InputStream inputStream) {
        ArrayList<CurrencyRate> currencyRates = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                String currencyCode = item.getElementsByTagName("targetCurrency").item(0).getTextContent();
                double rate = Double.parseDouble(item.getElementsByTagName("exchangeRate").item(0).getTextContent());

                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setCurrencyCode(currencyCode);
                currencyRate.setRate(rate);

                currencyRates.add(currencyRate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyRates;
    }
}
