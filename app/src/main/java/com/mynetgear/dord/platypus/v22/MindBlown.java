package com.mynetgear.dord.platypus.v22;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mynetgear.dord.platypus.R;

import java.io.InputStream;


public class MindBlown extends View {

    private Movie gifMovie;
//    private int movieWidth, movieHeight;
    private long movieDuration;
    private long mMovieStart;

    public MindBlown(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setFocusable(true);
        InputStream gifInputStream = context.getResources()
                .openRawResource(R.raw.mind_blown);
        gifMovie = Movie.decodeStream(gifInputStream);
//        movieWidth = gifMovie.width();
//        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

//    public int getMovieWidth(){
//        return movieWidth;
//    }
//
//    public int getMovieHeight(){
//        return movieHeight;
//    }

    public long getMovieDuration(){
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }

        if (gifMovie != null) {
            int dur = gifMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }

            int relTime = (int)((now - mMovieStart) % dur);

            gifMovie.setTime(relTime);

            gifMovie.draw(canvas, 0, 0);
            invalidate();

        }
    }
}
