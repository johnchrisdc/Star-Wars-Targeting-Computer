package com.jcdc.watchpace.targetingcomputer;

import android.content.Context;
import android.graphics.Paint;

import com.huami.watch.watchface.AbstractSlptClock;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsStepsWidget;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetBatteryWidget;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetMainClock;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetSportTotalDistanceWidget;

public class StarWarsTarget extends AbstractWatchFace {

    private Context context;

    private static Paint paint;

    public StarWarsTarget() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);

        this.clock = new StarWarsTargetMainClock(context);

        this.widgets.add(new StarWarsTargetBatteryWidget(context));
        this.widgets.add(new StarWarsTargetSportTotalDistanceWidget(context));
        this.widgets.add(new StarWarsStepsWidget(context));

        notifyStatusBarPosition(
                (float) 108,
                (float) 110
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return StarWarsTargerSlpt.class;
    }

    public static Paint getPaint() {
        return StarWarsTarget.paint;
    }
}
