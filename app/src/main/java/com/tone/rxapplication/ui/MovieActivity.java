package com.tone.rxapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tone.rxapplication.R;
import com.tone.rxapplication.entity.MovieTheather;
import com.tone.rxapplication.entity.Subject;
import com.tone.rxapplication.http.HttpUtil;
import com.tone.rxapplication.ui.adapter.MovieAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    Disposable disposable;
    CompositeDisposable  compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MovieAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadData();
    }


    private void loadData() {

        disposable = HttpUtil.getInstance()
                .getMovie()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieTheather>() {
                    @Override
                    public void accept(MovieTheather movieTheather) throws Exception {
                        List<Subject> list = movieTheather.getSubjects();
                        if (list != null) {
                            adapter.setList(list);
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        if (disposable != null)
            disposable.dispose();
    }
}
