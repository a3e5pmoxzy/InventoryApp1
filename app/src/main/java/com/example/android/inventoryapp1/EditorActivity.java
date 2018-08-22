package com.example.android.inventoryapp1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp1.data.InventoryAssetsMng.InventoryEntry;
import com.example.android.inventoryapp1.data.InventoryDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private EditText mProductQuantityEditText;
    private EditText mLogisticsSupplierNameEditText;
    private EditText mLogisticsSupplierPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mProductNameEditText = findViewById(R.id.edit_inventory_assets_product_name);
        mProductPriceEditText = findViewById(R.id.edit_inventory_assets_product_price);
        mProductQuantityEditText = findViewById(R.id.edit_inventory_assets_product_quantity);
        mLogisticsSupplierNameEditText = findViewById(R.id.edit_inventory_logistics_supplier_name);
        mLogisticsSupplierPhoneEditText = findViewById(R.id.edit_inventory_logistics_supplier_phone);
    }

    private void insertStockStorageItem() {
        String productNameString = mProductNameEditText.getText().toString().trim();
        String productPriceString = mProductPriceEditText.getText().toString().trim();
        String productQuantityString = mProductQuantityEditText.getText().toString().trim();
        String logisticsSupplierNameString = mLogisticsSupplierNameEditText.getText().toString().trim();
        String logisticsSupplierPhoneString = mLogisticsSupplierPhoneEditText.getText().toString().trim();
        double productPrice = Double.parseDouble(productPriceString);
        int productQuantity = Integer.parseInt(productQuantityString);
        int logisticsSupplierPhone = Integer.parseInt(logisticsSupplierPhoneString);
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_NAME, productNameString);
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_PRICE, productPrice);
        values.put(InventoryEntry.COL_INVENTORY_ASSETS_PRODUCT_QUANTITY, productQuantity);
        values.put(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_NAME, logisticsSupplierNameString);
        values.put(InventoryEntry.COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE, logisticsSupplierPhone);
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
        Log.v("InventoryEntry", "New row ID" + newRowId);
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertStockStorageItem();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}