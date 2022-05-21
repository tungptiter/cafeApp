package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.CONTROLLER.QuyenController;
import com.sinhvien.orderdrinkapp.MODEL.NhanVienModel;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterDisplayStaff extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVienModel> nhanVienModels;
    ViewHolder viewHolder;
    QuyenController quyenController;

    public AdapterDisplayStaff(Context context, int layout, List<NhanVienModel> nhanVienModels){
        this.context = context;
        this.layout = layout;
        this.nhanVienModels = nhanVienModels;
        quyenController = new QuyenController(context);
    }

    @Override
    public int getCount() {
        return nhanVienModels.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienModels.get(position).getMANV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customstaff_HinhNV = (ImageView)view.findViewById(R.id.img_customstaff_HinhNV);
            viewHolder.txt_customstaff_TenNV = (TextView)view.findViewById(R.id.txt_customstaff_TenNV);
            viewHolder.txt_customstaff_TenQuyen = (TextView)view.findViewById(R.id.txt_customstaff_TenQuyen);
            viewHolder.txt_customstaff_SDT = (TextView)view.findViewById(R.id.txt_customstaff_SDT);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVienModel nhanVienModel = nhanVienModels.get(position);

        viewHolder.txt_customstaff_TenNV.setText(nhanVienModel.getHOTENNV());
        viewHolder.txt_customstaff_TenQuyen.setText(quyenController.LayTenQuyenTheoMa(nhanVienModel.getMAQUYEN()));
        viewHolder.txt_customstaff_SDT.setText(nhanVienModel.getSDT());
        viewHolder.txt_customstaff_Email.setText(nhanVienModel.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_HinhNV;
        TextView txt_customstaff_TenNV, txt_customstaff_TenQuyen,txt_customstaff_SDT, txt_customstaff_Email;
    }
}
