package com.shivam.pillbox.extras;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shivam.pillbox.R;
import com.shivam.pillbox.data.MedicineColumns;
import com.shivam.pillbox.data.MedicineProvider;

import java.util.ArrayList;

/**
 * Created by shivam on 20/01/17.
 */

public class DeleteCurrentMedicineTask extends AsyncTask<ContextAndId, Void, Context> {

    @Override
    protected Context doInBackground(ContextAndId... contextAndIds) {

        Context context = contextAndIds[0].getContext();
        String medId = contextAndIds[0].getMedId();

        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

        ContentProviderOperation.Builder builder = ContentProviderOperation.newDelete
                (MedicineProvider.Medicines.CONTENT_URI);
        builder.withSelection(MedicineColumns.MEDICINE_ID + " = \"" + medId + "\"", null);

        batchOperations.add(builder.build());

        try {
            context.getContentResolver().applyBatch(MedicineProvider.AUTHORITY, batchOperations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context;
    }

    @Override
    protected void onPostExecute(Context context) {
        if (context != null)
            Toast.makeText(context, context.getString(R.string.delete_medicine_toast), Toast
                    .LENGTH_SHORT).show();
    }

}
