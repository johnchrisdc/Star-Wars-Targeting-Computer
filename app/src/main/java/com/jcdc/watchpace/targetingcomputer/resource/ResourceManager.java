package com.jcdc.watchpace.targetingcomputer.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.io.ByteArrayOutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * Resource manager for caching purposes
 */
public class ResourceManager {

    public enum Font {
        FONT_FILE("fonts/font.ttf"), ROBOTO("fonts/roboto_bold.ttf"), STENCIL("fonts/RubikMonoOne-Regular.ttf");

        private final String path;

        Font(String path) {
            this.path = path;
        }
    }


    private static Map<Font, Typeface> TYPE_FACES = new EnumMap<>(Font.class);

    public static Typeface getTypeFace(final Resources resources, final Font font) {
        Typeface typeface = TYPE_FACES.get(font);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(resources.getAssets(), font.path);
            TYPE_FACES.put(font, typeface);
        }
        return typeface;
    }

    // This function can scale images for verge
    public static byte[] getVergeImageFromAssets(Boolean verge, Context var0, String var1) {
        byte[] file;
        if(!verge) {
            file = SimpleFile.readFileFromAssets(var0, var1);
        }else{
            Bitmap image = Util.decodeImage(var0.getResources(),"background.png");
            image = Bitmap.createScaledBitmap(image, 360, 360, false);
            file = getBytesFromBitmap(image);
        }
        return file;
    }

    // convert from bitmap to byte array
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
