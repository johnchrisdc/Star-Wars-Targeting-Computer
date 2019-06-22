package com.jcdc.watchpace.targetingcomputer;

import android.content.Context;
import android.content.Intent;

import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsStepsWidget;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetBatteryWidget;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetMainClock;
import com.jcdc.watchpace.targetingcomputer.widget.StarWarsTargetSportTotalDistanceWidget;
import com.jcdc.watchpace.targetingcomputer.widget.Widget;

public class StarWarsTargerSlpt extends AbstractWatchFaceSlpt {

    private Context context;

    public StarWarsTargerSlpt() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        context = getApplicationContext();

        this.clock = new StarWarsTargetMainClock(context);

        this.widgets.add(new StarWarsTargetBatteryWidget(context));
        this.widgets.add(new StarWarsTargetSportTotalDistanceWidget(context));
        this.widgets.add(new StarWarsStepsWidget(context));

        return super.onStartCommand(intent, i, i1);
    }

    @Override
    protected SlptLayout createClockLayout26WC() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this, true)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this, true)) {
                result.add(component);
            }
        }

        return result;
    }

    @Override
    protected SlptLayout createClockLayout8C() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this)) {
                result.add(component);
            }
        }

        return result;
    }

    @Override
    protected void initWatchFaceConfig() {

    }
}
