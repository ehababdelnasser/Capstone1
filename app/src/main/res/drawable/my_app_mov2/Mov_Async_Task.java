package com.example.lenovo.my_app_mov2;

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

public class Mov_Async_Task extends AsyncTask<String, Void, List<Opj_Mov>> {



    public interface interface_listner1<T> {
        void onTaskComplete(ArrayList<?> result);
    }


    private interface_listner1 Call_Back;
    Context co;

    public Mov_Async_Task(interface_listner1 mCallBack, Context c)

    {
        this.Call_Back = mCallBack;
        this.co = c;
    }

    private final String LOG_TAG = com.example.lenovo.my_app_mov2.Mov_Async_Task.class.getSimpleName();

    private List<Opj_Mov> getMoviesDataFromJson(String moviesJSONString) throws JSONException {
        Opj_Mov moviesObjects;
        final String LISTITEM = "results";
        final String ORIGINAL_TITLE_Mov = "original_title";
        final String MOVIE_POSTER_IMAG = "poster_path";
        final String OVERVIEW_MOV = "overview";
        final String MOV_RATING = "vote_average";
        final String RELEASE_DATE_MOV = "release_date";
        final String MOVIE_ID = "id";


        JSONObject moviesJSONObj = new JSONObject(moviesJSONString);
        JSONArray moviesArray = moviesJSONObj.getJSONArray(LISTITEM);

        List<Opj_Mov> moviesList = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject movie = moviesArray.getJSONObject(i);

            String movie_image = movie.getString(MOVIE_POSTER_IMAG);
            String original_title = movie.getString(ORIGINAL_TITLE_Mov);
            String overview = movie.getString(OVERVIEW_MOV);
            String release_date = movie.getString(RELEASE_DATE_MOV);
            String vote_average = movie.getString(MOV_RATING);
            String film_id = movie.getString(MOVIE_ID);

            moviesObjects = new Opj_Mov(film_id,
                    original_title, movie_image, overview, vote_average, release_date);

            moviesList.add(moviesObjects);
        }
        return moviesList;
    }


    @Override
    protected List<Opj_Mov> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String moviesJSONString = null;

        try {

            final String APPID = "api_key";

            Uri uri = Uri.parse(params[0]).buildUpon()
                    .appendQueryParameter(APPID, co.getString(R.string.api_key))
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
    protected void onPostExecute(List<Opj_Mov> strings) {
        Call_Back.onTaskComplete((ArrayList<Opj_Mov>) strings);
    }


}
