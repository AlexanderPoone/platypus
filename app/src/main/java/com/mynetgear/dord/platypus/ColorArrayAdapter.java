package com.mynetgear.dord.platypus;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorArrayAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private int[] code;


    public ColorArrayAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        setActivity((Activity) context);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.old_layout_color_dialog, parent, false);

        String[] hues = activity.getResources().getStringArray(R.array.colours);
        String[] x11 = activity.getResources().getStringArray(R.array.x11colors);
        code = activity.getResources().getIntArray(R.array.colorSystem);
        String strColor = String.format("#%06X", 0xFFFFFF & code[position]);
        TextView apex = (TextView) row.findViewById(R.id.colorTxt);
        apex.setText(hues[position]);
        TextView submarine = (TextView) row.findViewById(R.id.codeTxt);
        Typeface beaux = Typeface.createFromAsset(activity.getAssets(), "fonts/courbd.ttf");
        submarine.setTypeface(beaux);
        submarine.setText(strColor);
        TextView exp = (TextView) row.findViewById(R.id.exp);
        exp.setTypeface(beaux);
        exp.setText(x11[position]);
        if (x11[position].equals("")){
            exp.setVisibility(View.GONE);
        }
        ImageView hueField = (ImageView) row.findViewById(R.id.colorField);
        Drawable myDrawable = activity.getDrawable(R.drawable.color_rects);
        myDrawable.setColorFilter(code[position], PorterDuff.Mode.MULTIPLY);
        hueField.setImageDrawable(myDrawable);
        return row;
    }

    public int getCode(int pos) {
        return code[pos];
    }

}