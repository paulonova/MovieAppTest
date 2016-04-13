package se.paulo.nackademin.movieapptest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by paulo on 2016-04-12.
 */
public interface OpenMovieDatabaseInterface {

// Ett interface i Java som kommer användas för att skicka förfrågningar
    // till vårt API, i detta fall http://www.omdbapi.com/

    // Vilka funktioner/metoder har API:t?
    // Vilka av de ska vi använda oss av?
    // Dessa kallas "endpoints"

    // Spotify har /search, /artist o.s.v...
    // OpenMovieDatabase har bara /

    // http://www.omdbapi.com/?t=The+Matrix&y=&plot=short&r=json

    @GET("/")
    Call<Movie> getMovie(@Query("t") String title,
                         @Query("y") String year,
                         @Query("plot") String plot,
                         @Query("r") String response);

    // Call - inbyggd klass
    // Movie - namnet på den klass vi byggt för att ta emot data
    // getMovie - ett påhittat namn på en metod som ska användas
    // Varje parameter har sedan följande format
    // Om URL är: t=The+Matrix
    // Då blir motsvarande i Java: @Query("t") String title
}
