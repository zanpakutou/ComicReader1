package com.example.myapplication;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.MyComicAdapter;
import com.example.myapplication.Adapter.MySliderAdapter;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.Banner;
import com.example.myapplication.Model.Comic;
import com.example.myapplication.Retrofit.IComicAPI;
import com.example.myapplication.Service.PicassoImageLoadingService;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity {

    Slider slider;
    IComicAPI iComicAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView recycler_comic;
    TextView txt_comic;
    @Override
    protected  void onStop() {
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init API
        iComicAPI = Common.getAPI();

        //View
        slider = (Slider)findViewById(R.id.banner_slider);
        Slider.init(new PicassoImageLoadingService());

        recycler_comic = (RecyclerView) findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        recycler_comic.setLayoutManager(new GridLayoutManager(this,2));

        txt_comic = (TextView)findViewById(R.id.txt_comic);

        fetchBanner();
        fetchComic();
    }

    private void fetchComic() {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build();
        dialog.show();
        compositeDisposable.add(iComicAPI.getComicList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        recycler_comic.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                        txt_comic.setText(new StringBuilder("NEW COMIC (").append(comics.size()).append(")"));
                        dialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error while load comics",Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    private void fetchBanner(){
        compositeDisposable.add(iComicAPI.getBannerList().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<Banner>>() {
                                @Override
                                public void accept(List<Banner> banners) throws Exception {
                                    slider.setAdapter(new MySliderAdapter(banners));
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Toast.makeText(MainActivity.this, "Error while loading banner", Toast.LENGTH_SHORT).show();
                                }
                            }));

    }
}
