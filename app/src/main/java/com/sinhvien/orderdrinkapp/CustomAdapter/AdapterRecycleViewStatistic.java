package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.orderdrinkapp.CONTROLLER.BanAnController;
import com.sinhvien.orderdrinkapp.CONTROLLER.NhanVienController;
import com.sinhvien.orderdrinkapp.MODEL.DonDatModel;
import com.sinhvien.orderdrinkapp.MODEL.NhanVienModel;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<DonDatModel> donDatModelList;
    NhanVienController nhanVienController;
    BanAnController banAnController;

    public AdapterRecycleViewStatistic(Context context, int layout, List<DonDatModel> donDatModelList){

        this.context =context;
        this.layout = layout;
        this.donDatModelList = donDatModelList;
        nhanVienController = new NhanVienController(context);
        banAnController = new BanAnController(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonDatModel donDatModel = donDatModelList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+ donDatModel.getMaDonDat());
        holder.txt_customstatistic_NgayDat.setText(donDatModel.getNgayDat());
        if(donDatModel.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatModel.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVienModel nhanVienModel = nhanVienController.LayNVTheoMa(donDatModel.getMaNV());
        holder.txt_customstatistic_TenNV.setText(nhanVienModel.getHOTENNV());
        holder.txt_customstatistic_BanDat.setText(banAnController.LayTenBanTheoMa(donDatModel.getMaBan()));
    }

    @Override
    public int getItemCount() {
        return donDatModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV,
                txt_customstatistic_BanDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat);
            txt_customstatistic_TenNV = itemView.findViewById(R.id.txt_customstatistic_TenNV);
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
