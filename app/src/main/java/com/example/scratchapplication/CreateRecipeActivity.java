package com.example.scratchapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scratchapplication.adapter.GalleryAdapter;
import com.example.scratchapplication.adapter.ListStringAdapter;
import com.example.scratchapplication.adapter.User;
import com.example.scratchapplication.dialog.BottomSheetDirections;
import com.example.scratchapplication.dialog.BottomSheetGallery;
import com.example.scratchapplication.dialog.BottomSheetInfo;
import com.example.scratchapplication.dialog.BottomSheetIngredients;
import com.example.scratchapplication.model.Additional;
import com.example.scratchapplication.model.RecipeCreate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipeActivity extends AppCompatActivity
        implements BottomSheetIngredients.BottomSheetListener,
        BottomSheetDirections.BottomSheetListener,
        BottomSheetInfo.BottomSheetListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_MULTI_IMAGE_REQUEST = 2;
    private ImageView imageViewUploadCover;
    private ImageView imageEditGallery;
    private EditText editTextName;
    private EditText editTextDesc;
    private Button buttonAddInfo;
    private Button buttonAddDirections;
    private Button buttonAddIngredients;
    private Button buttonPost;

    private Uri imageCoverUri;
    private List<Uri> galleryUri;

    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private RecipeCreate recipeCreate;

    private RecyclerView recyclerViewGallery;

    public CreateRecipeActivity(){
        recipeCreate = new RecipeCreate();
        //System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("covers");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recipes");

        imageViewUploadCover = findViewById(R.id.image_upload_cover);
        imageViewUploadCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        /*imageEditGallery= findViewById(R.id.image_edit_gallery);
        imageEditGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogGallery();
            }
        });*/

        buttonAddIngredients = findViewById(R.id.btn_add_ingredients);
        buttonAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogIngredients();
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

        editTextName = findViewById(R.id.et_recipe_name);
        editTextDesc = findViewById(R.id.et_recipe_desc);

        buttonPost = findViewById(R.id.btn_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask!=null&&mUploadTask.isInProgress()){
                    Toast.makeText(CreateRecipeActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else 
                    uploadFile();
            }
        });
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageCoverUri!=null){
            StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()+"." + getFileExtension(imageCoverUri));
            mUploadTask = fileRef.putFile(imageCoverUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            Toast.makeText(CreateRecipeActivity.this, "Post successfully", Toast.LENGTH_SHORT).show();
                            recipeCreate.setName(editTextName.getText().toString());
                            recipeCreate.setDescription(editTextDesc.getText().toString());
                            recipeCreate.setpId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            recipeCreate.setUrlCover(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(recipeCreate);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });
        }
        else
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
    }

    private void openDialogIngredients() {
        BottomSheetIngredients bottomSheetIngredients = new BottomSheetIngredients();
        bottomSheetIngredients.show(getSupportFragmentManager(),"ingredients");
    }

//    private void openDialogGallery() {
//        BottomSheetGallery bottomSheetGallery = new BottomSheetGallery(galleryUri,this);
//        bottomSheetGallery.show(getSupportFragmentManager(),"gallery");
//    }



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

            default:
        }
    }


    @Override
    public void onButtonClickedSaveIngredients(List<String> list) {
        RecyclerView recyclerViewIngredients = findViewById(R.id.rv_ingredients_create);
        ListStringAdapter adapter = new ListStringAdapter(list,this);
        recyclerViewIngredients.setAdapter(adapter);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));
        recipeCreate.setIngredients(list);
    }

    @Override
    public void onButtonClickedSaveDirections(List<String> list) {
        RecyclerView recyclerViewDirections= findViewById(R.id.rv_directions_create);
        ListStringAdapter adapter = new ListStringAdapter(list,this);
        recyclerViewDirections.setAdapter(adapter);
        recyclerViewDirections.setLayoutManager(new LinearLayoutManager(this));
        recipeCreate.setDirections(list);
    }

    @Override
    public void onButtonClickedSaveInfo(Additional additional) {
        TextView textViewTime = findViewById(R.id.tv_info_time);
        TextView textViewNutrition = findViewById(R.id.tv_info_nutrition);
        TextView textViewTags = findViewById(R.id.tv_info_tags);
        textViewTime.setPadding(16,0,16,16);
        textViewNutrition.setPadding(16,0,16,16);
        textViewTags.setPadding(16,0,16,16);
        textViewTime.setText("Serving Time:\t"+additional.getServingTime());
        textViewNutrition.setText("Nutrition:\t"+additional.getNutrition());
        textViewTags.setText("Tags:\t"+additional.getTags());
        recipeCreate.setServingTime(additional.getServingTime());
        recipeCreate.setNutrition(additional.getNutrition());
        recipeCreate.setTags(additional.getTags());
    }
}