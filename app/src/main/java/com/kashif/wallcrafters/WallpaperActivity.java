package com.kashif.wallcrafters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WallpaperActivity extends AppCompatActivity {

    private ImageView imageViewWallpaper;
    private Button btnSetWall;
    private String imgUrl;
    private WallpaperManager wallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        imageViewWallpaper = (ImageView) findViewById(R.id.imageViewWallpaper);
        btnSetWall = (Button) findViewById(R.id.btnSetWall);
        imgUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imgUrl).into(imageViewWallpaper);

        wallpaperManager = wallpaperManager.getInstance(getApplicationContext());
        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Glide.with(WallpaperActivity.this)
                        .asBitmap()
                        .load(imgUrl)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(WallpaperActivity.this, "Failed to Load image. ", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(@NonNull Bitmap resource, @NonNull Object model, Target<Bitmap> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                                try {
                                    wallpaperManager.setBitmap(resource);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(WallpaperActivity.this, "Failed to set image. ", Toast.LENGTH_SHORT).show();
                                }
                                return false;
                            }
                        }).submit();
                FancyToast.makeText(WallpaperActivity.this, "Wallpaper Set Successfully", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false)
                        .show();
            }

        });

    }
}