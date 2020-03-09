package com.rf1.showposts.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.rf1.showposts.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JsonPostTask extends AsyncTask<String, Void, List<Post>> {

    private final WeakReference<Context> context;
    private ProgressDialog dialog;
    private PostLoader postLoader;
    private BufferedInputStream is;

    public JsonPostTask(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void setPostLoader(PostLoader postLoader) {
        this.postLoader = postLoader;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Context context = this.context.get();
        if(context != null)
            dialog = ProgressDialog.show(context, "Carregando", "", true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected List<Post> doInBackground(String... params) {
        String url = params[0];

        try {
            URL requestUrl = new URL(url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode > 400)
                throw new IOException("Erro na comunicação com o servidor remoto");

            InputStream inputStream = urlConnection.getInputStream();

            is = new BufferedInputStream(inputStream);


//            JSONArray jsonObject = new JSONArray(jsonAsString);
//            Log.d("Teste", "asdasdasd");


            List<Post> posts = getPosts();

            is.close();

            return posts;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private List<Post> getPosts() throws JSONException, IOException {

        List<Post> posts = new ArrayList<>();

        String jsonAsString = toString(is);

        JSONArray jsonArray = new JSONArray(jsonAsString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String title = json.getString("title");
            String body = json.getString("body");
            Post postsObj = new Post();
            postsObj.setTitle(body);
            postsObj.setBody(title);
            posts.add(postsObj);
        }

        return posts;
    }

    private String toString(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int alreadyRead;
        while ((alreadyRead = inputStream.read(bytes)) > 0) {
            byteArrayOutputStream.write(bytes, 0, alreadyRead);
        }

        return new String(byteArrayOutputStream.toByteArray());
    }


    @Override
    protected void onPostExecute(List<Post> posts) {
        super.onPostExecute(posts);

        dialog.dismiss();
        if (postLoader != null)
            postLoader.onResult(posts);

    }
}
