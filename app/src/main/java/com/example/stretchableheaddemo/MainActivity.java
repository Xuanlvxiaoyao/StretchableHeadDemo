package com.example.stretchableheaddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyScrollView.SetHeightListener{
    MyScrollView myScrollView;
    ListViewForScrollView listViewForScrollView;
    ImageView iv;
    ArrayAdapter arrayAdapter;
    List<String> list;
    private float oldHeight;
    private float oldWeight;

    private float newHeight;
    private float newWeight;
    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        initdata();
    }

    private void initview() {
        iv= (ImageView) findViewById(R.id.iv);
        myScrollView= (MyScrollView) findViewById(R.id.sv);
        listViewForScrollView= (ListViewForScrollView) findViewById(R.id.lv);

        myScrollView.smoothScrollTo(0,0);

        myScrollView.getListener(this);
    }

    private void initdata() {
        list=new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add("item"+i);
        }

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listViewForScrollView.setAdapter(arrayAdapter);

        layoutParams = iv.getLayoutParams();
        oldHeight = layoutParams.height;
        oldWeight = layoutParams.width;
    }

    @Override
    public void setHeight(final float height,boolean flag) {
        newHeight=oldHeight+height;
        newWeight=myScrollView.dm.widthPixels+height*0.3f;

        layoutParams.height= (int) newHeight;
        layoutParams.width= (int) newWeight;
        iv.setLayoutParams(layoutParams);

        if(flag){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (newHeight>oldHeight){
                            Thread.sleep(10);
                            newHeight-=10;
                            newWeight-=3;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(newHeight>oldHeight){
                                        layoutParams.height= (int) newHeight;
                                        layoutParams.width= (int) newWeight;
                                        iv.setLayoutParams(layoutParams);
                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
