package com.kashif.wallcrafters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private EditText etSearch;
    private ImageView svSearch;
    private RecyclerView recyclerViewCat, recyclerViewWall;
    private ProgressBar pbLoading;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelsArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;

    //kbdkfVru4mFfzd18znFkGFqhLzunqI4o9Ma9u0mhrdkpyKAh5jlpEneI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        svSearch = findViewById(R.id.svSearch);
        recyclerViewCat = findViewById(R.id.recyclerViewCat);
        recyclerViewWall = findViewById(R.id.recyclerViewWall);
        pbLoading = findViewById(R.id.pbLoading);

        wallpaperArrayList = new ArrayList<>();
        categoryRVModelsArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerViewCat.setLayoutManager(linearLayoutManager);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelsArrayList, this, this::onCategoryClick);
        recyclerViewCat.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewWall.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter = new WallpaperRVAdapter(this, wallpaperArrayList);
        recyclerViewWall.setAdapter(wallpaperRVAdapter);

        //getCategories();
        getWallpapers();

        svSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr = etSearch.getText().toString();
                if (searchStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Some Text", Toast.LENGTH_SHORT).show();
                }else {
                    getWallpapersByCategory(searchStr);
                }
            }
        });

    }

    private void getWallpapersByCategory(String category){
        wallpaperArrayList.clear();
        pbLoading.setVisibility(View.GONE);
        /*
        * https://api.pexels.com/v1/search?query=technology&per_page=30&page=1
        * technology is replaced by category because user will search
        * and this is the second api that I had generated
        * */
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                try {
                     photoArray = response.getJSONArray("photos");
                    for (int i = 0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imageUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imageUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to Load the wallpapers.", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","kbdkfVru4mFfzd18znFkGFqhLzunqI4o9Ma9u0mhrdkpyKAh5jlpEneI");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
    private void getWallpapers() {
        wallpaperArrayList.clear();
        pbLoading.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i = 0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imageUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imageUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to Load the wallpapers.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization","kbdkfVru4mFfzd18znFkGFqhLzunqI4o9Ma9u0mhrdkpyKAh5jlpEneI");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getCategories() {
        categoryRVModelsArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1531297484001-80022131f5a1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8dGVjaG5vbG9neXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Nature","https://images.unsplash.com/photo-1426604966848-d7adac402bff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fG5hdHVyZXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Football","https://images.unsplash.com/photo-1553778263-73a83bab9b0c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGZvb3RiYWxsfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Cars","https://images.alphacoders.com/131/thumbbig-1311532.webp"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Anime","https://images6.alphacoders.com/596/thumbbig-596848.webp"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Travel","https://images.unsplash.com/photo-1504150558240-0b4fd8946624?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8VHJhdmVsfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Flowers","https://images8.alphacoders.com/133/thumbbig-1330262.webp"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Architectures","https://images.unsplash.com/photo-1522741938106-d7bce0d1af09?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8QXJjaGl0ZWN0dXJlcyU1RHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Programming","https://images7.alphacoders.com/679/thumbbig-679140.webp"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Arts","https://plus.unsplash.com/premium_photo-1664438942574-e56510dc5ce5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8YXJ0c3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Music","https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8bXVzaWN8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Animals","https://images.unsplash.com/photo-1529778873920-4da4926a72c2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YW5pbWFsc3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Bikes","https://images.alphacoders.com/132/thumbbig-1327888.webp"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelsArrayList.get(position).getCategory();
        getWallpapersByCategory(category);
    }
}