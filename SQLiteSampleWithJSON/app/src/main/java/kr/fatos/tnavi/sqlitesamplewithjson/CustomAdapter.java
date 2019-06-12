package kr.fatos.tnavi.sqlitesamplewithjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.fatos.tnavi.sqlitesamplewithjson.data.DataObject;

public class CustomAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<DataObject> InfoArr;
    private ViewHolder holder;

    public CustomAdapter(Context c, ArrayList<DataObject> array) {
        mInflater = LayoutInflater.from(c);
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            holder = new ViewHolder();
            v = mInflater.inflate(R.layout.listview_item, null);
            holder.name = (TextView)v.findViewById(R.id.tvName);
            holder.contact = (TextView)v.findViewById(R.id.tvContact);
            holder.no = (TextView)v.findViewById(R.id.tvEmail);

            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        DataObject info = InfoArr.get(position);

        holder.name.setText(info.name);
        holder.contact.setText(info.phone);
        holder.no.setText(info.no);

        return v;
    }


    private class ViewHolder {
        TextView name;
        TextView contact;
        TextView no;
    }
}
