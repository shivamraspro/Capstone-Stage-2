package com.shivam.pillbox.extras;

import android.content.Context;

/**
 * Created by shivam on 20/01/17.
 */

public class ContextAndId {
    Context context;
    String id;
    private String medId;

    public ContextAndId(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public String getMedId() {
        return id;
    }
}
