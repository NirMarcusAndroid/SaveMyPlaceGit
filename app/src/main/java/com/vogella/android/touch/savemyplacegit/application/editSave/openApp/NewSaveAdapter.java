package com.vogella.android.touch.savemyplacegit.application.editSave.openApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.touch.savemyplacegit.R;
import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import java.util.List;

public class NewSaveAdapter extends RecyclerView.Adapter<NewSaveAdapter.NewSaveViewHolder>{

    private List<NewSave> saves;
    private Context context;

    public NewSaveAdapter(List<NewSave> saves, Context context) {
        this.saves = saves;
        this.context = context;
    }

    interface newSaveListener{
        void onNewSaveClicked(int position, View view);
        void onCloseButtonClicked(int position, View view);
        void onEditButtonClicked(int position, View view);
    }
    private newSaveListener listener;

    public void setListener(newSaveListener listener) {this.listener = listener;}

    public class NewSaveViewHolder extends RecyclerView.ViewHolder{

        TextView saveTitle;
        ImageButton closeBtn, pinBtn, pinBtnGrey, editBtn, editBtnGrey ,saveEditBtn;
        EditText editSaveEt;
        View viewLeft, viewRight;

        public NewSaveViewHolder(@NonNull View itemView) {
            super(itemView);

            //edit buttons
            editBtn = itemView.findViewById(R.id.edit_btn);
            editBtnGrey = itemView.findViewById(R.id.edit_btn_grey);

            editBtn.setVisibility(View.VISIBLE);
            editBtnGrey.setVisibility(View.GONE);

            //edit text, text view and save and close buttons
            saveTitle = itemView.findViewById(R.id.new_save_title_tv);
            editSaveEt = itemView.findViewById(R.id.edit_save_et);

            saveTitle.setVisibility(View.VISIBLE);
            editSaveEt.setVisibility(View.GONE);

            saveEditBtn = itemView.findViewById(R.id.save_edit_btn);
            closeBtn = itemView.findViewById(R.id.delete_new_save_btn);

            saveEditBtn.setVisibility(View.GONE);
            closeBtn.setVisibility(View.GONE);

            //pin buttons and divider views
            pinBtn = itemView.findViewById(R.id.pin_btn);
            pinBtnGrey = itemView.findViewById(R.id.pin_grey_btn);

            pinBtn.setVisibility(View.VISIBLE);
            pinBtnGrey.setVisibility(View.GONE);

            viewLeft = itemView.findViewById(R.id.view_left);
            viewRight = itemView.findViewById(R.id.view_right);

            viewLeft.setBackgroundColor(Color.parseColor("#000000"));
            viewRight.setBackgroundColor(Color.parseColor("#000000"));

            //listener view methods
            pinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onNewSaveClicked(getAdapterPosition(), view);
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCloseButtonClicked(getAdapterPosition(), view);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editBtn.setVisibility(View.GONE);
                    saveTitle.setVisibility(View.GONE);
                    pinBtn.setVisibility(View.GONE);

                    closeBtn.setVisibility(View.VISIBLE);
                    editSaveEt.setVisibility(View.VISIBLE);
                    pinBtnGrey.setVisibility(View.VISIBLE);
                    saveEditBtn.setVisibility(View.VISIBLE);
                    editBtnGrey.setVisibility(View.VISIBLE);

                    viewLeft.setBackgroundColor(Color.parseColor("#FFD8D0D0"));
                    viewRight.setBackgroundColor(Color.parseColor("#FFD8D0D0"));

                    saveEditBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {

                            if(!editSaveEt.getText().toString().isEmpty())
                            {saves.get(getAdapterPosition()).setSaveName(editSaveEt.getText().toString());}

                            listener.onEditButtonClicked(getAdapterPosition(), view1);

                            closeBtn.setVisibility(View.GONE);
                            editSaveEt.setVisibility(View.GONE);
                            pinBtnGrey.setVisibility(View.GONE);
                            saveEditBtn.setVisibility(View.GONE);
                            editBtnGrey.setVisibility(View.GONE);

                            saveTitle.setVisibility(View.VISIBLE);
                            pinBtn.setVisibility(View.VISIBLE);
                            editBtn.setVisibility(View.VISIBLE);

                            viewLeft.setBackgroundColor(Color.parseColor("#000000"));
                            viewRight.setBackgroundColor(Color.parseColor("#000000"));
                        }
                    });
                }
            });
        }


    }

    @NonNull
    @Override
    public NewSaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_save_layout, parent, false);
        NewSaveViewHolder newSaveViewHolder = new NewSaveViewHolder(view);

        return newSaveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewSaveViewHolder holder, int position) {
        NewSave newSave = saves.get(position);
        holder.saveTitle.setText(newSave.getSaveName());
    }

    @Override
    public int getItemCount() {
        return (saves == null || saves.isEmpty())? 0: saves.size() ;
    }

}