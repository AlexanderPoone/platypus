package com.mynetgear.dord.platypus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alexandre Poon on 15.08.25.
 */
public class DBDataSource {
    private static final String LOGTAG = "DataSource";
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> names;
    ArrayList<String> cities;
    ArrayList<Double> lats;
    ArrayList<Double> lngs;
    ArrayList<Float> ratings;
    ArrayList<String> picfilenames;
    ArrayList<String> amenities;
    ArrayList<String> descs;
    ArrayList<String> isos;
    ArrayList<String> articles;

    public DBDataSource(Context context) {
        sqLiteOpenHelper = new DBOpenHelper(context);
        names = new ArrayList<>();
        names.add("Buccaneers' Hideout");
        names.add("Piñita Colada Shores");
        names.add("Negg Cavern");
        names.add("Hueland");
        cities = new ArrayList<>();
        cities.add("Willemstad, Curaçao");
        cities.add("San Juan, Puerto Rico");
        cities.add("Rotterdam, the Netherlands");
        cities.add("São José dos Campos, Brazil");
        lats = new ArrayList<>();
        lats.add(12.118823);
        lats.add(18.463505);
        lats.add(51.923506);
        lats.add(-23.186389);
        lngs = new ArrayList<>();
        lngs.add(-68.966506);
        lngs.add(-66.085563);
        lngs.add(4.477071);
        lngs.add(-45.878611);
        ratings = new ArrayList<>();
        ratings.add(4.7f);
        ratings.add(4.9f);
        ratings.add(2.2f);
        ratings.add(3.6f);
        picfilenames = new ArrayList<>();
        picfilenames.add("blue_bayou");
        picfilenames.add("pina_colada");
        picfilenames.add("bubbly_advocaat");
        picfilenames.add("caipirinha");
        amenities = new ArrayList<>();
        amenities.add("Unspoiled beaches, Curaçao liqueur fountains");
        amenities.add("Quaint tavern, 10+ variants of piña colada");
        amenities.add("Wine cellar, herb garden");
        amenities.add("Café that offers one of the best brigadeiros in the world");
        descs = new ArrayList<>();
        descs.add("Arrr, ye scurvy dogs!");
        descs.add("\"If you like Pina Coladas, and getting caught in the rain...\"");
        descs.add("Eww... Eggs are disgusting.");
        descs.add("New Portugal.");
        isos = new ArrayList<>();
        isos.add("cw");
        isos.add("pr");
        isos.add("nl");
        isos.add("br");
        articles = new ArrayList<>();
        articles.add("<p><b>Curaçao</b> (/'kjʊərəsaʊ/ <i>KEWR-ə-sow</i>) is a liqueur flavored with the dried peel of the laraha citrus fruit, grown on the island of Curaçao. A non-native plant similar to an orange, the laraha developed from the sweet Valencia orange transplanted by Spanish explorers. The nutrient-poor soil and arid climate of Curaçao proved unsuitable to Valencia cultivation, resulting in small, bitter fruit of the trees. Although the bitter flesh of the Laraha is all but inedible, the peels are aromatic and flavorful, maintaining much of the essence of the Valencia orange.</p><p>Curaçao liqueur was first developed and marketed by the Senior family in the 19th century. To create the liqueur the laraha peel is dried, bringing out the sweetly fragranced oils. After soaking in a still with alcohol and water for several days, the peel is removed and other spices are added. The liqueur has an orange-like flavor with varying degrees of bitterness. It is naturally colorless, but is often given artificial coloring, most commonly blue or orange, which confers an exotic appearance to cocktails and other mixed drinks. Blue color is achieved by adding a food colorant, most often E133 Brilliant Blue.</p>");
        articles.add("<p>The <b>piña colada</b> (/ˌpiːnəkɵˈnjəːdə/; Spanish: <i>piña</i>, pineapple and <i>colada</i>, strained) is a sweet cocktail made with rum, coconut cream, and pineapple juice, usually served either blended or shaken with ice. It may be garnished with a pineapple wedge, a maraschino cherry, or both. The piña colada has been the national drink of Puerto Rico since 1978.</p>");
        articles.add("<p><b>Advocaat</b> is a traditional Dutch alcoholic beverage made from eggs, sugar and brandy. The rich and creamy drink has a smooth, custard-like flavor and is similar to eggnog. The typical alcohol content is generally somewhere between 14% and 20% ABV. Its contents may be a blend of egg yolks, aromatic spirits, sugar or honey, brandy, vanilla and sometimes cream (or evaporated milk).</p>");
        articles.add("<p><b>Caipirinha</b> (/kajpiˈɾĩj̃ɐ/) is Brazil's national cocktail, made with cachaça (/kaˈʃasɐ/; sugar cane hard liquor), sugar and lime. Cachaça is Brazil's most common distilled alcoholic beverage (also known as Pinga or Caninha). Although both rum and cachaça are made from sugarcane-derived products, specifically in cachaça, the alcohol results from the fermentation of fresh sugarcane juice that is afterwards distilled, while rum is usually made from by-products from refineries, such as molasses.</p><p>The drink is prepared by smashing the fruit and the sugar together, and adding the liquor. This can be made into a single glass, usually large, that can be shared amongst people, or into a larger jar, from where it is served in individual glasses.</p>");
    }

    public void open() {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteOpenHelper.close();
    }

    public void createSailAway() {
        Cursor mcursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM " + DBOpenHelper.TABLE_SAILAWAY, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount == 0) {
            for (int iter = 0; iter < cities.size(); iter++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBOpenHelper.COLUMN_NAME, names.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_CITY, cities.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_LAT, lats.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_LNG, lngs.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_RATING, ratings.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_PICFILENAME, picfilenames.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_AMENITIES, amenities.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_DESC, descs.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_ISO, isos.get(iter));
                contentValues.put(DBOpenHelper.COLUMN_ARTICLE, articles.get(iter));
                sqLiteDatabase.insert(DBOpenHelper.TABLE_SAILAWAY, null, contentValues);
            }
        }
    }

    public ArrayList<ArrayList<Object>> findAllSailAway() {
        ArrayList<ArrayList<Object>> BiggerAL = new ArrayList<>();
        Cursor acursor = sqLiteDatabase.query(DBOpenHelper.TABLE_SAILAWAY, DBOpenHelper.COLUMNS, null, null, null, null, null);
        if (acursor.getCount() > 0) {
            while (acursor.moveToNext()) {
                ArrayList<Object> SmallerAL = new ArrayList<>();
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_CITY)));
                SmallerAL.add(acursor.getDouble(acursor.getColumnIndex(DBOpenHelper.COLUMN_LAT)));
                SmallerAL.add(acursor.getDouble(acursor.getColumnIndex(DBOpenHelper.COLUMN_LNG)));
                SmallerAL.add(acursor.getFloat(acursor.getColumnIndex(DBOpenHelper.COLUMN_RATING)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_PICFILENAME)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_AMENITIES)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_DESC)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_ISO)));
                SmallerAL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_ARTICLE)));

                BiggerAL.add(SmallerAL);
            }
        }
        //Log.i(LOGTAG, BiggerAL.toString());
        return BiggerAL;
    }

    public ArrayList<ArrayList<Double>> findAllLatLng() {
        ArrayList<ArrayList<Double>> BiggerAL = new ArrayList<>();
        Cursor acursor = sqLiteDatabase.rawQuery("SELECT " + DBOpenHelper.COLUMN_LAT + " , " +
                DBOpenHelper.COLUMN_LNG + " FROM " + DBOpenHelper.TABLE_SAILAWAY, null);
        if (acursor.getCount() > 0) {
            while (acursor.moveToNext()) {
                ArrayList<Double> SmallerAL = new ArrayList<>();
                SmallerAL.add(acursor.getDouble(acursor.getColumnIndex(DBOpenHelper.COLUMN_LAT)));
                SmallerAL.add(acursor.getDouble(acursor.getColumnIndex(DBOpenHelper.COLUMN_LNG)));
                BiggerAL.add(SmallerAL);
            }
        }
        Log.i(LOGTAG, BiggerAL.toString());
        return BiggerAL;
    }

    public ArrayList<Object> findOthersFromLatLng(Double lat, Double lng) {
        //EXCEPT ARTICLE
        ArrayList<Object> AL = new ArrayList<>();
        Cursor acursor = sqLiteDatabase.rawQuery("SELECT " + DBOpenHelper.COLUMN_NAME + " , " +
                DBOpenHelper.COLUMN_CITY + " , " +
                DBOpenHelper.COLUMN_RATING + " , " +
                DBOpenHelper.COLUMN_PICFILENAME + " , " +
                DBOpenHelper.COLUMN_AMENITIES + " , " +
                DBOpenHelper.COLUMN_DESC + " , " +
                DBOpenHelper.COLUMN_ISO + " FROM " + DBOpenHelper.TABLE_SAILAWAY +
                " WHERE " + DBOpenHelper.COLUMN_LAT + " = " + Double.toString(lat) +
                " AND " + DBOpenHelper.COLUMN_LNG + " = " + Double.toString(lng), null);
        if (acursor.getCount() > 0) {
            acursor.moveToFirst();
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_NAME)));
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_CITY)));
            AL.add(acursor.getFloat(acursor.getColumnIndex(DBOpenHelper.COLUMN_RATING)));
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_PICFILENAME)));
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_AMENITIES)));
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_DESC)));
            AL.add(acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_ISO)));
        }
        return AL;
    }

    public String findArticleFromLatLng(Double lat, Double lng) {
        String target;
        Cursor acursor = sqLiteDatabase.rawQuery("SELECT " +
                DBOpenHelper.COLUMN_ARTICLE + " FROM " + DBOpenHelper.TABLE_SAILAWAY +
                " WHERE " + DBOpenHelper.COLUMN_LAT + " = " + Double.toString(lat) +
                " AND " + DBOpenHelper.COLUMN_LNG + " = " + Double.toString(lng), null);
        if (acursor.getCount() > 0) {
            acursor.moveToFirst();
            target=acursor.getString(acursor.getColumnIndex(DBOpenHelper.COLUMN_ARTICLE));
        }
        else
        {
            target="Not found";
        }
        return target;
    }
}