package com.example.myapplication.Common;

import com.example.myapplication.Model.Chapter;
import com.example.myapplication.Model.Comic;
import com.example.myapplication.Retrofit.IComicAPI;
import com.example.myapplication.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static Comic selected_comic;
    public static Chapter selected_chapter;
    public static int chapter_index = -1;
    public static List<Chapter> chapterList = new ArrayList<>();

    public static IComicAPI getAPI(){
        return RetrofitClient.getInstance().create(IComicAPI.class);
    }

    public static String formatString(String name) {
        StringBuilder finalResult = new StringBuilder(name.length() > 15 ? name.substring(0,5) + "...":name);
        return finalResult.toString();
    }


}
