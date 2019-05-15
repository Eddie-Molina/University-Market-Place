package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ItemCreate extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText nameText;
    EditText descriptionText;
    EditText quantityText;
    EditText priceText;
    Button cancelButton;
    Button submitButton;
    Button imageButton;
    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);
        setTitle("Sell Item");
        myDb = new DatabaseHelper(this);
        nameText = (EditText) findViewById(R.id.nameText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        quantityText = (EditText) findViewById(R.id.quantityText);
        priceText = (EditText) findViewById(R.id.priceText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        submitButton = (Button) findViewById(R.id.submitButton);
        imageButton = (Button) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        cancel();
        submit();
        selectImage();
    }

    public void selectImage(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                image = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(image);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public void submit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String quantityStr = quantityText.getText().toString();
                int quantity = -1;
                String priceStr = priceText.getText().toString();
                double price = -1;
                boolean validName = false;
                boolean validDescription = false;
                boolean validQuantity = false;
                boolean validPrice = false;
                if(!name.equals("")){
                    validName = true;
                } else {
                    Toast.makeText(ItemCreate.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!description.equals("")){
                    validDescription = true;
                } else{
                    Toast.makeText(ItemCreate.this, "Please Enter a Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!quantityStr.equals("") && quantityStr.matches("\\d+")){
                    quantity = Integer.valueOf(quantityStr);
                    if(quantity > 0) {
                        validQuantity = true;
                    } else {
                        Toast.makeText(ItemCreate.this, "Please Enter a Positive Value for Quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(ItemCreate.this, "Please Enter an Integer for Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!priceStr.equals("") && priceStr.matches("[0-9]*\\.?[0-9]{2}|[0-9]+")){
                    price = Double.valueOf(priceStr);
                    if(price >= 0){
                        validPrice = true;
                    } else {
                        Toast.makeText(ItemCreate.this, "Please Enter a Positive Value for Price", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(ItemCreate.this, "Please Enter a Valid Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(validName && validDescription && validPrice && validQuantity){
                    if(image == null){
                        image = BitmapFactory.decodeResource(getResources(), R.drawable.utamascot);
                    }
                    if(image == null){
                        Toast.makeText(ItemCreate.this, "Failed to set default", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cursor user = myDb.getCurrentUser();
                    user.moveToNext();
                    int userID = user.getInt(0);
                    if(myDb.insertItem(name, description, quantity, price, userID, image)){ //sellerID should come from current user ID
                        Toast.makeText(ItemCreate.this, "Successfully Added Item", Toast.LENGTH_SHORT).show();
                        Intent nextIntent = new Intent(ItemCreate.this, TradeMenu.class);
                        startActivity(nextIntent);
                    } else {
                        Toast.makeText(ItemCreate.this, "Failed to Add Item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ItemCreate.this, TradeMenu.class);
                startActivity(mainIntent);
            }
        });
    }
}
