package com.example.pruebadesalida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Cake> listCakes;
    List<CakeDetails> listCakeDetails;
    RecyclerView rvCakes;
    CakeAdapter cakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // El programa sigue patrón MVC con el MainActivity.java como Controlador
        // Carga los cakes desde la API-REST
        listCakes = new ArrayList<Cake>();
        listCakeDetails = new ArrayList<CakeDetails>();
        rvCakes = (RecyclerView) findViewById(R.id.rvCakes);
        rvCakes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadCakes();
    }

    private void loadCakes() {
        new LoadCakes().execute();
    }

    private class LoadCakes extends AsyncTask<String, Void, String> {
        // Lee la data desde el endpoint cakes y la deja en la variable global listCakes
        // Lee la data desde el endpoint details y la deja en la variable global listCakeDetails
        @Override
        protected String doInBackground(String... strings) {
            try{
                listCakes = getRetrofit(0).create(CakeAPI.class).getCakes("cakes").execute().body();

                for(int i=0; i < listCakes.size(); i++) {
                    CakeDetails cakeDetails = getRetrofit(1).create(CakeAPI.class).getCakeDetails(Integer.toString(listCakes.get(i).getId())).execute().body();
                    listCakeDetails.add(cakeDetails);
                }
                //loadCakesIntoDB();
            } catch(IOException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private void loadCakesIntoDB() {
            try {
                // Carga los cakes de listCakes a la tabla cake_entity
                for (int i = 0; i < listCakes.size(); i++) {
                    CakeApp.databaseApp.cakeDAO().addCake(new CakeEntity(
                                    listCakes.get(i).getId(),
                                    listCakes.get(i).getTitle(),
                                    listCakes.get(i).getPreviewDescription(),
                                    listCakes.get(i).getSize(),
                                    listCakes.get(i).getPrice(),
                                    listCakes.get(i).getImage()
                            )
                    );
                }

                // Carga los cakes de listCakeDetails a la tabla cake_detail_entity
                for (int i = 0; i < listCakeDetails.size(); i++) {
                    CakeApp.databaseApp.cakeDAO().addCakeDetails(new CakeDetailEntity(
                            listCakeDetails.get(i).getId(),
                            listCakeDetails.get(i).getTitle(),
                            listCakeDetails.get(i).getPreviewDescription(),
                            listCakeDetails.get(i).getDetailDescription(),
                            listCakeDetails.get(i).getImage(),
                            listCakeDetails.get(i).getShape(),
                            listCakeDetails.get(i).getSize(),
                            listCakeDetails.get(i).getPrice(),
                            listCakeDetails.get(i).getLastPrice(),
                            listCakeDetails.get(i).isDelivery()
                            )
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // Carga la data desde las listas listCakes y listCakeDetails a la BD Room
        @Override
        protected void onPostExecute(String result) {
            // Carga los datos en el RV
            // cakeAdapter.refreshMyList(listCakes);
            // cakeAdapter.notifyDataSetChanged();

            cakeAdapter = new CakeAdapter(listCakes, getApplicationContext());
            rvCakes.setAdapter(cakeAdapter);
        }

        private Retrofit getRetrofit(int mode) {
            // Si el modo es 0, retorna todo, sino retorna el detalle
            if (mode == 0) {
                return new Retrofit.Builder().baseUrl(getString(R.string.endPoint_Cakes)).addConverterFactory(GsonConverterFactory.create()).build();
            } else {
                return new Retrofit.Builder().baseUrl(getString(R.string.endPoint_CakeDetails)).addConverterFactory(GsonConverterFactory.create()).build();
            }
        }
    }

}