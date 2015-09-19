package com.mynetgear.dord.platypus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.mynetgear.dord.platypus.db.DBDataSource;

import java.util.ArrayList;

/**
 * Created by Alexandre Poon on 15.09.13.
 */
public class CocktailMapFragment extends Fragment {

    private GoogleMap mMap;
    private static final LatLng INIT_LATLNG = new LatLng(34.591307, -29.369960);
    private static final float INIT_ZOOM = 0.2f;
    private static final int AREA_FILL_COLOUR = 0x66A4C639;
    private static DBDataSource DB_DATA_SOURCE;
    private static ArrayList<ArrayList<Double>> ENTRIES;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB_DATA_SOURCE = new DBDataSource(getActivity());
        DB_DATA_SOURCE.open();
        DB_DATA_SOURCE.createSailAway();
        ENTRIES = DB_DATA_SOURCE.findAllLatLng();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        DB_DATA_SOURCE.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_buccaneers, container, false);
        SupportMapFragment sMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap = sMapFragment.getMap();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(INIT_LATLNG, INIT_ZOOM);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        PolygonOptions polygonOptions = new PolygonOptions()
                .fillColor(AREA_FILL_COLOUR)
                .strokeWidth(8)
                .strokeColor(getActivity().getResources().getColor(R.color.OliveDrab));
        for (int found = 0; found < ENTRIES.size(); found++) {
            LatLng coordinates = new LatLng(ENTRIES.get(found).get(0), ENTRIES.get(found).get(1));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(coordinates)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pointer_hub));
            mMap.addMarker(markerOptions);
            polygonOptions.add(coordinates);
        }
        mMap.addPolygon(polygonOptions);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
//                            Log.i("Lat", Double.toString(marker.getPosition().latitude));
//                            Log.i("Lng", Double.toString(marker.getPosition().longitude));
                ArrayList<Object> details = DB_DATA_SOURCE.findOthersFromLatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                View v = getActivity().getLayoutInflater().inflate(R.layout.layout_buccaneers_snippet, null);
                TextView plcName = (TextView) v.findViewById(R.id.placeName);
                plcName.setText((String) details.get(0));
                TextView plcCity = (TextView) v.findViewById(R.id.placeOwner);
                plcCity.setText((String) details.get(1));
                StringBuilder Coors = new StringBuilder();
                //Break
                double latDou = marker.getPosition().latitude;
                int latDec = Math.abs((int) latDou);
                Coors.append(latDec);
                Coors.append("°");
                int latMin = (int) ((Math.abs(latDou) - latDec) * 60);
                Coors.append(latMin);
                Coors.append("'");
                int latSec = (int) (((Math.abs(latDou) - latDec) * 60 - latMin) * 60);
                Coors.append(latSec);
                Coors.append("\"");
                if (latDou >= 0) {
                    Coors.append("N ");
                } else {
                    Coors.append("S ");
                }
                //Break
                double lngDou = marker.getPosition().longitude;
                int lngDec = Math.abs((int) lngDou);
                Coors.append(lngDec);
                Coors.append("°");
                int lngMin = (int) ((Math.abs(lngDou) - lngDec) * 60);
                Coors.append(lngMin);
                Coors.append("'");
                int lngSec = (int) (((Math.abs(lngDou) - lngDec) * 60 - lngMin) * 60);
                Coors.append(lngSec);
                Coors.append("\"");
                if (lngDou >= 0) {
                    Coors.append("E");
                } else {
                    Coors.append("W");
                }
                //Break
                TextView plcLl = (TextView) v.findViewById(R.id.placeLat);
                plcLl.setText(Coors);
                RatingBar ratingBar = (RatingBar) v.findViewById(R.id.awesomeness);
                ratingBar.setRating((Float) details.get(2));
                TextView ratingText = (TextView) v.findViewById(R.id.awesomenessText);
                ratingText.setText(details.get(2).toString());
                ImageView plcPic = (ImageView) v.findViewById(R.id.sneakPeek);
                int res = getActivity().getResources().getIdentifier(details.get(3).toString(), "drawable", getActivity().getPackageName());
                plcPic.setImageResource(res);
                TextView plcAmenities = (TextView) v.findViewById(R.id.placeLng);
                plcAmenities.setText(details.get(4).toString());
                TextView plcDesc = (TextView) v.findViewById(R.id.placeDesc);
                plcDesc.setText(details.get(5).toString());
                ImageView plcFlag = (ImageView) v.findViewById(R.id.flagSprite);
                int flagicon = getActivity().getResources().getIdentifier(details.get(6).toString(), "drawable", getActivity().getPackageName());
                plcFlag.setImageResource(flagicon);
                return v;
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String found = DB_DATA_SOURCE.findArticleFromLatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                TextView articleView = (TextView) rootView.findViewById(R.id.introduction);
                articleView.setText(Html.fromHtml(found));
                return false;
            }
        });
        return rootView;
    }
}
