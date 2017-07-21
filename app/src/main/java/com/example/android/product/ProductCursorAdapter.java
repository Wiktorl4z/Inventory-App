package com.example.android.product;

/**
 * Created by l4z on 16.07.2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.product.data.ProductContract;

public class ProductCursorAdapter extends CursorAdapter {

    private ImageView mImageView;
    private CatalogActivity catalogActivity;

    int mQuantity;

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Fill out this method and return the list item view (instead of null)
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        mImageView = (ImageView) view.findViewById(R.id.image_view_buy);
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.text_view_price);
       final TextView quantityTextView = (TextView) view.findViewById(R.id.text_view_quantity);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_list);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PICTURE);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);

        mQuantity = Integer.parseInt(quantity);

        if (!cursor.isNull(imageColumnIndex)) {
            Integer productImage = cursor.getInt(imageColumnIndex);
            imageView.setImageResource(productImage);
        }
        // If the product price is empty string or null, then use some default text
        // that says "Unknown price", so the TextView isn't blank.
        if (TextUtils.isEmpty(productPrice)) {
            productPrice = context.getString(R.string.unknown_price);
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText("PRODUCT " + productName);
        priceTextView.setText("PRICE " + productPrice + "$");
        quantityTextView.setText("QUANTITY " + mQuantity);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantity == 0) {
                    Toast.makeText(context, "Quantity Unavailable", Toast.LENGTH_SHORT).show();
                } else {
                    //  catalogActivity.onBuyProduct(id,mQuantity);
                    mQuantity--;
                    quantityTextView.setText("QUANTITY " + mQuantity);
                    notifyDataSetChanged ();
                }
            }
        });
    }
}