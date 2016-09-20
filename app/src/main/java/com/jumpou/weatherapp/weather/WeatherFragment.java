package com.jumpou.weatherapp.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jumpou.weatherapp.R;
import com.jumpou.weatherapp.addweather.AddWeatherDialogFragment;
import com.jumpou.weatherapp.addweather.AddWeatherPresenter;
import com.jumpou.weatherapp.data.WeatherItem;
import com.jumpou.weatherapp.util.EmptyRecyclerView;
import com.jumpou.weatherapp.util.Injection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zimburg on 15/09/16.
 */
public class WeatherFragment extends Fragment implements WeatherContract.View {
    private WeatherContract.Presenter mPresenter;
    private FloatingActionButton mFabAddWeatherItem;
    private WeatherAdapter mAdapter;
    private AddWeatherDialogFragment mAddWeatherDialogFragment;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.weather_recycler_view)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.loading_weather)
    ProgressBar mLoadingIndicator;

    public WeatherFragment() {
        // Requires empty public constructor
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new WeatherAdapter(getActivity(), new ArrayList<WeatherItem>(), new WeatherListener() {
            @Override
            public void onWeatherTrashTouched(int id) {
                mPresenter.trashWeatherTouched(id);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        mFabAddWeatherItem = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_weather_item);
        mFabAddWeatherItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addWeatherItem();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(emptyView);


        mPresenter.start();
        return view;
    }

    @Override
    public void setPresenter(@NonNull WeatherContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddWeatherItem() {
        if (mAddWeatherDialogFragment == null) {
            mAddWeatherDialogFragment = AddWeatherDialogFragment.newInstance();
            // Create the presenter
            new AddWeatherPresenter(mAddWeatherDialogFragment, Injection.provideNetworkService(getActivity()).getWeatherAPI(), Injection.provideDataService(getActivity()));
            mAddWeatherDialogFragment.setTargetFragment(this, 0);
        }
        mAddWeatherDialogFragment.show(getFragmentManager(), "mFacebookRankingDialogFragment");
    }

    @Override
    public void showWeatherItems(ArrayList<WeatherItem> weatherItemsList) {
        mAdapter.swap(weatherItemsList);
    }

    @Override
    public void addCityDialogDismiss() {
        mPresenter.addCityDialogDismiss();
    }

    public interface WeatherListener {
        void onWeatherTrashTouched(int id);
    }

    @Override
    public void displayLoadingIndicator(Boolean isVisible) {
        mLoadingIndicator.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void displayNoNetworkMessage() {
        if (getView() != null) {
            Snackbar snackbarNoInternetConnection = Snackbar
                    .make(getView(), getString(R.string.error_msg_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.again), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.retryInternetAsked();
                        }
                    });
            snackbarNoInternetConnection.show();
        }
    }
}
