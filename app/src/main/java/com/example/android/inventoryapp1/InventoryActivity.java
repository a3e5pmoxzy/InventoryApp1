package com.example.android.inventoryapp1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventoryapp1.data.InventoryAssetsMng.InventoryEntry;
import com.example.android.inventoryapp1.data.InventoryDbHelper;
import com.example.android.inventoryapp1.data.InventoryAssetsMng;

public class InventoryActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        FloatingActionButton faBtnPlus = (FloatingActionButton) findViewById(R.id.btn_fab_plus);
        faBtnPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_NAME,
                InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_PRICE,
                InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_QUANTITY,
                InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_NAME,
                InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE
        };

        Cursor cursor = db.query(
                InventoryAssetsMng.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        TextView displayView = (TextView) findViewById(R.id.txtview_inventory_assets_product);

        try {
            displayView.setText("The inventory table contains " + cursor.getCount() + " inventory_assets.\n\n");
            displayView.append(InventoryAssetsMng.InventoryEntry._ID + " - " +
                    InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_NAME + " - " +
                    InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_PRICE + " - " +
                    InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_QUANTITY + " - " +
                    InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_NAME + " - " +
                    InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE + "\n");
            int idColumnIndex = cursor.getColumnIndex(InventoryAssetsMng.InventoryEntry._ID);
            int productnameColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_NAME);
            int productpriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_PRICE);
            int productquantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_QUANTITY);
            int suppliernameColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_NAME);
            int supplierphoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productnameColumnIndex);
                String currentProductPrice = cursor.getString(productpriceColumnIndex);
                String currentProductQuantity = cursor.getString(productquantityColumnIndex);
                String currentLogisticsSupplierName = cursor.getString(suppliernameColumnIndex);
                String currentLogisticsSupplierPhone = cursor.getString(supplierphoneColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentProductName + " - " +
                        currentProductPrice + " - " +
                        currentProductQuantity + " - " +
                        currentLogisticsSupplierName + " - " +
                        currentLogisticsSupplierPhone));
            }

        } finally {
            cursor.close();
        }
    }

    public void insertInventoryAssets() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_NAME, "Headphones");
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_PRICE, 9.8);
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_QUANTITY, 300);
        values.put(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_NAME, "Sony");
        values.put(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE, "001 5901298");
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
        Log.v("InventoryActivity", "New row ID" + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertInventoryAssets();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}