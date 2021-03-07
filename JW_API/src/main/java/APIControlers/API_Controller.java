package APIControlers;


import HTTP.Callback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class API_Controller<T> {




    public enum HTTP_Type {GET, PUT, POST, DELETE}

    protected String URL;
    private  Class<T> Class_type;
    public boolean Debug = false;
    public Plugin plugin;
    public String Tocken;

    public API_Controller(String URL, Class<T> type,Plugin plugin) {
        this.URL = URL;
        this.Class_type  = type;
        this.plugin = plugin;

    }

    public String getParams(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        if(params==null)
            return result.toString();
        result.append("?");
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        } catch (UnsupportedEncodingException e) {
            return result.toString();
        }
    }

    public void Request(String json, HTTP_Type http_type, String Url, Callback<String> respond_function) {


        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {
                final String result;
                try {

                    java.net.URL url = new URL(Url); //parameter
                    StringBuffer response = new StringBuffer();
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    BufferedReader rd = null;
                    con.setConnectTimeout(5000);
                    // con.setReadTimeout(5000);
                    con.setUseCaches(false);
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Connection", "close");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod(http_type.toString());
                    con.setDoOutput(true);
                    if (http_type != HTTP_Type.GET)
                    {
                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);
                        OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                        osw.write(String.format(json));
                        osw.flush();
                        osw.close();
                    }

                    int statusCode = con.getResponseCode();


                    if (statusCode >= 200 && statusCode < 400) {
                        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    }
                    else {
                        rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    String inputLine;
                    while ((inputLine = rd.readLine()) != null)
                    {
                        response.append(inputLine);
                    }
                    rd.close();

                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (Debug) {
                                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Responnse: " + ChatColor.AQUA + response.toString() + ChatColor.GREEN + " Status: " + ChatColor.AQUA + statusCode);
                            }
                            respond_function.Response(response.toString(), "Success", statusCode);

                        }
                    });
                } catch (Exception e) {
                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            respond_function.Response(null,e.getMessage(), 999);
                        }
                    });
                    Bukkit.getServer().getConsoleSender().sendMessage("Request error " + URL + "  " + e.getMessage());
                }


            }
        });
    }


    public void Get_One(Map<String, String> params, Callback<T> callback)
    {
        String final_Url = URL+getParams(params);
        this.Request(null, HTTP_Type.GET, final_Url, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                  T result =  gson.fromJson(data,Class_type);
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }

        });



    }

   public void Get_Many(Map<String, String> params, Callback<List<T>> callback)
    {
        String final_Url = URL+getParams(params);
        this.Request(null, HTTP_Type.GET, final_Url, new Callback<String>() {
            Gson gson = new Gson();
          //  ObjectMapper mapper = new ObjectMapper();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    List<T> result = new ArrayList<T>();
                    Gson gson = new Gson();
                    JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
                    for (JsonElement jsonElement : arry)
                        result.add(gson.fromJson(jsonElement, Class_type));
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }
        });
    }

    public void Insert_One(T data, Callback<T> callback)
    {
        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.POST, URL, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    T result =  gson.fromJson(data,Class_type);
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,data+"#"+e.getMessage(),code);
                }

            }
        });

    }
    public void Insert_Many(ArrayList<T> data, Callback<List<T>> callback)
    {

        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.POST, URL, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    List<T> result = new ArrayList<T>();
                    Gson gson = new Gson();
                    JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
                    for (JsonElement jsonElement : arry)
                        result.add(gson.fromJson(jsonElement, Class_type));
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }
        });
    }

    public void Update_One(T data,Map<String, String> params, Callback<T> callback)
    {
        String final_Url = URL+getParams(params);
        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.PUT, final_Url, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    T result =  gson.fromJson(data,Class_type);
                    callback.Response(result,message,code);
                }
                catch (IllegalStateException e)
                {
                    callback.Response(null,message,code);
                }

            }


        });

    }
    public void Update_Many(ArrayList<T> data,Map<String, String> params, Callback<List<T>> callback)
    {
        String final_Url = URL+getParams(params);
        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.PUT, final_Url, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    List<T> result = new ArrayList<T>();
                    Gson gson = new Gson();
                    JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
                    for (JsonElement jsonElement : arry)
                        result.add(gson.fromJson(jsonElement, Class_type));
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }
        });
    }

    public void Delete_One(T data,Map<String, String> params, Callback<T> callback)
    {
        String final_Url = URL+getParams(params);
        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.DELETE, final_Url, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    T result =  gson.fromJson(data,Class_type);
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }


        });

    }
    public void Delete_Many(ArrayList<T> data,Map<String, String> params, Callback<List<T>> callback)
    {
        String final_Url = URL+getParams(params);
        Gson gson = new Gson();
        String json_data = gson.toJson(data);
        this.Request(json_data, HTTP_Type.DELETE, final_Url, new Callback<String>() {
            Gson gson = new Gson();
            @Override
            public void Response(String data, String message, int code)
            {
                try
                {
                    List<T> result = new ArrayList<T>();
                    Gson gson = new Gson();
                    JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
                    for (JsonElement jsonElement : arry)
                        result.add(gson.fromJson(jsonElement, Class_type));
                    callback.Response(result,message,code);
                }
                catch (Exception e)
                {
                    callback.Response(null,e.getMessage(),code);
                }

            }
        });
    }

}
