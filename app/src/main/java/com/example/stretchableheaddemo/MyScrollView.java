package com.example.stretchableheaddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/10/10.
 */

public class MyScrollView extends ScrollView {

    private float yDown;
    private float yMove;
    private float yUp;
    public DisplayMetrics dm;

    SetHeightListener setHeightListener;
    private float y1;
    private float y3;

    public MyScrollView(Context context) {
        super(context);
        getScreenWidth(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenWidth(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public  void getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public void getListener(SetHeightListener setHeightListener){
        this.setHeightListener=setHeightListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                yDown = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = ev.getY();
                y1 = yMove - yDown;
                if(y1 >0){
                    if(y1 <=300){

                        y3 = y1;
                        setHeightListener.setHeight(y1,false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                yUp = ev.getY();
                float y2 = yUp - yDown;
                if(y2>0){
                    setHeightListener.setHeight(y3,true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface SetHeightListener{
        void setHeight(float height,boolean flag);
    }
}
