package com.example.champions.DAL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.champions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import src.buisnesEntities.Game;
import src.buisnesEntities.User;

public class ScoreAdapter extends BaseAdapter {
    Context context;
    Map<String, Integer> listScores;
    LayoutInflater inflater;

    public ScoreAdapter(Context ctx, Map<String, Integer> table) {
        this.context = ctx;
        this.listScores = table;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listScores.size();
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
        view = inflater.inflate(R.layout.activity_leaderboard_row, null);
        TextView textView1 = (TextView) view.findViewById(R.id.rank_);
        TextView textView2 = (TextView) view.findViewById(R.id.nick_);
        TextView textView3 = (TextView) view.findViewById(R.id.score_);

        // Bind the data to the TextViews
        int index = 0;
        Iterator<Map.Entry<String, Integer>> iterator = listScores.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (i == index) {
                String userId = entry.getKey();
                Integer score = entry.getValue();
                FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseDatabase.collection("users").document(userId);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        String nickname= user.getNickname();
                        // Bind the data to the TextViews
                        textView1.setText("  "+String.valueOf(i+1));
                        textView2.setText(nickname);
                        textView3.setText(String.valueOf(score)+"  ");
                    }
                });
                break;
            }
            index++;
        }

        // Return the view
        return view;
    }
}

