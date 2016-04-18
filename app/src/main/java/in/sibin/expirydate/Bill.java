package in.sibin.expirydate;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahammad on 16/04/16.
 */
public class Bill {

    String name, description, image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private static String sp = "sharedPreference";
    public static String spBills = "bills";

    public static void saveBills(Context context, String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPrefString(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }
}
