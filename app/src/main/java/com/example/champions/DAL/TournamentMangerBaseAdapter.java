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

import src.buisnesEntities.Tournament;

public class TournamentMangerBaseAdapter extends BaseAdapter {
    Context context;
    List<Tournament> listTournament;
    int tournamentImgList[];
    LayoutInflater inflater;
    public TournamentMangerBaseAdapter(Context ctx, List<Tournament> tournamentList, int[] images){
    this.context =ctx;
    this.listTournament = tournamentList;
    this.tournamentImgList = images;
    inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return listTournament.size();
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
        view = inflater.inflate(R.layout.activity_manager_tournaments_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.manager_tournament_txt_view);
        ImageView tournament_img = (ImageView) view.findViewById(R.id.manager_tournament_img);
        textView.setText(listTournament.get(i).getName());
        tournament_img.setImageResource(tournamentImgList[0]);
        return view;

    }
}
