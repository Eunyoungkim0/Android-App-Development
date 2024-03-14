package edu.uncc.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import edu.uncc.weatherapp.models.WeatherForecastResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private City mCity;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        binding.textViewCityName.setText(mCity.getName() + ", " + mCity.getState());
        getWeatherForecastURL(mCity.getLat(), mCity.getLng());
    }

    void getWeatherForecastURL(double latitude, double longitude){
        HttpUrl url = HttpUrl.parse("https://api.weather.gov/points/").newBuilder()
                .addPathSegment(String.valueOf(latitude) + "," + String.valueOf(longitude))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Gson gson = new Gson();

                    WeatherForecastResponse weatherForecastResponse = gson.fromJson(body, WeatherForecastResponse.class);
                    String forecastUrl = weatherForecastResponse.properties.forecast;
                    getForecast(forecastUrl);
                }
            }
        });
    }

    void getForecast(String forecastUrl){
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    Gson gson = new Gson();

                    WeatherForecastResponse weatherForecastResponse = gson.fromJson(body, WeatherForecastResponse.class);
                    ArrayList<Forecast> periods = weatherForecastResponse.properties.periods;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new ForecastAdapter(periods);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    WeatherForecastListener mListener;
    public interface WeatherForecastListener{

    }

    class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{
        ArrayList<Forecast> periods;
        public ForecastAdapter(ArrayList<Forecast> periods) {
            this.periods = periods;
        }

        @NonNull
        @Override
        public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ForecastListItemBinding mBinding = ForecastListItemBinding.inflate(getLayoutInflater(), parent, false);
            ForecastViewHolder holder = new ForecastViewHolder(mBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
            Forecast forecast = periods.get(position);
            holder.setupUI(forecast);
        }

        @Override
        public int getItemCount() {
            return periods.size();
        }

        private class ForecastViewHolder extends RecyclerView.ViewHolder {
            ForecastListItemBinding mBinding;
            Forecast mForecast;
            public ForecastViewHolder(ForecastListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Forecast forecast){
                mForecast = forecast;
                mBinding.textViewDateTime.setText(mForecast.getStartTime());
                mBinding.textViewTemperature.setText("Temperature: " + mForecast.getTemperature() + " F");
                mBinding.textViewHumidity.setText("Humidity: " + mForecast.getHumidity() + "%");
                mBinding.textViewWindSpeed.setText("Wind Speed: " + mForecast.getWindSpeed());
                mBinding.textViewForecast.setText(mForecast.getShortForecast());
                Picasso.get().load(mForecast.getIcon()).into(mBinding.imageView);
            }
        }
    }
}