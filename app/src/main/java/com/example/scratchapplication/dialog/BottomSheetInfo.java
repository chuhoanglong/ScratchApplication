package com.example.scratchapplication.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scratchapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetInfo extends BottomSheetDialogFragment {
    private ImageView imageViewClose;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_info,container,false);
        imageViewClose = v.findViewById(R.id.image_close_dialog_info);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }
}
