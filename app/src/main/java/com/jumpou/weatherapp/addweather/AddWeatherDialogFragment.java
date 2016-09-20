package com.jumpou.weatherapp.addweather;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jumpou.weatherapp.R;
import com.jumpou.weatherapp.weather.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zimburg on 19/09/16.
 */
public class AddWeatherDialogFragment extends DialogFragment implements AddWeatherContract.View {

    private AddWeatherContract.Presenter mPresenter;
    @BindView(R.id.et_city)
    EditText mEditTextCity;
    @BindView(R.id.loading_add_city)
    ProgressBar mLoadingIndicator;

    public static AddWeatherDialogFragment newInstance() {
        return new AddWeatherDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View view = inflater.inflate(R.layout.dialog_add_city, null);
        ButterKnife.bind(this, view);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.addCity(mEditTextCity.getText().toString());
                }
            });
        }
    }

    @Override
    public void setPresenter(AddWeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void closeView() {
        ((WeatherFragment) getTargetFragment()).addCityDialogDismiss();
        AddWeatherDialogFragment.this.getDialog().dismiss();
    }

    @Override
    public void displayErrorMessage(int stringRessource) {
        mEditTextCity.setError(getString(stringRessource));
    }

    @Override
    public void displayLoadingIndicator(Boolean isVisible) {
        mLoadingIndicator.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mEditTextCity.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }
}
