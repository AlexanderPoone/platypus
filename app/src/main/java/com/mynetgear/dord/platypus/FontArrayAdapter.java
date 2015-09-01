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
import android.widget.RadioButton;
import android.widget.TextView;

public class FontArrayAdapter extends ArrayAdapter<String> {

    private Activity activity;


    public FontArrayAdapter(Context context, int textViewResourceId, String[] objects) {
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
        View row = inflater.inflate(R.layout.fontlayout, parent, false);
//        RadioButton radioButton=(RadioButton) row.findViewById(R.id.typRadio);
        String[] typefaceFiles=activity.getResources().getStringArray(R.array.typefaceFiles);
        String[] typefaceNames=activity.getResources().getStringArray(R.array.typefaces);
        Typeface textyp = Typeface.createFromAsset(activity.getAssets(), "fonts/"+typefaceFiles[position]);
//        radioButton.setText(typefaceNames[position]+" Regular");
//        radioButton.setTypeface(textyp);
        return row;
    }
}