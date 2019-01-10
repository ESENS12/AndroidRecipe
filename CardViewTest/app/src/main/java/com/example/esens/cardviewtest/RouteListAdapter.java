package com.example.esens.cardviewtest;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteViewHolder> {

    private ArrayList<RouteCardData> dataSet;

    private int selected = 0;

    private Drawable defaultIcon;
    private Drawable chargeIconNormal;
    private Drawable chargeIconSelected;
    private int defaultCardColor;
    private int defaultTextColor;
    private int selectedTextColor;

    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onRouteListItemClick(int position);
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView textRouteType;
        public ImageView imagePathColor;
        public LinearLayout layoutCharge;
        public ImageView imageCharge;
        public TextView textCharge;
        public TextView textDistance;
        public TextView textTime;

        public RouteViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview);
            textRouteType = (TextView) itemView.findViewById(R.id.textRouteType);
            imagePathColor = (ImageView) itemView.findViewById(R.id.imagePathColor);
            layoutCharge = (LinearLayout) itemView.findViewById(R.id.layoutCharge);
            imageCharge = (ImageView) itemView.findViewById(R.id.imageCharge);
            textCharge = (TextView) itemView.findViewById(R.id.textCharge);
            textDistance = (TextView) itemView.findViewById(R.id.textDistance);
            textTime = (TextView) itemView.findViewById(R.id.textTime);
        }
    }

    public RouteListAdapter(ArrayList<RouteCardData> data) {
        this.dataSet = data;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ESENS::RouteListAdapter","oncreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_routepath, parent, false);

        //RouteView 아이템의 width 수정 1/2 에서 1로(Full width) (ESENS)
        //view.getLayoutParams ().width = parent.getWidth () / 2;

        view.getLayoutParams().width = parent.getWidth();
        defaultIcon = view.getResources().getDrawable(R.drawable.s_info_icon_sel);
        chargeIconNormal = view.getResources().getDrawable(R.drawable.s_info_icon_charge_nor);
        chargeIconSelected = view.getResources().getDrawable(R.drawable.s_info_icon_charge_sel);
        defaultCardColor = view.getResources().getColor(R.color.white);
        selectedTextColor = view.getResources().getColor(R.color.white);
        defaultTextColor = view.getResources().getColor(R.color.black);
//        view.setOnClickListener(AMapRouteSummaryActivity.routeListOnClickListener);

        return (new RouteViewHolder(view));
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView textRouteType = holder.textRouteType;
        ImageView imagePathColor = holder.imagePathColor;
        LinearLayout layoutCharge = holder.layoutCharge;
        ImageView imageCharge = holder.imageCharge;
        TextView textCharge = holder.textCharge;
        TextView textDistance = holder.textDistance;
        TextView textTime = holder.textTime;

        RouteCardData routeCardData = dataSet.get(position);

        textRouteType.setText(routeCardData.type);

        if (routeCardData.type.isEmpty()) {
            cardView.setVisibility(View.INVISIBLE);
            return;
        }
        else {
            cardView.setVisibility(View.VISIBLE);
        }

        if (routeCardData.showCharge) {
            textCharge.setText(routeCardData.charge);
            layoutCharge.setVisibility(View.VISIBLE);
        } else
            layoutCharge.setVisibility(View.INVISIBLE);

        textDistance.setText(routeCardData.distance);
        textTime.setText(routeCardData.time);

        if (position == selected) {
            cardView.setCardBackgroundColor(routeCardData.color);
            imagePathColor.setImageDrawable(defaultIcon);
            textRouteType.setTextColor(selectedTextColor);
            imageCharge.setImageDrawable(chargeIconSelected);
            textCharge.setTextColor(selectedTextColor);
            textDistance.setTextColor(selectedTextColor);
            textTime.setTextColor(selectedTextColor);
        } else {
            cardView.setCardBackgroundColor(defaultCardColor);
            imagePathColor.setImageDrawable(routeCardData.icon);
            textRouteType.setTextColor(routeCardData.color);
            imageCharge.setImageDrawable(chargeIconNormal);
            textCharge.setTextColor(defaultTextColor);
            textDistance.setTextColor(defaultTextColor);
            textTime.setTextColor(routeCardData.color);
        }

        final int clickPosition = position;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onRouteListItemClick(clickPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
