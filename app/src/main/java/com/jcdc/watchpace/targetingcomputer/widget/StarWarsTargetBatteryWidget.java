package com.jcdc.watchpace.targetingcomputer.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.jcdc.watchpace.targetingcomputer.R;
import com.jcdc.watchpace.targetingcomputer.data.Battery;
import com.jcdc.watchpace.targetingcomputer.data.DataType;
import com.jcdc.watchpace.targetingcomputer.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StarWarsTargetBatteryWidget extends AbstractWidget {
    private Context context;

    private Battery batteryData;
    private Paint batteryPaint;

    private Service mService;

    // Constructor
    public StarWarsTargetBatteryWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        // Battery percent element
        this.batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.batteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.batteryPaint.setTextSize(context.getResources().getDimension(R.dimen.star_wars_widgets_font));
        this.batteryPaint.setColor(Color.WHITE);
        this.batteryPaint.setTextAlign(Paint.Align.CENTER);
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Battery class
        this.batteryData = (Battery) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.BATTERY);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        Paint mGPaint = new Paint();
        mGPaint.setAntiAlias(false);
        mGPaint.setFilterBitmap(false);

        if (this.batteryData == null) {
            return;
        }


        String text = Integer.toString(this.batteryData.getLevel() * 100 / this.batteryData.getScale());
        canvas.drawText(
                text,
                context.getResources().getDimension(R.dimen.star_wars_battery_left),
                context.getResources().getDimension(R.dimen.star_wars_battery_top),
                batteryPaint
        );
    }

    // Screen-off (SLPT)
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        int font_ratio = 90;

        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        int tmp_left;

        SlptLinearLayout power = new SlptLinearLayout();

        power.add(new SlptPowerNumView());
        power.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_widgets_font),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL)
        );
        // Position based on screen on
        power.alignX = 2;
        power.alignY = 0;

        power.setRect(
                (int) (2 * context.getResources().getDimension(R.dimen.star_wars_battery_left) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_widgets_font))
        );
        power.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.star_wars_battery_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_widgets_font))
        );

        slpt_objects.add(power);

        return slpt_objects;
    }
}
