package com.example.new_project_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CurrencyRate>> {

    private static final int DATA_LOADER_ID = 1;
    private TextView textView;
    private Button buttonCheckRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        buttonCheckRates = findViewById(R.id.button_check_rates);

        buttonCheckRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportLoaderManager().restartLoader(DATA_LOADER_ID, null, MainActivity.this).forceLoad();
            }
        });
    }

    @Override
    public Loader<ArrayList<CurrencyRate>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this, "https://www.floatrates.com/daily/usd.xml");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CurrencyRate>> loader, ArrayList<CurrencyRate> data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CurrencyRate currencyRate : data) {
            stringBuilder.append(currencyRate.getCurrencyCode())
                    .append(": ")
                    .append(currencyRate.getRate())
                    .append("\n");
        }
        textView.setText(stringBuilder.toString());
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CurrencyRate>> loader) {
        textView.setText("");
    }
}
