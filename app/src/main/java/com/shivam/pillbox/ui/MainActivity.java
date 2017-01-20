package com.shivam.pillbox.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.DeleteOldMedicinesTask;
import com.shivam.pillbox.recyclerViewHelpers.MedicineCursorAdapter;
import com.shivam.pillbox.recyclerViewHelpers.RecyclerViewEmptyViewSupport;
import com.shivam.pillbox.recyclerViewHelpers.RecyclerViewClickListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements LoaderManager
        .LoaderCallbacks<Cursor> {

    @BindView(R.id.recyler_view)
    RecyclerViewEmptyViewSupport recyclerView;

    @BindView(R.id.empty_view)
    TextView emptyView;

    @BindView(R.id.today_date)
    TextView todayDate;

    @BindView(R.id.today_day)
    TextView todayDay;

    private Context mContext;
    private MedicineCursorAdapter adapter;
    private static final int CURSOR_LOADER_ID = 0;
    private Cursor mCursor;
    private String midnightTime;
    private String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
    "Oct", "Nov", "Dec"};
    private String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = this;

        deleteOldMedicines();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddMedicationActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MedicineCursorAdapter(mContext, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(mContext, R.drawable.divider));
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, new
                RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        startActivity(intent);
                    }
                }));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        String date = "";
        String day = "";
        date = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar
                .DAY_OF_MONTH);
        day = weekNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        todayDate.setText(date);
        todayDay.setText(day);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        midnightTime = calendar.getTimeInMillis() + "";

        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        setupStetho();
    }

    private void setupStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                mContext,
                MedicineProvider.Medicines.CONTENT_URI,
                MedicineColumns.ALL_COLUMNS,
                MedicineColumns.TIME_IN_MILLIS + " <= ?",
                new String[]{midnightTime},
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        mCursor = cursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void deleteOldMedicines() {
        new DeleteOldMedicinesTask().execute(mContext);
    }
}
