package com.william.pokekeep.mvp.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.william.pokekeep.R;
import com.william.pokekeep.mvp.model.entity.PokeDexObj;

import java.util.List;

/**
 * Created by William Chow on 2019-11-19.
 */
public class PokeListAdapter extends RecyclerView.Adapter<PokeListAdapter.PokeListHolder> {

    private List<PokeDexObj> pokeDexObjList;
    private Activity activity;

    private AdapterOnClickListener onClickListener;

    public void setOnClickListener(AdapterOnClickListener _onClickListener) {
        this.onClickListener = _onClickListener;
    }

    public PokeListAdapter(Activity _activity, List<PokeDexObj> _pokeDexObjList) {
        activity = _activity;
        pokeDexObjList = _pokeDexObjList;
    }

    @NonNull
    @Override
    public PokeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_poke_item, parent, false);
        return new PokeListHolder(itemView);
    }

    public void onBindViewHolder(@NonNull PokeListHolder holder, int position) {
        PokeDexObj pokeDexObj = pokeDexObjList.get(position);
        Glide.with(activity).load(pokeDexObj.getImage()).placeholder(R.drawable.drawable_poke_dex).into(holder.ivPokeImage);
        holder.tvPokeName.setText(pokeDexObj.getName());
        holder.rlPoke.setOnClickListener(view -> onClickListener.onClickListener(view, position, pokeDexObjList));
        holder.rlMenu.setOnClickListener(view -> onClickListener.onClickMenuListener(view, position, pokeDexObjList));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return pokeDexObjList.size();
    }

    private void add(PokeDexObj r) {
        pokeDexObjList.add(r);
        notifyItemInserted(pokeDexObjList.size() - 1);
    }

    public void addAll(List<PokeDexObj> moveResults) {
        for (PokeDexObj result : moveResults) {
            add(result);
        }
    }

    public void clear() {
        int size = this.pokeDexObjList.size();
        this.pokeDexObjList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class PokeListHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlPoke, rlMenu;
        ImageView ivPokeImage;
        TextView tvPokeName;
        // ImageView ivMenu;

        PokeListHolder(View itemView) {
            super(itemView);
            rlPoke = itemView.findViewById(R.id.rlPoke);
            rlMenu = itemView.findViewById(R.id.rlMenu);
            ivPokeImage = itemView.findViewById(R.id.ivPokeImage);
            tvPokeName = itemView.findViewById(R.id.tvPokeName);
            // ivMenu = itemView.findViewById(R.id.ivMenu);
        }
    }

    public interface AdapterOnClickListener {
        void onClickListener(View view, int _position, List<PokeDexObj> pokeDexObjList);

        void onClickMenuListener(View view, int _position, List<PokeDexObj> pokeDexObjList);
    }
}
