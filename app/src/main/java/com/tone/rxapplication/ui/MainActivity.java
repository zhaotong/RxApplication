package com.tone.rxapplication.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tone.rxapplication.R;
import com.tone.rxapplication.ui.fragment.BlankFragment1;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import androidx.fragment.app.Fragment;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2;
    private TextView text;

    private int count = 0;

    private FrameLayout frameLayout;

    MyImageView imageView;

    private Stack<Fragment> stack = new Stack<>();

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack("BlankFragment", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        text = findViewById(R.id.text);
        frameLayout = findViewById(R.id.frameLayout);
        imageView = findViewById(R.id.imageView);


        imageView.setImageResource(R.drawable.ic_launcher_background);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new BlankFragment1(), "BlankFragment")
                        .commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });

        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {

            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {
//                button2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss SSS");
//                        String s = format.format(new Date());
//
//                        Calendar calendar = Calendar.getInstance();
//                        int month = calendar.get(Calendar.MONTH);
//                        calendar.set(Calendar.MONTH, 11);
//                        calendar.add(Calendar.MONTH, 1);
//                        calendar.get(Calendar.YEAR);
//                        emitter.onNext("button2 _" + s);
//                    }
//                });
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                button2.setText(s);
                return Observable.just(s);
            }
        });

        Disposable disposable = Observable.zip(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s2;
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        text.setText(s);
                    }
                });

//
//        Disposable disposable = Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                        button1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss SSS");
//                                String s = format.format(new Date());
//                                button1.setText(s);
//                                count++;
//                                emitter.onNext("button1 ----> " + s);
//                            }
//                        });
//                    }
//                })
//                .flatMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String s) throws Exception {
//                        if (count % 5 == 1)
//                            throw new Exception("flatMap  error  count=" + count);
//                        return Observable.just(s);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
////                .doOnError(new Consumer<Throwable>() {
////                    @Override
////                    public void accept(Throwable throwable) throws Exception {
////                        text.setText("doOnError  "+throwable.getMessage());
////                    }
////                })
//
//                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                                text.setText("retryWhen  "+throwable.getMessage());
//                                if (count % 5 == 1)
//                                    throw new Exception(" retryWhen  error  count=" + count);
//                                return Observable.just("retryWhen  " + throwable.getMessage());
//                            }
//                        });
//                    }
//                })

//                .onErrorResumeNext(new Function<Throwable, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(Throwable throwable) throws Exception {
////                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss SSS");
////                        String s = format.format(new Date());
//                        return Observable.just(throwable.getMessage());
//                    }
//                })
//                .subscribe(
//                        new Consumer<String>() {
//                            @Override
//                            public void accept(String s) throws Exception {
//                                text.setText(s);
//                            }
//                        },
//                        new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Log.d("", "accept: "+throwable.getMessage());
//                            }
//                        },
//                        new Action() {
//                            @Override
//                            public void run() throws Exception {
//
//                                Log.d("", "run: ");
//                            }
//                        },
//                        new Consumer<Disposable>() {
//                            @Override
//                            public void accept(Disposable disposable) throws Exception {
//
//                                Log.d("", "accept: ");
//                            }
//                        });

//        Flowable<String> flowable1 = Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss SSS");
//                        String s = format.format(new Date());
//                        button1.setText(s);
//                        emitter.onNext("button1 ----> " + s);
//                    }
//                });
//            }
//        }, BackpressureStrategy.LATEST);

//        Flowable<String> flowable2 = Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
//                button2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss SSS");
//                        String s = format.format(new Date());
//                        button2.setText(s);
//                        emitter.onNext("button2 ----> " + s);
//                    }
//                });
//            }
//        }, BackpressureStrategy.LATEST);


//        Disposable disposable =
//                Flowable.zip(flowable1, flowable2,
//                        new BiFunction<String, String, String>() {
//                            @Override
//                            public String apply(String s, String s2) {
////                        throw new Exception("");
//                                return s + "\n" + s2;
//                            }
//                        })
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
////                        s.request(1);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        text.setText(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.d("", "onError: "+t.getMessage());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("", "onComplete: ");
//
//                    }
//                });
//                        .subscribe(new Consumer<String>() {
//                            @Override
//                            public void accept(String s) {
//                                text.setText(s);
//                            }
//                        });

//
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//
////                emitter.onComplete();
////                emitter.onError(new Throwable());
//            }
//        });
//
//
//        Observable<Integer> observable1 = Observable.just(11);
//
//
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
////                d.dispose();
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//
//        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//
//
//        Flowable<Integer> flowable = new Flowable<Integer>() {
//            @Override
//            protected void subscribeActual(Subscriber<? super Integer> s) {
//                s.onNext(1);
//
//            }
//        };
//
//
//        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                s.cancel();
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        flowable.subscribe(subscriber);


//        getLocation();

    }


//    private boolean checkLocationPermission() {
//        if (Build.VERSION.SDK_INT >= 23 &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this, new String[]{
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION},
//                    1111);
//            return false;
//        }
//        return true;
//    }


//    private void getLocation() {
//        if (!checkLocationPermission())
//            return;
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setCostAllowed(true);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//        String provider = locationManager.getBestProvider(criteria, true);
//        locationManager.requestLocationUpdates(provider, 1000, 1, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.d("getLocation", "onLocationChanged: "+location.toString());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                Log.d("getLocation", "onStatusChanged: "+provider);
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//                Log.d("getLocation", "onProviderEnabled: "+provider);
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//                Log.d("getLocation", "onProviderDisabled: "+provider);
//            }
//        });
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1111)
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLocation();
//            }
//    }

//    public void toMovie(View view) {
//        startActivity(new Intent(this, MovieActivity.class));
//    }
}
