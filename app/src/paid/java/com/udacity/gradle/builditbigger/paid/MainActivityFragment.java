package com.udacity.gradle.builditbigger.paid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.jokedisplay.JokeActivity;
import com.example.android.jokesourcelibrary.JokeSource;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class MainActivityFragment extends Fragment {
    private Button mJoke_btn;

    private String mJoke;

    private Intent jokeIntent;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJoke_btn = root.findViewById(R.id.btn_joke);

        mJoke_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JokeAsyncTask().execute();
            }
        });

        return root;
    }

    public void setJoke(String joke) {

        mJoke = joke;
    }


    public class JokeAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;
        @SuppressLint("StaticFieldLeak")


        private JokeSource mJokeSource = new JokeSource();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            String name = mJokeSource.Joke();

            try {
                return name;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            setJoke(result);

            jokeIntent = new Intent(getActivity(), JokeActivity.class);

            jokeIntent.putExtra(JokeActivity.jokeName, mJoke);

            startActivity(jokeIntent);
        }
    }
}
