package com.jcdc.watchpace.targetingcomputer.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayStepNumView;
import com.jcdc.watchpace.targetingcomputer.R;
import com.jcdc.watchpace.targetingcomputer.data.DataType;
import com.jcdc.watchpace.targetingcomputer.data.Steps;
import com.jcdc.watchpace.targetingcomputer.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StarWarsStepsWidget extends AbstractWidget {
    private Steps stepsData;
    private Paint stepsPaint;

    private Context context;

    // Constructor
    public StarWarsStepsWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.stepsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.stepsPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.stepsPaint.setTextSize(context.getResources().getDimension(R.dimen.star_wars_steps_font));
        this.stepsPaint.setColor(Color.WHITE);
        this.stepsPaint.setTextAlign(Paint.Align.CENTER);
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Steps class
        this.stepsData = (Steps) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.STEPS);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.stepsData == null) {
            return;
        }

        String formatted = String.format("%05d", this.stepsData.getSteps());

        canvas.drawText(
                formatted,
                context.getResources().getDimension(R.dimen.star_wars_steps_left),
                context.getResources().getDimension(R.dimen.star_wars_steps_top),
                stepsPaint
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

        SlptLinearLayout steps = new SlptLinearLayout();
        steps.add(new SlptTodayStepNumView());
        steps.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_steps_font),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL)
        );
        // Position based on screen on
        steps.alignX = 2;
        steps.alignY = 0;

        steps.setRect(
                (int) (2 * context.getResources().getDimension(R.dimen.star_wars_steps_left) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_steps_font))
        );
        steps.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.star_wars_steps_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_steps_font))
        );

        slpt_objects.add(steps);

        return slpt_objects;
    }
}
