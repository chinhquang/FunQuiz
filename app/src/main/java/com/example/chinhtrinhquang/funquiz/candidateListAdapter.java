package com.example.chinhtrinhquang.funquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.chinhtrinhquang.funquiz.Candidate;
import com.example.chinhtrinhquang.funquiz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 3/14/2017.
 */

public class candidateListAdapter extends ArrayAdapter<Rank> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView rankpoint;
        TextView name;
        TextView score;
    }

    public candidateListAdapter(Context context, int resource, ArrayList<Rank> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getUsername();

        Integer Point = getItem(position).getScore();

        Integer rankscore = getItem(position).getRankpoint();


        //Create the person object with the information
        Rank rank = new Rank(name, Point, rankscore);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.rankpoint = (TextView) convertView.findViewById(R.id.textView1);
            holder.name = (TextView) convertView.findViewById(R.id.textView2);
            holder.score = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? android.R.anim.slide_in_left : android.R.anim.slide_out_right);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(rank.getUsername());
        holder.score.setText(rank.getScore());
        holder.rankpoint.setText(rank.getRankpoint());


        return convertView;
    }
}

