package com.example.simonsays;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FlyingSimon extends View {

    private Bitmap simon;
    int direction_x_balloon, direction_y_balloon;
    int xAxis_balloon, yAxis_balloon;

    public FlyingSimon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        simon = BitmapFactory.decodeResource(getResources(),R.drawable.simon_says_icon);
        direction_x_balloon =3;
        direction_y_balloon =3;
        xAxis_balloon =630;
        yAxis_balloon =630;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        //bottom
        if(yAxis_balloon >=canvas.getHeight()-320)
        {
            direction_y_balloon =-3;
        }
        //upper
        if(yAxis_balloon <=-10)
        {
            direction_y_balloon =3;
        }
        //right side
        if(xAxis_balloon>=canvas.getWidth()-320)
        {
            direction_x_balloon=-5;
        }
        //left side
        if(xAxis_balloon<-6)
        {
            direction_x_balloon=5;
        }

        // Update balloon position according to the speed (direction)
        xAxis_balloon += direction_x_balloon;
        yAxis_balloon += direction_y_balloon;
        canvas.drawBitmap(simon, xAxis_balloon, yAxis_balloon,paint);
        invalidate();

    }

}
