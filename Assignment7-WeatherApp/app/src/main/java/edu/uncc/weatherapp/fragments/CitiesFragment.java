package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.FragmentCitiesBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.CityResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CitiesFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<City> cities = new ArrayList<>();
    ArrayAdapter<City> adapter;

    public CitiesFragment() {
        // Required empty public constructor
    }

    FragmentCitiesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCities();
    }

    void getCities(){

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Gson gson = new Gson();
                    cities.clear();
                    CityResponse cityResponse = gson.fromJson(body, CityResponse.class);
                    cities.addAll(cityResponse.cities);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, cities);
                            binding.listView.setAdapter(adapter);

                            binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    City selectedCity = adapter.getItem(position);
                                    mListener.gotoWeatherForecast(selectedCity);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    CitiesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CitiesListener) context;
    }

    public interface CitiesListener{
        void gotoWeatherForecast(City city);
    }
}