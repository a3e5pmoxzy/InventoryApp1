package com.example.android.inventoryapp1.data;

import android.provider.BaseColumns;

public final class InventoryAssetsMng {
    private InventoryAssetsMng() {
    }

    public static abstract class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "tbl_warehouse_storage_mng";

        public static final String _ID = BaseColumns._ID;
        public static final String COL_INVENTORY_ASSETS_PRODUCT_NAME = "assets_product_name";
        public static final String COL_INVENTORY_ASSETS_PRODUCT_PRICE = "assets_product_price";
        public static final String COL_INVENTORY_ASSETS_PRODUCT_QUANTITY = "assets_product_quantity";
        public static final String COL_INVENTORY_LOGISTICS_SUPPLIER_NAME = "logistics__supplier_name";
        public static final String COL_INVENTORY_LOGISTICS_SUPPLIER_PHONE = "logistics_supplier_phone";
    }
}
