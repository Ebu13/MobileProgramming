package com.example.sanalbahce;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PlantAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;

    public PlantAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndexOrThrow("id"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_plant, parent, false);
        }

        cursor.moveToPosition(position);

        TextView txtPlantCode = convertView.findViewById(R.id.txtPlantCode);
        TextView txtPlantType = convertView.findViewById(R.id.txtPlantType);
        TextView txtPlantDate = convertView.findViewById(R.id.txtPlantDate);
        TextView txtLastWaterDate = convertView.findViewById(R.id.txtLastWaterDate);
        TextView txtWaterAmount = convertView.findViewById(R.id.txtWaterAmount);
        TextView txtWateringWarning = convertView.findViewById(R.id.txtWateringWarning);

        txtPlantCode.setText("Bitki Kodu: " + cursor.getString(cursor.getColumnIndexOrThrow("plant_code")));
        txtPlantType.setText("Tür: " + cursor.getString(cursor.getColumnIndexOrThrow("type")));
        txtPlantDate.setText("Dikim Tarihi: " + cursor.getString(cursor.getColumnIndexOrThrow("plant_date")));
        txtLastWaterDate.setText("Son Sulama Tarihi: " + cursor.getString(cursor.getColumnIndexOrThrow("last_water_date")));
        txtWaterAmount.setText("Sulama Miktarı: " + cursor.getDouble(cursor.getColumnIndexOrThrow("water_amount")) + " litre");

        // Gecikmiş sulama kontrolü
        String lastWaterDate = cursor.getString(cursor.getColumnIndexOrThrow("last_water_date"));
        if (isWateringOverdue(lastWaterDate)) {
            // Zemin rengini kırmızı yap
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));

            // Uyarı metnini göster
            txtWateringWarning.setVisibility(View.VISIBLE);
            txtWateringWarning.setText("Sulama Zamanı Gecikti!");
        } else {
            // Varsayılan zemin rengi ve uyarıyı gizle
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            txtWateringWarning.setVisibility(View.GONE);
        }

        return convertView;
    }

    private boolean isWateringOverdue(String lastWaterDate) {
        // Tarih işlemleri için kontrol (örnek)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date lastDate = sdf.parse(lastWaterDate);
            Date today = new Date();
            long diff = today.getTime() - lastDate.getTime();
            long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            // Sulama gecikmiş mi?
            return daysDiff > 7; // 7 günden fazla geçmişse uyarı
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Bu metod, adapter içindeki veriyi güncellemek için eklenmiştir
    public void updatePlants(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }
}
