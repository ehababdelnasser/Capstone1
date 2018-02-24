package com.example.lenovo.my_app_mov2;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Lenovo on 04/2/2018.
 */

public class Service extends RemoteViewsService {

      /*
* call the Adapter of the listview
*  Adapter is ListProvider
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new com.example.lenovo.my_app_mov2.WidgetListProvider(this.getApplicationContext(), intent));
    }

}
