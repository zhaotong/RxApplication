package com.tone.rxapplication.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tone.rxapplication.R;
import com.tone.rxapplication.entity.MovieTheather;
import com.tone.rxapplication.entity.Subject;
import com.tone.rxapplication.http.HttpUtil;
import com.tone.rxapplication.ui.adapter.MovieAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
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


    }

    public void loadData(View view){
        loadData();
    }

    private void loadData() {

//        disposable = HttpUtil.getInstance()
//                .getMovie()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<MovieTheather>() {
//                    @Override
//                    public void accept(MovieTheather movieTheather) throws Exception {
//                        List<Subject> list = movieTheather.getSubjects();
//                        if (list != null) {
//                            adapter.setList(list);
//                        }
//                    }
//                });
//
//        compositeDisposable.add(disposable);

        Observable<String> observable1 = HttpUtil.getInstance()
                .getTime();

        Observable<String> observable2 = HttpUtil.getInstance()
                .getTime();

        Observable.zip(observable1, observable2, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String movieTheather, String movieTheather2) throws Exception {

                Log.d("", "apply: "+movieTheather);
                return true;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        if (disposable != null)
            disposable.dispose();
    }
}
