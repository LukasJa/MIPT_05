package com.example.new_project_05;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<CurrencyRate>> {

    private static final int DATA_LOADER_ID = 1;
    private EditText editTextCurrencyCode;
    private Button buttonCheckRates;
    private ListView listViewCurrencyRates;
    private ArrayAdapter<String> adapter;
    private List<String> displayedCurrencyRates = new ArrayList<>();
    private ArrayList<CurrencyRate> allCurrencyRates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCurrencyCode = findViewById(R.id.editTextCurrencyCode);
        buttonCheckRates = findViewById(R.id.button_check_rates);
        listViewCurrencyRates = findViewById(R.id.listViewCurrencyRates);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayedCurrencyRates);
        listViewCurrencyRates.setAdapter(adapter);

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
        allCurrencyRates.clear();
        allCurrencyRates.addAll(data);
        filterCurrencyRates(editTextCurrencyCode.getText().toString().trim());
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CurrencyRate>> loader) {
        allCurrencyRates.clear();
        displayedCurrencyRates.clear();
        adapter.notifyDataSetChanged();
    }

    private void filterCurrencyRates(String currencyCode) {
        displayedCurrencyRates.clear();
        if (TextUtils.isEmpty(currencyCode)) {
            for (CurrencyRate currencyRate : allCurrencyRates) {
                displayedCurrencyRates.add(currencyRate.getCurrencyCode() + " - " + currencyRate.getRate());
            }
        } else {
            for (CurrencyRate currencyRate : allCurrencyRates) {
                if (currencyRate.getCurrencyCode().equalsIgnoreCase(currencyCode)) {
                    displayedCurrencyRates.add(currencyRate.getCurrencyCode() + " - " + currencyRate.getRate());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
