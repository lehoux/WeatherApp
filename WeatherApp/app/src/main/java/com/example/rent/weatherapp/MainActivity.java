package com.example.rent.weatherapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.temperature)
    TextView temperature;

    @BindView(R.id.humidity)
    TextView humidity;

    @BindView(R.id.sky_text)
    TextView skyText;

    @BindView(R.id.wind)
    TextView wind;

    @BindView(R.id.weather_icon)
    ImageView weatherIcon;

    @BindView(R.id.mood_icon)
    ImageView moodIcon;

    @BindView(R.id.search_button)
    FloatingActionButton searchButton;

    @BindView(R.id.city_name_edit_text)
    TextInputEditText editText;

    private Retrofit retrofit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TextView location = (TextView) findViewById(R.id.location);
        TextView temperature = (TextView) findViewById(R.id.temperature);
        TextView skyText = (TextView) findViewById(R.id.sky_text);
        TextView humidity = (TextView) findViewById(R.id.humidity);
        TextView wind = (TextView) findViewById(R.id.wind);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://weathers.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        search("Warszawa");
    }

    private void search(String searchQuery) {

        WeatherService weatherService = retrofit.create(WeatherService.class);

        weatherService.getWeather(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataContainer>() {
                    @Override
                    public void accept(DataContainer dataContainer) throws Exception {
                        WeatherDetails weatherDetails = dataContainer.getWeatherDetails();
                        location.setText(weatherDetails.getLocation());
                        temperature.setText(weatherDetails.getTemperature() + "\u00b0 C");
                        skyText.setText(weatherDetails.getSkyText());
                        humidity.setText(weatherDetails.getHumidity() + " %");
                        wind.setText(weatherDetails.getWind());

                        showImageBySkyText(weatherDetails.getSkyText());

                        if (progressDialog != null) {
                            progressDialog.hide();
                        }

                        showNotification(searchQuery);
                    }
                });
    }

    private void showNotification(String searchQuery) {

        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity
                (this, 11, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mood);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Załadowano informacje pogodowe dla miasta " + searchQuery)
                .setSmallIcon(R.drawable.ic_mood)
                .setFullScreenIntent(mainActivityPendingIntent, false)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
//                .setBigContentTitle("BIG CONTENT TITLE").setSummaryText("SUMMARY TEXT"))
                .setContentIntent(mainActivityPendingIntent)
                .addAction(R.drawable.ic_search, "Search", mainActivityPendingIntent)
                .addAction(R.drawable.ic_search, "Second", mainActivityPendingIntent)
                .addAction(R.drawable.ic_search, "Third", mainActivityPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(searchQuery.hashCode(), notification);


    }

    @OnClick(R.id.search_button)
    void OnSearchButton() {
        progressDialog = ProgressDialog.show(this, "Ladowanko", "Sie laduje", true);
        search(editText.getText().toString());
    }

    private void showImageBySkyText(String skyText) {
        if ("Sky is clear".equalsIgnoreCase(skyText)) {
            weatherIcon.setImageResource(R.drawable.sun);
        } else if ("Few clouds".equalsIgnoreCase(skyText)) {
            weatherIcon.setImageResource(R.drawable.humidity);
        } else {
            weatherIcon.setImageResource(R.drawable.rain);
        }
    }
}

