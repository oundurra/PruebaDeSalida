package com.example.pruebadesalida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CakeDetailActivity extends AppCompatActivity {
    int cakeId;
    TextView tvCakeTitleDetail;
    TextView tvCakePriceDetail;
    TextView tvCakeDescriptionDetail;
    ImageView ivCakeImageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_detail);
        cakeId = getIntent().getIntExtra("id",-1);
        tvCakeTitleDetail = (TextView) findViewById(R.id.tvCakeTitleDetail);
        tvCakePriceDetail = (TextView) findViewById(R.id.tvCakePriceDetail);
        tvCakeDescriptionDetail = (TextView) findViewById(R.id.tvCakeDescriptionDetail);
        ivCakeImageDetail = (ImageView) findViewById(R.id.ivCakeImageDetail);
        new LoadCakeDetails().execute();
    }

    private class LoadCakeDetails extends AsyncTask<String, Void, String> {
        CakeDetailEntity cakeDetailEntity;

        @Override
        protected String doInBackground(String... strings) {
            cakeDetailEntity = CakeApp.databaseApp.cakeDAO().getCakeDetailsById(cakeId);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Picasso.get().load(cakeDetailEntity.getImage()).into(ivCakeImageDetail);
            tvCakeTitleDetail.setText(cakeDetailEntity.getTitle());
            tvCakePriceDetail.setText(Integer.toString(cakeDetailEntity.getPrice()));
            tvCakeDescriptionDetail.setText(cakeDetailEntity.getDetailDescription());
        }
    }
}