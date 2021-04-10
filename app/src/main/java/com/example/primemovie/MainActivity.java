package com.example.primemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.primemovie.Retrofif.RetroiftClient;
import com.example.primemovie.adapter.BannerMoviesPagerAdapter;
import com.example.primemovie.adapter.MainRecyclerAdapter;
import com.example.primemovie.model.AllCategories;
import com.example.primemovie.model.BannerMovie;
import com.example.primemovie.model.CategoryItemList;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    BannerMoviesPagerAdapter bannerMoviePagerAdapter;
    TabLayout indicatorTab,categoryTab;
    //indicator is used category
    ViewPager bannerMovieViewPager;

    List<BannerMovie> homeBannerList;
    List<BannerMovie> tvShowBannerList;
    List<BannerMovie> movieBannerList;
    List<BannerMovie> kidsBannerList;

    Timer sliderTimer;
    Toolbar toolbar;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategories> allCategoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab = findViewById(R.id.tab_indicator); //dot
        categoryTab = findViewById(R.id.tabLayout);//category

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);


        //array list data tab
        homeBannerList = new ArrayList<>();

        tvShowBannerList = new ArrayList<>();

        movieBannerList = new ArrayList<>();

        kidsBannerList = new ArrayList<>();

        //fetch banner data from  server
        getBannerData();
        //fetch movie server from server
        getAllMovieData(1);


        //on tab change selected data
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviePagerAdapter(tvShowBannerList);
                        getAllMovieData(2);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviePagerAdapter(movieBannerList);
                        getAllMovieData(3);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviePagerAdapter(kidsBannerList);
                        getAllMovieData(4);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviePagerAdapter(homeBannerList);
                        getAllMovieData(1);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        allCategoryList = new ArrayList<>();

    }
    private void setBannerMoviePagerAdapter(List<BannerMovie> bannerMovieList) {
        bannerMovieViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviePagerAdapter = new BannerMoviesPagerAdapter(  this, bannerMovieList);
        bannerMovieViewPager.setAdapter(bannerMoviePagerAdapter);
        indicatorTab.setupWithViewPager(bannerMovieViewPager);//dot

        sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
        indicatorTab.setupWithViewPager(bannerMovieViewPager);
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bannerMovieViewPager.getCurrentItem() < homeBannerList.size() - 1) {
                        bannerMovieViewPager.setCurrentItem(bannerMovieViewPager.getCurrentItem() + 1);
                    }else {
                        bannerMovieViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void setMainRecycler(List<AllCategories> allCategoriesList)
    {
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoriesList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }
    //when user change tab then automatically scroll up
    private void setScrollDefaultState()
    {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }
    //disposable method of rx java
    private void getBannerData()
    {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetroiftClient.getRetrofitClient().getAllBanners()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<BannerMovie>>() {
                          @Override
                          public void onNext(List<BannerMovie> bannerMovies) {
                              for(int i = 0;i<bannerMovies.size();i++)
                              {
                                  if(bannerMovies.get(i).getBannerCategoryId().toString().equals("1"))
                                  {
                                      homeBannerList.add(bannerMovies.get(i));
                                  }
                                  else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("2"))
                                  {
                                      tvShowBannerList.add(bannerMovies.get(i));
                                  }
                                  else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("3"))
                                  {
                                      movieBannerList.add(bannerMovies.get(i));
                                  }
                                  else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("4"))
                                  {
                                      kidsBannerList.add(bannerMovies.get(i));
                                  }
                                  else
                                  {

                                  }
                              }

                          }
                          @Override
                          public void onError(Throwable e) {

                          }

                          @Override
                          public void onComplete() {
                              //default tab
                              setBannerMoviePagerAdapter(homeBannerList);
                          }
                      })
        );

    }


    private void getAllMovieData(int categoryId)
    {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetroiftClient.getRetrofitClient().getAllCategoryMovies(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<AllCategories>>() {
                    @Override
                    public void onNext(List<AllCategories> allCategoriesList){
                        setMainRecycler(allCategoriesList);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        //default tab
                    }})
        );
    }
}
