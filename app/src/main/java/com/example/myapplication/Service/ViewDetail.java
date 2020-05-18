package com.example.myapplication.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.MyViewPagerAdapter;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.Link;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.IComicAPI;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewDetail extends AppCompatActivity {

    IComicAPI iComicAPI;
    ViewPager myViewPager;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_chapter_name;
    View back, next;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        iComicAPI = Common.getAPI();
        myViewPager  = (ViewPager) findViewById(R.id.view_pager);
        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapter_index == 0) {
                    Toast.makeText(ViewDetail.this, "You are reading first chapter" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    Common.chapter_index--;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (Common.chapter_index == Common.chapterList.size() - 1) {
                    Toast.makeText(ViewDetail.this, "You are reading last chapter" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    Common.chapter_index++;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());
                }
            }
        });

        fetchLinks(Common.selected_chapter.getID());
    }

    private void fetchLinks(int id) {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait...").setCancelable(false).build();
        dialog.show();

        compositeDisposable.add(iComicAPI.getImageList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Link>>() {
                    @Override
                    public void accept(List<Link> links) throws Exception {
                        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), links);
                        myViewPager.setAdapter(adapter);

                        txt_chapter_name.setText(Common.formatString(Common.selected_chapter.getName()));

                        //Create Bool Flip Page
                        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                        bookFlipPageTransformer.setScaleAmountPercent(10f);
                        myViewPager.setPageTransformer(true, bookFlipPageTransformer);

                        dialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ViewDetail.this,"This chapter is being translating",Toast.LENGTH_LONG);
                        dialog.dismiss();
                    }
                }));
    }
}
