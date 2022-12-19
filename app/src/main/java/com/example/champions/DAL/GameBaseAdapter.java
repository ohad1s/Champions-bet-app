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

public class GameBaseAdapter extends BaseAdapter {
    Context context;
    List<Game> listGames;
    int gameImgList[];
    LayoutInflater inflater;

    public GameBaseAdapter(Context ctx, List<Game> gameList, int[] images) {
        this.context = ctx;
        this.listGames = gameList;
        this.gameImgList = images;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listGames.size();
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
        view = inflater.inflate(R.layout.activity_game_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.game_txt_view);
        ImageView tournament_img = (ImageView) view.findViewById(R.id.game_img);
        textView.setText(listGames.get(i).getGameID());
        tournament_img.setImageResource(gameImgList[0]);
        return view;

    }
}
