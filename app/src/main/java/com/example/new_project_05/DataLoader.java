package com.example.new_project_05;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataLoader extends AsyncTaskLoader<ArrayList<CurrencyRate>> {

    private String urlString;

    public DataLoader(Context context, String urlString) {
        super(context);
        this.urlString = urlString;
    }

    @Override
    public ArrayList<CurrencyRate> loadInBackground() {
        ArrayList<CurrencyRate> currencyRates = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            currencyRates = CurrencyRateParser.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyRates;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
