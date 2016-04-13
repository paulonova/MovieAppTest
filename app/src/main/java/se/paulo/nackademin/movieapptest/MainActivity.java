package se.paulo.nackademin.movieapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

   /*
        För att använda ett RESTful API i Android
        1. Lägg in Retrofit i build.gradle (app)
           compile 'com.squareup.retrofit2:retrofit:2.0.1'
        2. Lägg in att vi använder oss av internet i AndroidManifest.xml:
           <uses-permission android:name="android.permission.INTERNET" />
        3. För att konvertera informationen (från JSON till Java-objekt) så behöver vi GSON (en konverterare)
           compile 'com.google.code.gson:gson:2.6.2'
           compile 'com.squareup.retrofit2:converter-gson:2.0.1'
        4. Förbered för att hämta JSON-data - skapa en klass som motsvarar det vi hämtar
           * Skapa klassen Movie
           * Lägg till instansvariabler för det du vill komma åt i JSON-datan
           * Skapa getters (get-metoder) för våra instansvariabler
        5. Ta fram koden som gör så att Retrofit går in på en sida, hämtar data
           och gör om det till ett objekt i Java!
           Se metoden findMovie nedan
        6. Skapa ett Interface i Java för det RESTful API vi vill kommunicera med
           Se OpenMovieDatabaseInterface.java
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Instansmetod
    public void findMovie(View view) {
        // Vilken film ska vi söka efter?
        EditText editText = (EditText) findViewById(R.id.movie_to_find);
        String movie = editText.getText().toString();

        // Adressen vi utgår ifrån
        String baseUrl = "http://www.omdbapi.com/";

        // Skapa en instans av Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Börja kommunicera med Open Movie Database API
        OpenMovieDatabaseInterface omdbApi = retrofit.create(OpenMovieDatabaseInterface.class);

        // Vad vi vill göra:
        // http://www.omdbapi.com/?t=The+Matrix&y=&plot=short&r=json
        Call<Movie> call = omdbApi.getMovie(movie, "", "short", "json");

        // När vi ska koppla upp oss på internet behöver vi göra det samtidigt
        // som resten av koden i programmet. Vi ser till att kod körs parallellt.
        // Kod som körs parallellt säger man körs på olika "trådar"
        // All kod körs normalt på huvudtråden (MainThread)
        // När vi vill göra något som kan ta tid vill vi göra det på en annan tråd
        // (Så att det inte tar fokus och får appen att verka "stilla")

        // Följande rad kod ser till att vi gör vår internetförfrågan på en annan tråd
        call.enqueue(new Callback<Movie> () {

                         @Override
                         public void onResponse(Call<Movie> call, Response<Movie> response) {
                             // Vi har fått ett svar på vår förfrågan

                             // Skapa ett objekt Movie från vår response
                             Movie returnedMovie = response.body();

                             /* Nu kan vi använda följande för att få ut information om filmen
                             returnedMovie.getTitle()     -> "The Matrix"
                             returnedMovie.getReleased()  -> "31 Mar 1999"
                             returnedMovie.getYear()      -> 1999
                             returnedMovie.getRated()     -> "R"
                              */

                             // Visa informationen i appen
                             String movieInformation = returnedMovie.getTitle() + " släpptes " + returnedMovie.getReleased() + " och är " + returnedMovie.getRated() + "-rated";
                             TextView textView = (TextView) findViewById(R.id.movie_information);
                             textView.setText(movieInformation);

                             // Visa filmens poster
                             setMoviePoster(returnedMovie.getPoster());

                             Log.i("Movie", returnedMovie.getReleased());

                         }

                         @Override
                         public void onFailure(Call<Movie> call, Throwable t) {
                             // Något gick fel när vi gjorde vår förfrågan
                             TextView textView = (TextView) findViewById(R.id.movie_information);
                             textView.setText("Kunde inte hänta data,");
                             Log.e("ERROR", "Kunde inte hämta data!");
                         }
                     }
        );
    } // Slut på metoden findMovie

    // Ladda in en bild!
    public void setMoviePoster(String url) {
        // Hämta imageview där bilden ska hamna
        ImageView moviePosterView = (ImageView) findViewById(R.id.movie_poster);

        // Sätt bilden på url i vår imageView
        Glide.with(this).load(url).into(moviePosterView);
    }


}
