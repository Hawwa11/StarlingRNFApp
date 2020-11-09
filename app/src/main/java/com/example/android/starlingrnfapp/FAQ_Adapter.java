package com.example.android.starlingrnfapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class FAQ_Adapter extends RecyclerView.Adapter<FAQ_Adapter.FAQVH> {
    List<FAQ> FAQlist;

    public FAQ_Adapter(List<FAQ> FAQlist) {
        this.FAQlist = FAQlist;
    }

    @NonNull
    @Override
    public FAQVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new FAQVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQVH holder, int position) {
        FAQ FAQ=FAQlist.get(position);
        holder.question.setText(FAQ.getQuestion());
        holder.answer.setText(FAQ.getAnswer());

        boolean isExpandable=FAQlist.get(position).isExpandable();
        holder.expandable_layout.setVisibility(isExpandable? View.VISIBLE:View.GONE);

    }

    @Override
    public int getItemCount() {
        return FAQlist.size();
    }

    public class FAQVH extends RecyclerView.ViewHolder {

        TextView question,answer;
        LinearLayout linear_layout;
        RelativeLayout expandable_layout;

        public FAQVH(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            linear_layout=itemView.findViewById(R.id.linear_layout);
            expandable_layout=itemView.findViewById(R.id.expandable_layout);

            linear_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FAQ FAQ=FAQlist.get(getAdapterPosition());
                    FAQ.setExpandable(!FAQ.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}