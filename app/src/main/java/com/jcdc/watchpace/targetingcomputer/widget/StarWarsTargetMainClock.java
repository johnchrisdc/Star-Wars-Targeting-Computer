package com.jcdc.watchpace.targetingcomputer.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.digital.SlptDayHView;
import com.ingenic.iwds.slpt.view.digital.SlptDayLView;
import com.ingenic.iwds.slpt.view.digital.SlptHourHView;
import com.ingenic.iwds.slpt.view.digital.SlptHourLView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteHView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteLView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthHView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthLView;
import com.ingenic.iwds.slpt.view.digital.SlptYear0View;
import com.ingenic.iwds.slpt.view.digital.SlptYear1View;
import com.ingenic.iwds.slpt.view.digital.SlptYear2View;
import com.ingenic.iwds.slpt.view.digital.SlptYear3View;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;
import com.jcdc.watchpace.targetingcomputer.R;
import com.jcdc.watchpace.targetingcomputer.resource.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class StarWarsTargetMainClock extends DigitalClockWidget {

    private Context context;

    private TextPaint hourFont;
    private TextPaint minutesFont;
    private TextPaint secondsFont;
    private TextPaint dateFont;

    private Bitmap background;

    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private Service service;

    public StarWarsTargetMainClock(Context context) {
        this.context = context;
    }

    @Override
    public void init(Service service) {
        this.service = service;
        this.background = Util.decodeImage(service.getResources(), "background_target_0.png");

        this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.hourFont.setTextSize(context.getResources().getDimension(R.dimen.star_wars_font_size));
        this.hourFont.setColor(Color.WHITE);
        this.hourFont.setTextAlign(Paint.Align.RIGHT);

        this.secondsFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.secondsFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.secondsFont.setTextSize(context.getResources().getDimension(R.dimen.star_wars_font_size));
        this.secondsFont.setColor(Color.WHITE);
        this.secondsFont.setTextAlign(Paint.Align.CENTER);

        this.minutesFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.minutesFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.minutesFont.setTextSize(context.getResources().getDimension(R.dimen.star_wars_font_size));
        this.minutesFont.setColor(Color.WHITE);
        this.minutesFont.setTextAlign(Paint.Align.LEFT);

        this.dateFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.dateFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL));
        this.dateFont.setTextSize(context.getResources().getDimension(R.dimen.star_wars_date_font_size));
        this.dateFont.setColor(Color.WHITE);
        this.dateFont.setTextAlign(Paint.Align.CENTER);
    }

    // Screen open watch mode
    @Override
    public void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
        // Draw background image
        //this.background.draw(canvas);


        Paint mGPaint = new Paint();
        mGPaint.setAntiAlias(false);
        mGPaint.setFilterBitmap(false);

        if (seconds % 2 == 0) {
            this.background = Util.decodeImage(this.service.getResources(), "background_target_0.png");
        } else {
            this.background = Util.decodeImage(this.service.getResources(), "background_target_1.png");
        }
        canvas.drawBitmap(this.background, 0f, 0f, mGPaint);

        // Draw hours
        canvas.drawText(
                Util.formatTime(hours),
                context.getResources().getDimension(R.dimen.star_wars_time_hour_left),
                context.getResources().getDimension(R.dimen.star_wars_time_top),
                this.hourFont
        );

        // Draw seconds
        String indicator = ":";
        canvas.drawText(
                indicator,
                context.getResources().getDimension(R.dimen.star_wars_time_second_left),
                context.getResources().getDimension(R.dimen.star_wars_time_seconds_top),
                this.secondsFont
        );

        // Draw minutes
        canvas.drawText(
                Util.formatTime(minutes),
                context.getResources().getDimension(R.dimen.star_wars_time_minutes_left),
                context.getResources().getDimension(R.dimen.star_wars_time_top),
                this.minutesFont
        );

        // Draw date
        String date = Util.formatTime(month) + "-" + Util.formatTime(day) + "-" + year;
        canvas.drawText(
                date,
                context.getResources().getDimension(R.dimen.star_wars_date_left),
                context.getResources().getDimension(R.dimen.star_wars_date_top),
                this.dateFont
        );
    }


    // Screen locked/closed watch mode (Slpt mode)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        int font_ratio = 90;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Draw background image
        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service, "background_target_0.png"));

        slpt_objects.add(background);

        // Set font
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.STENCIL);

        // Draw hours
        SlptLinearLayout hourLayout = new SlptLinearLayout();
        hourLayout.add(new SlptHourHView());
        hourLayout.add(new SlptHourLView());
        hourLayout.setStringPictureArrayForAll(this.digitalNums);

        hourLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_font_size),
                Color.WHITE,
                timeTypeFace
        );
        // Position based on screen on
        hourLayout.alignX = 2;
        hourLayout.alignY = 0;
        hourLayout.setRect(
                (int) (2 * (context.getResources().getDimension(R.dimen.star_wars_time_hour_left_slpt)) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );
        hourLayout.setStart(
                -255,
                (int) (context.getResources().getDimension(R.dimen.star_wars_time_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );
        //Add it to the list
        slpt_objects.add(hourLayout);

        // Draw minutes
        SlptLinearLayout minuteLayout = new SlptLinearLayout();
        minuteLayout.add(new SlptMinuteHView());
        minuteLayout.add(new SlptMinuteLView());
        minuteLayout.setStringPictureArrayForAll(this.digitalNums);
        minuteLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_font_size),
                Color.WHITE,
                timeTypeFace
        );
        // Position based on screen on
        minuteLayout.alignX = 2;
        minuteLayout.alignY = 0;
        minuteLayout.setRect(
                (int) (2 * (context.getResources().getDimension(R.dimen.star_wars_time_minutes_left_slpt)) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );
        minuteLayout.setStart(
                -255,
                (int) (context.getResources().getDimension(R.dimen.star_wars_time_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );

        //Add it to the list
        slpt_objects.add(minuteLayout);


        //Seconds
        SlptLinearLayout indicatorLayout = new SlptLinearLayout();
        SlptPictureView colon = new SlptPictureView();
        colon.setStringPicture(":");
        indicatorLayout.add(colon);
        indicatorLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_font_size),
                Color.WHITE,
                timeTypeFace
        );
        // Position based on screen on
        indicatorLayout.alignX = 2;
        indicatorLayout.alignY = 0;
        indicatorLayout.setRect(
                (int) (2 * context.getResources().getDimension(R.dimen.star_wars_time_second_left_slpt) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );
        indicatorLayout.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.star_wars_time_seconds_top_slpt) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_font_size))
        );
        //Add it to the list
        slpt_objects.add(indicatorLayout);


        //Date
        SlptLinearLayout dateLayout = new SlptLinearLayout();

        SlptLinearLayout separatorLayout = new SlptLinearLayout();
        SlptPictureView separator = new SlptPictureView();
        separator.setStringPicture("-");

        separatorLayout.add(separator);
        separatorLayout.setTextAttr(
                context.getResources().getDimension(R.dimen.star_wars_font_size),
                Color.WHITE,
                timeTypeFace
        );

        SlptLinearLayout separatorLayout2 = new SlptLinearLayout();
        SlptPictureView separator2 = new SlptPictureView();
        separator2.setStringPicture("-");

        separatorLayout2.add(separator);
        separatorLayout2.setTextAttr(
                context.getResources().getDimension(R.dimen.star_wars_font_size),
                Color.WHITE,
                timeTypeFace
        );


        dateLayout.add(new SlptMonthHView());
        dateLayout.add(new SlptMonthLView());
        dateLayout.add(separatorLayout);
        dateLayout.add(new SlptDayHView());
        dateLayout.add(new SlptDayLView());
        dateLayout.add(separatorLayout2);
        dateLayout.add(new SlptYear3View());
        dateLayout.add(new SlptYear2View());
        dateLayout.add(new SlptYear1View());
        dateLayout.add(new SlptYear0View());

        dateLayout.setStringPictureArrayForAll(this.digitalNums);

        dateLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.star_wars_date_font_size),
                Color.WHITE,
                timeTypeFace
        );
        // Position based on screen on
        dateLayout.alignX = 2;
        dateLayout.alignY = 0;
        dateLayout.setRect(
                (int) (2 * context.getResources().getDimension(R.dimen.star_wars_date_left) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_date_font_size))
        );
        dateLayout.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.star_wars_date_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.star_wars_date_font_size))
        );
        //Add it to the list
        slpt_objects.add(dateLayout);


        return slpt_objects;
    }
}
