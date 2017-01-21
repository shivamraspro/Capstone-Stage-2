package com.shivam.pillbox.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;
import com.shivam.pillbox.extras.ContextAndId;
import com.shivam.pillbox.extras.DeleteCurrentMedicineTask;
import com.shivam.pillbox.extras.Utility;
import com.shivam.pillbox.recyclerViewHelpers.MedicineDetailsCursorAdapter;
import com.shivam.pillbox.recyclerViewHelpers.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<Cursor> {

    @BindView(R.id.detail_activity_container)
    LinearLayout detailsActivityContainer;

    @BindView(R.id.details_name)
    TextView nameTextView;

    @BindView(R.id.details_frequency)
    TextView frequencyTextView;

    @BindView(R.id.details_instructions_title)
    TextView instructionsTitleTextView;

    @BindView(R.id.details_instructions)
    TextView instructionsTextView;

    @BindView(R.id.details_upcoming_title)
    TextView upcomingTextView;

    @BindView(R.id.recyler_view_details)
    RecyclerView recyclerView;

    @BindView(R.id.floatingActionButtonDelete)
    FloatingActionButton fab;

    private Context mContext;
    private String medId;
    private static final int CURSOR_LOADER_ID = 1;
    private MedicineDetailsCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        mContext = this;

        Intent intent = getIntent();

        medId = intent.getStringExtra("medId");

        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        final int colorIndex = intent.getIntExtra("medColor", -1);
        detailsActivityContainer.setBackground(getResources().getDrawable(
                Utility.getColorBackground(colorIndex)));

        nameTextView.setText(intent.getStringExtra("medName"));
        nameTextView.setTextColor(getResources().getColor(Utility.getColorText(colorIndex)));

        instructionsTitleTextView.setTextColor(getResources().getColor(Utility.getColorText(colorIndex)));
        upcomingTextView.setTextColor(getResources().getColor(Utility.getColorText(colorIndex)));

        int freq = intent.getIntExtra("medFreq", 1);
        if (freq > 1)
            frequencyTextView.setText(getString(R.string.details_frequency_text, freq));
        else
            frequencyTextView.setText(getString(R.string.details_frequency_text_one));

        float dose = (float) intent.getDoubleExtra("medDose", 1.00);
        String descString;
        if (((int) (dose * 100)) % 100 == 0)
            descString = mContext.getString(R.string.selectedDoseStringInt, (int) dose);
        else
            descString = mContext.getString(R.string.selectedDoseString, dose);

        descString += " " + intent.getStringExtra("medFoodMessage");
        String freeMsg = intent.getStringExtra("medFreeMessage");
        if (!freeMsg.equals(""))
            descString += ". " + freeMsg;

        instructionsTextView.setText(descString);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setPositiveButton(
                                getString(R.string.delete_medicine_alert_dialog_positive_button),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new DeleteCurrentMedicineTask().execute(new ContextAndId(
                                                mContext, medId));

                                        finish();
                                    }
                                })
                        .setNegativeButton(getString(R.string.dialog_time_picker_negative_button),
                                null)
                        .setMessage(getString(R.string
                                .delete_medicine_alert_dialog))
                        .show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MedicineDetailsCursorAdapter(mContext, null);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, new
                RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        //v.setFocusable(true);
                        v.setSelected(true);
                    }
                }));
    }

    public void onClickBackButton(View view) {
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                mContext,
                MedicineProvider.Medicines.CONTENT_URI,
                MedicineColumns.ALL_COLUMNS,
                MedicineColumns.MEDICINE_ID + " = \"" + medId + "\"",
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
