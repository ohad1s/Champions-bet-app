package com.example.champions.DAL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.champions.R;

import java.util.List;

import src.buisnesEntities.Game;

public class BetsPlayerBaseAdapter extends BaseAdapter {
    Context context;
    List<Game> games_list;
    int[] imgList;
    LayoutInflater inflater;
    public BetsPlayerBaseAdapter(Context ctx, List<Game> games, int[] images){
        this.context =ctx;
        this.games_list = games;
        this.imgList = images;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return games_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_bet_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.player_bet_txt_view);
        ImageView tournament_img = (ImageView) view.findViewById(R.id.ball_icon);
        textView.setText(games_list.get(i).getName());
        tournament_img.setImageResource(imgList[0]);
        return view;
    }
}
