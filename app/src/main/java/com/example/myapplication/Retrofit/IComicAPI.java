package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.Banner;
import com.example.myapplication.Model.Chapter;
import com.example.myapplication.Model.Comic;
import com.example.myapplication.Model.Link;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IComicAPI {
    @GET("banner")
    Observable<List<Banner>> getBannerList();

    @GET("comic")
    Observable<List<Comic>> getComicList();

    @GET("chapter/{comicid}")
    Observable<List<Chapter>> getChapterList(@Path("comicid")int comicid);

    @GET("links/{chapterid}")
    Observable<List<Link>> getImageList(@Path("chapterid")int chapterid);
}
