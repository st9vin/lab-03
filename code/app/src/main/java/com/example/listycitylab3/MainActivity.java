package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditFragment.EditCityListener {

    private ArrayList<City> cities;
    private ListView cityList;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new ArrayList<>();
        cities.add(new City("Edmonton", "AB"));
        cities.add(new City("Vancouver", "BC"));
        cities.add(new City("Toronto", "ON"));
        cities.add(new City("Calgary", "AB"));

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityAdapter(this, cities);
        cityList.setAdapter(cityAdapter);

        // Open edit fragment when city clicked
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City clicked = cities.get(position);
            EditFragment frag = EditFragment.newInstance(clicked, position);
            frag.show(getSupportFragmentManager(), "edit_dialog");
        });
    }

    @Override
    public void onCityEdited(City editedCity, int position) {
        cities.set(position, editedCity);
        cityAdapter.notifyDataSetChanged();
    }
}
