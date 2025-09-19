package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditFragment extends DialogFragment {
    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS = "arg_pos";

    public interface EditCityListener {
        void onCityEdited(City editedCity, int position);
    }

    private EditCityListener listener;

    public static EditFragment newInstance(City city, int position) {
        EditFragment f = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POS, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityListener) {
            listener = (EditCityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EditCityListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = requireArguments();
        final City city = (City) args.getSerializable(ARG_CITY);
        final int pos = args.getInt(ARG_POS);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit, null);

        EditText nameEt = view.findViewById(R.id.edit_city_name);
        EditText provEt = view.findViewById(R.id.edit_city_province);

        if (city != null) {
            nameEt.setText(city.getName());
            provEt.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Edit City")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = nameEt.getText().toString().trim();
                    String newProv = provEt.getText().toString().trim();
                    City edited = new City(newName, newProv);
                    listener.onCityEdited(edited, pos);
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
