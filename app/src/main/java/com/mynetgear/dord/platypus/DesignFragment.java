package com.mynetgear.dord.platypus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;

/**
 * Created by Alexandre Poon on 15.09.13.
 */
public class DesignFragment extends Fragment {

    private Button button, anotherButton;
    private AlertDialog.Builder builder, anotherBuilder;

    public class PatternThread extends Thread {
        private View rootView;

        public PatternThread(View view) {
            rootView = view;
        }

        @Override
        public void run() {
            Drawable d = getActivity().getResources().getDrawable(R.drawable.polka_dots);
            Bitmap src = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true); //Must copy the original to make it mutable
            for (int x = 0; x < src.getWidth(); x++) {
                for (int y = 0; y < src.getHeight(); y++) {
                    if (Color.red(src.getPixel(x, y)) == 0x32 &&
                            Color.green(src.getPixel(x, y)) == 0x34 &&
                            Color.blue(src.getPixel(x, y)) == 0x34) {
                        src.setPixel(x, y, getActivity().getResources().getColor(R.color.Azure));
                    } else if (Color.red(src.getPixel(x, y)) == 0x66 &&
                            Color.green(src.getPixel(x, y)) == 0x67 &&
                            Color.blue(src.getPixel(x, y)) == 0x67) {
                        src.setPixel(x, y, getActivity().getResources().getColor(R.color.Violet));
                    } else if (Color.red(src.getPixel(x, y)) == 0x99 &&
                            Color.green(src.getPixel(x, y)) == 0x99 &&
                            Color.blue(src.getPixel(x, y)) == 0x99) {
                        src.setPixel(x, y, getActivity().getResources().getColor(R.color.Goldenrod));
                    } else {
                        src.setPixel(x, y, getActivity().getResources().getColor(R.color.ForestGreen));
                    }

                }
            }
            BitmapDrawable backdrop = new BitmapDrawable(getActivity().getResources(), src);
            backdrop.setAntiAlias(true);
            backdrop.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT); //Look, it's the BitmapDrawable, not rootView
            rootView.setBackground(backdrop);
        }
    }

    public class PatternCloudsThread extends Thread {
        private View rootView;

        public PatternCloudsThread(View view) {
            rootView = view;
        }

        @Override
        public void run() {
            SVG svgPattern;
            Bitmap src;
            try {
                svgPattern = SVG.getFromAsset(getActivity().getAssets(), "pattern_clouds.svg");
                src = Bitmap.createBitmap(450,450, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(src);
                svgPattern.renderToCanvas(canvas);
//            Drawable d = getActivity().getResources().getDrawable(R.drawable.clouds);
//            Bitmap src = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true); //Must copy the original to make it mutable
                for (int x = 0; x < src.getWidth(); x++) {
                    for (int y = 0; y < src.getHeight(); y++) {
                        if (Color.red(src.getPixel(x, y)) == 0x08) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Thistle));
                        } else if (Color.red(src.getPixel(x, y)) == 0x32) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.MidnightBlue));
                        } else if (Color.red(src.getPixel(x, y)) == 0x65) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.BondiBlue));
                        } else if (Color.red(src.getPixel(x, y)) == 0x99) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.SteelBlue));
                        } else {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Mauve));
                        }

                    }
                }
                BitmapDrawable backdrop = new BitmapDrawable(getActivity().getResources(), src);
                backdrop.setAntiAlias(true);
                backdrop.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT); //Look, it's the BitmapDrawable, not rootView
                rootView.setBackground(backdrop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class PatternCloudsThread2 extends Thread {
        private View rootView;

        public PatternCloudsThread2(View view) {
            rootView = view;
        }

        @Override
        public void run() {
            SVG svgPattern;
            Bitmap src;
            try {
                svgPattern = SVG.getFromAsset(getActivity().getAssets(), "pattern_embroidery.svg");
                src = Bitmap.createBitmap(109*2,96*2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(src);
                svgPattern.renderToCanvas(canvas);
//            Drawable d = getActivity().getResources().getDrawable(R.drawable.clouds);
//            Bitmap src = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true); //Must copy the original to make it mutable
                for (int x = 0; x < src.getWidth(); x++) {
                    for (int y = 0; y < src.getHeight(); y++) {
                        if (Color.red(src.getPixel(x, y)) == 0x07) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.OliveDrab));
                        } else if (Color.red(src.getPixel(x, y)) == 0x39) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Goldenrod));
                        } else if (Color.red(src.getPixel(x, y)) == 0x68) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Orchid));
                        } else if (Color.red(src.getPixel(x, y)) == 0x9B) {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Thistle));
                        } else {
                            src.setPixel(x, y, getActivity().getResources().getColor(R.color.Magnolia));
                        }

                    }
                }
                BitmapDrawable backdrop = new BitmapDrawable(getActivity().getResources(), src);
                backdrop.setAntiAlias(true);
                backdrop.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT); //Look, it's the BitmapDrawable, not rootView
                rootView.setBackground(backdrop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class TypeTwoThread extends Thread {
        private View rootView;

        public TypeTwoThread(View view) {
            rootView = view;
        }

        @Override
        public void run() {
            button = (Button) rootView.findViewById(R.id.butt);
            String[] pHues = getActivity().getResources().getStringArray(R.array.colours);
            final TextView pholderText = (TextView) rootView.findViewById(R.id.ver);
            final ColorArrayAdapter listAdapter = new ColorArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, pHues);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.coloursTitle)
                    .setIcon(R.mipmap.ic_color_lens_white_24dp)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setAdapter(listAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pholderText.setTextColor(listAdapter.getCode(which));
                            dialog.cancel();
                        }
                    });
            //
            anotherButton = (Button) rootView.findViewById(R.id.spinButt);
            final FontLinearLayout daLayout = new FontLinearLayout(new ContextThemeWrapper(getActivity(), R.style.PeriodoAzul_Light));
            anotherBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Material_Light_Dialog));
            anotherBuilder.setView(daLayout)
                    .setTitle(R.string.font)
                    .setIcon(R.mipmap.ic_font_download_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Typeface typ = daLayout.getSelectedTypeface();
                            pholderText.setTypeface(typ);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
//            Button patternButt = (Button) rootView.findViewById(R.id.patternButt);
//            patternButt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PatternThread patternThread = new PatternThread(rootView);
//                    patternThread.start();
//                }
//            });
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_hermoso, container, false);
        PatternCloudsThread patternThread = new PatternCloudsThread(rootView);
        TypeTwoThread typeTwoThread = new TypeTwoThread(rootView);
        patternThread.start();
        typeTwoThread.start();
        try {
            patternThread.join();
            typeTwoThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Dialog realDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realDialog.show();
            }
        });
        final Dialog fontDialog = anotherBuilder.create();
        anotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontDialog.show();
            }
        });
        return rootView;
    }
}
