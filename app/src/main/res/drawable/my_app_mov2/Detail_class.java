package com.example.lenovo.my_app_mov2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Detail_class extends AppCompatActivity {

        @BindView(R.id.fab)
        FloatingActionButton f;
    ImageView imag_View;
    TextView tit_Txt_View, rel_Date_Txt_View, vot_Avg_Txt_View, ovrvw_Txt_View;

    ListView l_View_Trail, l_View_Rev;
    com.example.lenovo.my_app_mov2.Trai_Adapt array_A;


    private com.example.lenovo.my_app_mov2.Opj_Mov mov_Attr;
    int flag = 0;
    public String Ec="x";
    com.example.lenovo.my_app_mov2.Data_Base db_Help;

    String mov_ID, mov_Titl, mov_Img, mov_Over, vot_Avg, rel_date ;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        ButterKnife.bind(this);


        db_Help = new com.example.lenovo.my_app_mov2.Data_Base(this);

        imag_View = (ImageView) findViewById(R.id.mov_imag);
        tit_Txt_View = (TextView) findViewById(R.id.t_v_mov_na);
        rel_Date_Txt_View = (TextView) findViewById(R.id.txt_view_date);
        vot_Avg_Txt_View = (TextView) findViewById(R.id.txt_view_vot);
        ovrvw_Txt_View = (TextView) findViewById(R.id.text_view_over);

        try {
            flag = getIntent().getExtras().getInt("xeno", 0);
        } catch (Exception e) {
        }
        if (flag == 0) {
            mov_Titl = getIntent().getExtras().getString("title");
            tit_Txt_View.setText(mov_Titl);

            rel_date = getIntent().getExtras().getString("releaseDate");
            rel_Date_Txt_View.setText(rel_date);

            vot_Avg = getIntent().getExtras().getString("voteAvg");
            vot_Avg_Txt_View.setText(vot_Avg);

            mov_Over = getIntent().getExtras().getString("overview");
            ovrvw_Txt_View.setText(mov_Over);

            mov_ID = (getIntent().getExtras().getString("id"));

            mov_Img = getIntent().getExtras().getString("image");
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + mov_Img)
                    .into(imag_View);




            Trail_sync_Tsk trailer_Async = new Trail_sync_Tsk();
            trailer_Async.execute();
            Rev_sync_Tsk review_cTask = new Rev_sync_Tsk();
            review_cTask.execute();




            l_View_Trail = (ListView) findViewById(R.id.list_view_trailer);
            l_View_Trail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" +
                            ((TextView) findViewById(R.id.tv_trailerName)).getText())));
                }
            });

            l_View_Trail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    v.onTouchEvent(event);
                    return true;
                }
            });


            l_View_Rev = (ListView) findViewById(R.id.list_view_review);


        }
        else {
            Bundle bundle = getIntent().getExtras();
            bundle.getSerializable("value");
            mov_Attr = (com.example.lenovo.my_app_mov2.Opj_Mov) bundle.getSerializable("value");
            tit_Txt_View.setText(mov_Attr.orig_tit);
            rel_Date_Txt_View.setText(mov_Attr.rel_date);
            vot_Avg_Txt_View.setText(mov_Attr.vot_avg);
            ovrvw_Txt_View.setText(mov_Attr.ov_vw);
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + mov_Attr.mov_imag)
                    .into(imag_View);

        }
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.lenovo.my_app_mov2.Detail_class.this, History.class);
                startActivity(intent);
            }
        });
    }




    public class Trail_sync_Tsk extends AsyncTask<String, Void, List<com.example.lenovo.my_app_mov2.Opj_Mov>>
    {

        private final String LOG_TAG = "ehab";
        final String Mov_URL = "http://api.themoviedb.org/3/movie/" + mov_ID
                + "/videos?api_key=" + R.string.api_key;

        List<com.example.lenovo.my_app_mov2.Opj_Mov> getTrailerDataFromJson(String trailerJsonStr) throws JSONException {
            com.example.lenovo.my_app_mov2.Opj_Mov trailersJsonAttrs;
            final String LIST_TRAIL = "results";
            final String KEY_TRAIL = "key";
            final String NAME_TRAIL = "name";


            JSONObject movieJson = new JSONObject(trailerJsonStr);
            JSONArray moviesArray = movieJson.getJSONArray(LIST_TRAIL);

            List<com.example.lenovo.my_app_mov2.Opj_Mov> list = new ArrayList<>();

            for (int i = 0; i < moviesArray.length(); i++) {

                trailersJsonAttrs = new com.example.lenovo.my_app_mov2.Opj_Mov();
                JSONObject jsonObj = moviesArray.getJSONObject(i);

                trailersJsonAttrs.name = jsonObj.getString(NAME_TRAIL);
                trailersJsonAttrs.key = jsonObj.getString(KEY_TRAIL);

                list.add(trailersJsonAttrs);
                Intent intent = new Intent();
                intent.putExtra("key",trailersJsonAttrs.key);
            }

            return list;
        }

        @Override
        protected List<com.example.lenovo.my_app_mov2.Opj_Mov> doInBackground(String... params)
        {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;

            try {


                final String APP_ID = "0bd79f673879110717eda5abfe25f589";

                Uri uribuilder = Uri.parse(Mov_URL).buildUpon()
                        .appendQueryParameter("api_key", APP_ID)
                        .build();

                URL url = new URL(uribuilder.toString());

                Log.v(LOG_TAG, "Build Uri" + uribuilder.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final Exception e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getTrailerDataFromJson(movieJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<com.example.lenovo.my_app_mov2.Opj_Mov> strings) {
            if (strings != null) {

                array_A = new com.example.lenovo.my_app_mov2.Trai_Adapt(com.example.lenovo.my_app_mov2.Detail_class.this, R.layout.item_trailer, strings);
                l_View_Trail.setAdapter(array_A);
                Log.v("x", strings.get(0).key);
                Log.v("x", Ec);
                Ec = strings.get(0).key;
                Log.v("x", Ec);
            }
        }


    }
    public class Rev_sync_Tsk extends AsyncTask<Void, Void, List<com.example.lenovo.my_app_mov2.Opj_Mov>> {

        final private String LOG_TAG = Rev_sync_Tsk.class.getSimpleName();
        final String MOVIE_URL = "http://api.themoviedb.org/3/movie/" + mov_ID + "/reviews?";

        com.example.lenovo.my_app_mov2.Opj_Mov reviewAttrs;

        List<com.example.lenovo.my_app_mov2.Opj_Mov> getMovieReviewDataFromJson(String movieJsonString) throws JSONException {

            final String LIST = "results";
            final String AUTHOR = "author";
            final String CONTENT = "content";

            List<com.example.lenovo.my_app_mov2.Opj_Mov> reviewJsonList = new ArrayList<com.example.lenovo.my_app_mov2.Opj_Mov>();

            JSONObject movieJson = new JSONObject(movieJsonString);
            JSONArray moviesArray = movieJson.getJSONArray(LIST);

            Log.i(LOG_TAG, moviesArray.toString());
            Log.v(LOG_TAG, moviesArray.toString());
            for (int i = 0; i < moviesArray.length(); i++) {

                reviewAttrs = new com.example.lenovo.my_app_mov2.Opj_Mov();
                JSONObject jsonObj = moviesArray.getJSONObject(i);

                reviewAttrs.author = jsonObj.getString(AUTHOR);
                reviewAttrs.review = jsonObj.getString(CONTENT);

                reviewJsonList.add(reviewAttrs);
            }
            return reviewJsonList;

        }
        @Override
        protected List<com.example.lenovo.my_app_mov2.Opj_Mov> doInBackground(Void... params) {
            Log.v(LOG_TAG, mov_ID);

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String moviesJSONString = null;

            try {
                final String APP_ID = "0bd79f673879110717eda5abfe25f589";

                Uri uri = Uri.parse(MOVIE_URL).buildUpon()
                        .appendQueryParameter("api_key", APP_ID)
                        .build();

                URL url = new URL(uri.toString());
                Log.v(LOG_TAG, "Build Uri" + uri.toString());


                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();


                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {

                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {

                    return null;
                }
                moviesJSONString = stringBuffer.toString();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final Exception e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieReviewDataFromJson(moviesJSONString);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<com.example.lenovo.my_app_mov2.Opj_Mov> list) {

            if (list != null) {
                Rev_Adap reviewAdapter = new Rev_Adap(com.example.lenovo.my_app_mov2.Detail_class.this, R.layout.review_item, list);
                l_View_Rev.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
            }
        }
    }


    public void mdelete() {

        String stringId = mov_ID;
        Uri uri = com.example.lenovo.my_app_mov2.Conr.CONT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        getContentResolver().delete(uri, mov_Titl, null);
    }

    public void add() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_MOVIE_ID, mov_ID);
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_TITLE, mov_Titl);
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_OVERVIEW, mov_Over);
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_RELEASE_DATE, rel_date);
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_POSTER_PATH, mov_Img);
        contentValues.put(com.example.lenovo.my_app_mov2.Conr.CONTRACT_VOTEAVG, vot_Avg);
        getContentResolver().insert(com.example.lenovo.my_app_mov2.Conr.CONT_URI, contentValues);
    }

    private void check(String Title) {

        Boolean res = db_Help.Check(Title);
        if (res) {
            mdelete();
        } else {
            add();
        }
    }


}

