package com.jcdc.watchpace.targetingcomputer.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import androidx.core.content.ContextCompat;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceLView;
import com.jcdc.watchpace.targetingcomputer.R;
import com.jcdc.watchpace.targetingcomputer.data.DataType;
import com.jcdc.watchpace.targetingcomputer.data.TotalDistance;
import com.jcdc.watchpace.targetingcomputer.resource.ResourceManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class StarWarsTargetSportTotalDistanceWidget extends AbstractWidget {
    private TotalDistance total_distanceData;
    private Paint total_distancePaint;

    private Context context;

    // Constructor
    public StarWarsTargetSportTotalDistanceWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.total_distancePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.total_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.total_distancePaint.setTextSize(context.getResources().getDimension(R.dimen.star_wars_total_distance_font));
        this.total_distancePaint.setColor(ContextCompat.getColor(context, R.color.orange));
        this.total_distancePaint.setTextAlign(Paint.Align.CENTER);
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // total Sport's Distance class
        this.total_distanceData = (TotalDistance) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.TOTAL_DISTANCE);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.total_distanceData == null) {
            return;
        }

        // total Sport's Distance widget
        String units = "km";// if units are enabled

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        formatter.applyPattern("00000");

        String padded_distance = formatter.format(this.total_distanceData.getDistance());

        String text = padded_distance + units;
        canvas.drawText(
                text,
                context.getResources().getDimension(R.dimen.star_wars_total_distance_left),
                context.getResources().getDimension(R.dimen.star_wars_total_distance_top),
                this.total_distancePaint
        );
    }

    // Screen-off (SLPT)
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        int font_ratio = 90;

        SlptPictureView dot = new SlptPictureView();
        dot.setStringPicture(".");
        SlptPictureView kilometer = new SlptPictureView();
        kilometer.setStringPicture("km");

        SlptLinearLayout distance = new SlptLinearLayout();
        distance.add(new SlptTotalDistanceFView());
        distance.add(dot);
        distance.add(new SlptTotalDistanceLView());
        // Show or Not Units
        distance.add(kilometer);

        distance.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_total_distance_font),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL)
        );
        // Position based on screen on
        distance.alignX = 2;
        distance.alignY = 0;

        distance.setRect(
                (int) (2 * context.getResources().getDimension(R.dimen.star_wars_total_distance_left) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_total_distance_font))
        );
        distance.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.star_wars_total_distance_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_total_distance_font))
        );

        slpt_objects.add(distance);

        return slpt_objects;
    }
}
