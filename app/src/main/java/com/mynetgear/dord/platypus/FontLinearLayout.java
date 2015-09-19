package com.mynetgear.dord.platypus;

import android.app.Activity;
import android.content.Context;
//import android.content.res.ColorStateList;
import android.graphics.Typeface;
//import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mynetgear.dord.platypus.R;

import java.util.ArrayList;

/**
 * Created by Alexandre Poon on 15.08.29.
 */
public class FontLinearLayout extends LinearLayout {

    FontView fontView;

    public FontLinearLayout(Context context) {
        super(context);
        setOrientation(VERTICAL);
        TextView textView = new TextView(context);
        textView.setText(R.string.charset);
        textView.setPadding(30, 0, 0, 0);
        SpinnerCharset spinnerCharset = new SpinnerCharset(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(textView);
        linearLayout.addView(spinnerCharset);
        addView(linearLayout);
        fontView = new FontView(context);
        addView(fontView);
        spinnerCharset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(Integer.toString(position), "is selected");
                fontView.swap(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Typeface getSelectedTypeface() {
        Typeface typeface=fontView.getTypeface();
        return typeface;
    }

    private class SpinnerCharset extends Spinner {
        private Context context;

        public SpinnerCharset(Context context) {
            super(context);
            setLayoutMode(MODE_DROPDOWN);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(context.getString(R.string.latin));
            arrayList.add(context.getString(R.string.latinExt));
            arrayList.add(context.getString(R.string.cyrillic));
            arrayList.add(context.getString(R.string.greek));
            arrayList.add(context.getString(R.string.devanagari));
            arrayList.add(context.getString(R.string.japanese));
            arrayList.add(context.getString(R.string.chinese));
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setAdapter(dataAdapter);
        }

    }

    private class FontView extends RadioGroup {

        private Context context;

        public FontView(Context context) {
            super(context);
            setContext(context);
        }


        public void setContext(Context context) {
            this.context = context;
        }

        public void swap(int spinnerPos) {
            removeAllViews();
            String[] typefaceFiles;
            String[] typefaceNames;
            String regular;
            switch (spinnerPos) {
                case 0: //Latin
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFiles);
                    typefaceNames = context.getResources().getStringArray(R.array.typefaces);
                    regular = " Regular";
                    break;
                case 1: //Latin-Ext
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesEXT);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesEXT);
                    regular = " Regular";
                    break;
                case 2: //Cyrillic
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesCR);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesCR);
                    regular = " Регулярный";
                    break;
                case 3: //Greek
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesEL);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesEL);
                    regular = " Τακτική";
                    break;
                case 4: //Devangari
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesDV);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesDV);
                    regular = " नियमित";
                    break;
                case 5: //Japanese
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesJA);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesJA);
                    regular = " ヘビー";
//                    regular=" 標準";
                    break;
                case 6: //Chinese
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFilesZH);
                    typefaceNames = context.getResources().getStringArray(R.array.typefacesZH);
                    regular = " 標準";
                    break;
                default:
                    typefaceFiles = context.getResources().getStringArray(R.array.typefaceFiles);
                    typefaceNames = context.getResources().getStringArray(R.array.typefaces);
                    regular = " Regular";
                    break;
            }
            for (int iter = 0; iter < typefaceNames.length; iter++) {
//            RadioButton rb = new RadioButton(activity);
//            Typeface textyp = Typeface.createFromAsset(activity.getAssets(), "fonts/" + typefaceFiles[iter]);
//            rb.setTypeface(textyp);
//            rb.setTextColor(activity.getColor(R.color.Aubergine));
//            rb.setTextSize(19);
//            rb.setButtonTintList(ColorStateList.valueOf(activity.getColor(R.color.Olive))); //THEME NOT WORKING! BUG!
//            rb.setPadding(0, 18, 0, 18);
//            rb.setText(typefaceNames[iter] + " Regular");
//            addView(rb);
                RadioButton rb = new RadioButton(context);
                Typeface textyp = Typeface.createFromAsset(context.getAssets(), "fonts/" + typefaceFiles[iter]);
                rb.setTypeface(textyp);
//            rb.setTextColor(context.getColor(R.color.Black));
                rb.setTextSize(19);
//            rb.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.Olive))); //THEME NOT WORKING! BUG!
                rb.setPadding(0, 18, 0, 18);
                rb.setText(typefaceNames[iter] + regular);
                addView(rb);
            }
            ((RadioButton)getChildAt(0)).toggle();
        }

        @Override
        public int getCheckedRadioButtonId() {
            return super.getCheckedRadioButtonId();
        }

        public Typeface getTypeface() {
            Typeface typeface=((RadioButton) findViewById(getCheckedRadioButtonId())).getTypeface();
            return typeface;
        }

    }
}
