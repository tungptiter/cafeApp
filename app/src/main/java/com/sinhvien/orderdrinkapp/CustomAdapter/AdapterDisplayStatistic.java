package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.CONTROLLER.BanAnController;
import com.sinhvien.orderdrinkapp.CONTROLLER.NhanVienController;
import com.sinhvien.orderdrinkapp.MODEL.DonDatModel;
import com.sinhvien.orderdrinkapp.MODEL.NhanVienModel;
import com.sinhvien.orderdrinkapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<DonDatModel> donDatModels;
    List<DonDatModel> donDatModelSold;
    ViewHolder viewHolder;
    NhanVienController nhanVienController;
    BanAnController banAnController;

    public AdapterDisplayStatistic(Context context, int layout, List<DonDatModel> donDatModelSold){
        this.context = context;
        this.layout = layout;
        this.donDatModelSold = donDatModelSold;
        nhanVienController = new NhanVienController(context);
        banAnController = new BanAnController(context);
        getDonDatThanhCong(donDatModelSold);
    }

    @Override
    public int getCount() {
        return donDatModels.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatModels.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDatModel donDatModel = donDatModels.get(position);
        if (donDatModel.getTinhTrang().equals("true")) {
            viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: " + donDatModel.getMaDonDat());
            viewHolder.txt_customstatistic_NgayDat.setText(donDatModel.getNgayDat());
            viewHolder.txt_customstatistic_TongTien.setText(donDatModel.getTongTien() + " VNĐ");

            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");

            NhanVienModel nhanVienModel = nhanVienController.LayNVTheoMa(donDatModel.getMaNV());
            viewHolder.txt_customstatistic_TenNV.setText(nhanVienModel.getHOTENNV());
            viewHolder.txt_customstatistic_BanDat.setText(banAnController.LayTenBanTheoMa(donDatModel.getMaBan()));
        }
        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
    private void getDonDatThanhCong(List<DonDatModel> listoll){

        donDatModels = new ArrayList<>();
        for (DonDatModel datDTO: listoll) {
            if(datDTO.getTinhTrang().equals("true")){
                donDatModels.add(datDTO);
            }
        }
    }
}
