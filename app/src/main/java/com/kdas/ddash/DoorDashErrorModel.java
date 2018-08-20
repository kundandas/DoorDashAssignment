package com.kdas.ddash;

import android.content.Context;

public class DoorDashErrorModel implements ErrorModel{

    int errorResourceId;
    String[] formatArgs;

    public DoorDashErrorModel(int errorResourceId) {
        this(errorResourceId, null);
    }

    public DoorDashErrorModel(int errorResourceId, String[] formatArgs) {
        this.errorResourceId = errorResourceId;
        this.formatArgs = formatArgs;
    }

    @Override
    public String getErrorDisplayMessage(Context context) {
        if (formatArgs == null || formatArgs.length == 0) {
            return context.getString(errorResourceId);
        } else {
            return context.getString(errorResourceId, formatArgs);
        }
    }
}
