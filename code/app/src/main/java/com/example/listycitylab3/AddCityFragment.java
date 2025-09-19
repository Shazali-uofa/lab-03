package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);


        City existingCity = null;
        if (getArguments() != null) {
            existingCity = (City) getArguments().getSerializable("city");
        }

        if (existingCity != null) {
            editCityName.setText(existingCity.getName());
            editProvinceName.setText(existingCity.getProvince());
        }

        City editCity = existingCity;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (editCity == null) {
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        editCity.setName(cityName);
                        editCity.setProvince(provinceName);
                        listener.addCity(editCity);
                    }
                })
                .create();

    }

    public static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", (Serializable) city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }




}
