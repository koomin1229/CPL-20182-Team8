package comaa.example.hanhyeonmin.trans;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpAsyncTask extends AsyncTask<Void, Void, ResultBody> implements JsonDeserializer{
    private String url = "http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000/";
    private String action;
    private String path;
    private Type typeToken;
    private MyCallBack callback;


    public HttpAsyncTask(String action, String path, Type typeToken, MyCallBack callback) {
        this.action = action;
        this.path = path;
        this.typeToken = typeToken;
        this.callback = callback;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    OkHttpClient client = new OkHttpClient();

    @Override
    protected ResultBody doInBackground(Void... params) {
        String strUrl = this.url + this.path;
        ResultBody resultBody = null;

        try {

            Request request = null;
            request = new Request.Builder()
                    .url(strUrl)
                    .build();

            /*if(this.action.equalsIgnoreCase("GET")){ //GET, 대소문자 상관X
                // 요청
                request = new Request.Builder()
                        .url(strUrl)http://ec2-18-222-114-158.us-east-2.compute.amazonaws.com:3000
                        .build();
            }*/

            /*
            else if(this.action.equalsIgnoreCase("PUT")){
                request = new Request.Builder()
                        .url(strUrl)
                        .get(request_body)
                        .build();
            }
            */
/*
            else if(this.action.equalsIgnoreCase("DELETE")) {
                request = new Request.Builder()
                        .url(strUrl)
                        .delete()
                        .build();
            }*/

            // 응답 : header, body 정보 담겨있음
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            resultBody = gson.fromJson(response.body().string(), typeToken); //fromJson 사용하면 자동으로 변환
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResultBody<>(resultBody.getSuccess(), resultBody.getSize(), resultBody.getDatas(), resultBody.getError());
    }

    @Override
    protected void onPostExecute(ResultBody resultBody) {
        super.onPostExecute(resultBody);

        this.callback.doTask(resultBody);
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}