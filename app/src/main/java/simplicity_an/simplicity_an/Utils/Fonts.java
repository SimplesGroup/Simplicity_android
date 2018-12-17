package simplicity_an.simplicity_an.Utils;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {
    public Typeface play;
    Context context;
    public static final String FONT= "font";
    Typeface tf,tf1;
public static String playfairfont="fonts/playfairDisplayRegular.ttf";
    public static String sanfranciscobold="fonts/Oxygen-Bold.ttf";
    public static String sanfranciscoregular="fonts/SystemSanFranciscoDisplayRegular.ttf";
    public static String muktamalar="fonts/MUKTAMALAR-BOLD.TTF";

    public Typeface font(String s, Context context){
        if(s.equals("playfair")){
            tf=Typeface.createFromAsset(context.getAssets(),playfairfont);

        }else {
            tf=Typeface.createFromAsset(context.getAssets(),sanfranciscobold);
            font1(context);
        }

        return tf;
    }
    public Typeface font1(Context context){
        tf1=Typeface.createFromAsset(context.getAssets(),sanfranciscoregular);
        return tf1;
    }

}
