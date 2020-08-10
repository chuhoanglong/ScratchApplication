package com.example.scratchapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scratchapplication.adapter.GalleryAdapter;
import com.example.scratchapplication.dialog.BottomSheetDirections;
import com.example.scratchapplication.dialog.BottomSheetGallery;
import com.example.scratchapplication.dialog.BottomSheetInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MULTI_IMAGE_REQUEST = 2;
    private ImageView imageViewUploadCover;
    private ImageView imageEditGallery;
    private Button buttonAddInfo;
    private Button buttonAddDirections;
    private Button buttonAddGallery;

    private Uri imageCoverUri;
    private List<Uri> galleryUri;

    private RecyclerView recyclerViewGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        imageViewUploadCover = findViewById(R.id.image_upload_cover);
        imageViewUploadCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        imageEditGallery= findViewById(R.id.image_edit_gallery);
        imageEditGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogGallery();
            }
        });

        buttonAddInfo = findViewById(R.id.btn_add_info);
        buttonAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInfo();
            }
        });

        buttonAddDirections = findViewById(R.id.btn_add_direction);
        buttonAddDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDirections();
            }
        });
        buttonAddGallery = findViewById(R.id.btn_add_gallery);
        buttonAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMultifileChooser();
            }
        });
    }

    private void openDialogGallery() {
        BottomSheetGallery bottomSheetGallery = new BottomSheetGallery(galleryUri,this);
        bottomSheetGallery.show(getSupportFragmentManager(),"gallery");
    }

    private void openMultifileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photos"), PICK_MULTI_IMAGE_REQUEST);

    }

    private void openDialogDirections() {
        BottomSheetDirections bottomSheetDirections = new BottomSheetDirections();
        bottomSheetDirections.show(getSupportFragmentManager(), "directions");
        bottomSheetDirections.setCancelable(false);
    }

    private void openDialogInfo() {
        BottomSheetInfo dialogInfo = new BottomSheetInfo();
        dialogInfo.show(getSupportFragmentManager(), "info");
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    imageCoverUri = data.getData();
                    Picasso.with(this).load(imageCoverUri).into(imageViewUploadCover);
                    imageViewUploadCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
            case PICK_MULTI_IMAGE_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    galleryUri = new ArrayList<>();
                    if (data.getData()!=null){
                        galleryUri.add(data.getData());
                    }
                    else if (data.getClipData()!=null){
                        ClipData clipData = data.getClipData();
                        for (int i = 0; i<clipData.getItemCount();i++){
                            ClipData.Item item = clipData.getItemAt(i);
                            Uri uri = item.getUri();
                            galleryUri.add(uri);

                        }
                        Toast.makeText(this, galleryUri.size()+"", Toast.LENGTH_SHORT).show();
                    }

                    //edit view
                    recyclerViewGallery = findViewById(R.id.rv_add_gallery);
                    recyclerViewGallery.setAdapter(new GalleryAdapter(galleryUri,this));
                    recyclerViewGallery.setHasFixedSize(true);

                    buttonAddGallery.setVisibility(View.GONE);
                }
                break;
            default:
        }
    }
}