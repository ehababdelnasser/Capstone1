package com.example.lenovo.capstone;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class MovieAsyncTask extends AsyncTask<String, Void, List<MovieObjects>> {


    private listner1 mCallBack;
    Context c;

    public MovieAsyncTask(listner1 mCallBack, Context c)

    {
        this.mCallBack = mCallBack;
        this.c = c;
    }

    private final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

    private List<MovieObjects> getMoviesDataFromJson(String moviesJSONString) throws JSONException {
        MovieObjects moviesObjects;
        final String LIST = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String MOVIE_POSTER = "poster_path";
        final String OVERVIEW = "overview";
        final String RATING = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String FILM_ID = "id";

        JSONObject moviesJSONObj = new JSONObject(moviesJSONString);
        JSONArray moviesArray = moviesJSONObj.getJSONArray(LIST);

        List<MovieObjects> moviesList = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject movie = moviesArray.getJSONObject(i);

            String movie_image = movie.getString(MOVIE_POSTER);
            String original_title = movie.getString(ORIGINAL_TITLE);
            String overview = movie.getString(OVERVIEW);
            String release_date = movie.getString(RELEASE_DATE);
            String vote_average = movie.getString(RATING);
            String film_id = movie.getString(FILM_ID);

            moviesObjects = new MovieObjects(film_id,
                    original_title, movie_image, overview, vote_average, release_date);

            moviesList.add(moviesObjects);
        }
        return moviesList;
    }


    @Override
    protected List<MovieObjects> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String moviesJSONString = null;

        try {

            final String APPID = "api_key";

            Uri uri = Uri.parse(params[0]).buildUpon()
                    .appendQueryParameter(APPID, c.getString(R.string.api_key))
                    .build();

            URL url = new URL(uri.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line).append("\n");
            }

            if (stringBuffer.length() == 0) {
                return null;
            }
            moviesJSONString = stringBuffer.toString();
        } catch (Exception e) {
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final Exception ignored) {
                }
            }
        }

        try {
            return getMoviesDataFromJson(moviesJSONString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<MovieObjects> strings) {
        mCallBack.onTaskComplete((ArrayList<MovieObjects>) strings);
    }


}
