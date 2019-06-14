package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.fatos.tnavi.sqlitesamplewithjson.data.DataObject;
import kr.fatos.tnavi.sqlitesamplewithjson.data.ViewHolder;

public class CustomAdapter  extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DataObject> InfoArr;
    private ViewHolder holder;
    private int mSelectedItem;

    public CustomAdapter(Context c, ArrayList<DataObject> array) {
        mInflater = LayoutInflater.from(c);
        mContext = c;
        InfoArr = array;
    }

    @Override
    public int getCount() {
        return InfoArr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setArrayList(ArrayList<DataObject> arrays) {
        this.InfoArr = arrays;
    }

    public ArrayList<DataObject> getArrayList(){
        return InfoArr;
    }


    public interface ListBtnClickListener {
        void onListBtnClick(ViewHolder holder) ;
    }

    @Override
    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((ViewHolder)v.getTag());
            mSelectedItem = ((ViewHolder) v.getTag()).index;
        }
    }

    private ListBtnClickListener listBtnClickListener ;

    public void setListBtnClickListener (ListBtnClickListener clickListener){
        this.listBtnClickListener = clickListener ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            holder = new ViewHolder();
            v = mInflater.inflate(R.layout.listview_item, null);

            holder.name = (TextView)v.findViewById(R.id.tv_name);
            holder.contact = (TextView)v.findViewById(R.id.tv_contact);
            holder.no = (TextView)v.findViewById(R.id.tv_num);
            holder.index = position;
            v.setTag(holder);
            v.setOnClickListener(this);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        if (position == mSelectedItem) {
            v.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }else{
            v.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        DataObject info = InfoArr.get(position);

        holder.name.setText(info.name);
        holder.contact.setText(info.phone);
        holder.no.setText(info.no+"");

        return v;
    }

}
