package com.example.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tv_result;
    FloatingActionButton fb_start;
    Handler handler = new Handler();
    List<Integer> mangSoNguyen = new ArrayList<>();
    List<Integer> mangChiaHet5 = new ArrayList<>();

    static String KEY_FIVE = "FIVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);
        fb_start = findViewById(R.id.fb_start);

        fb_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                for (int i = 0; i < 100; i++) {
                    mangSoNguyen.add(random.nextInt(500));
                }

                Log.d("TAG", "mangSoNguyen: " + mangSoNguyen.toString());
                soLe();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("TAG", "handleMessage: " + msg.getData().getInt(KEY_FIVE));
            }
        };
    }

    void tinhTong() {
    }

    void soLe() {
        Thread threadSoL = new Thread(new Runnable() {
            @Override
            public void run() {
                int dem = 0;
                for (int i = 0; i < mangSoNguyen.size(); i++) {
                    int currentNumber = mangSoNguyen.get(i);
                    if (isSoLe(currentNumber)) {
                        dem++;
                        if (currentNumber % 5 == 0) {
                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putInt(KEY_FIVE, currentNumber);
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }
                }
                Log.d("TAG", "soLe: " + dem);
            }
        });
        threadSoL.start();
        soChan();
    }

    void soChan() {
        Thread threadSoChan = new Thread(new Runnable() {
            @Override
            public void run() {
                int dem = 0;
                for (int i = 0; i < mangSoNguyen.size(); i++) {
                    int currentNumber = mangSoNguyen.get(i);
                    if (isSoLe(currentNumber)) {
                        dem++;
                        if (currentNumber % 5 == 0) {
                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putInt(KEY_FIVE, currentNumber);
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }

                }
                Log.d("TAG", "soChan: " + dem);
            }
        });
        threadSoChan.start();
    }

    boolean isSoLe(int number) {
        return number % 2 == 1;
    }
}