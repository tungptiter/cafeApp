package com.sinhvien.orderdrinkapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterDisplayPayment;
import com.sinhvien.orderdrinkapp.CONTROLLER.BanAnController;
import com.sinhvien.orderdrinkapp.CONTROLLER.ChiTietDonDatController;
import com.sinhvien.orderdrinkapp.CONTROLLER.DonDatController;
import com.sinhvien.orderdrinkapp.CONTROLLER.ThanhToanController;
import com.sinhvien.orderdrinkapp.MODEL.ThanhToanModel;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonDatController donDatController;
    BanAnController banAnController;
    ThanhToanController thanhToanController;
    ChiTietDonDatController chiTietDonDatController;
    List<ThanhToanModel> thanhToanModels;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int maban, madondat;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = (TextView)findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayDat = (TextView)findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donDatController = new DonDatController(this);
        thanhToanController = new ThanhToanController(this);
        banAnController = new BanAnController(this);
        chiTietDonDatController = new ChiTietDonDatController(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        TXT_payment_TenBan.setText(tenban);
        TXT_payment_NgayDat.setText(ngaydat);

        //ktra mã bàn tồn tại thì hiển thị
        if(maban !=0 ){
            HienThiThanhToan();
            int soluong = 0;
            int giatien = 0;
            for (int i = 0; i< thanhToanModels.size(); i++){
                soluong = thanhToanModels.get(i).getSoLuong();
                giatien = thanhToanModels.get(i).getGiaTien();

                tongtien += (soluong * giatien);
            }
            TXT_payment_TongTien.setText(String.valueOf(tongtien) +" VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);

        registerForContextMenu(gvDisplayPayment);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                if(tongtien > 0){
                    boolean ktraban = banAnController.CapNhatTinhTrangBan(maban,"false");
                    boolean ktradondat = donDatController.UpdateTThaiDonTheoMaBan(maban,"true");
                    boolean ktratongtien = donDatController.UpdateTongTienDonDat(madondat,String.valueOf(tongtien));
                    if(ktraban && ktradondat && ktratongtien){
                        HienThiThanhToan();
                        TXT_payment_TongTien.setText("0 VNĐ");
                        Intent intent = new Intent();
                        setResult(RESULT_OK,intent);
                        Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Chưa có món ăn",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = thanhToanModels.get(vitri).getIdMon();
        switch (id){
            case R.id.itEdit:
                break;
            case R.id.itDelete:
                boolean ktXoa = chiTietDonDatController.XoaMonChiTieTDonDat(madondat,mamon);
                if(ktXoa){
                    HienThiThanhToan();
                    System.out.println("test");
                    Toast.makeText(getApplicationContext(),"Xoá thành công!", Toast.LENGTH_SHORT).show();

//                    thanhToanModels = thanhToanController.LayDSMonTheoMaDon(madondat);
                    System.out.println(thanhToanModels.size());
                    int soluong = 0;
                    int giatien = 0;
                    int money = 0;
                    for (int i = 0; i< thanhToanModels.size(); i++){
                        soluong = thanhToanModels.get(i).getSoLuong();
                        giatien = thanhToanModels.get(i).getGiaTien();

                        money += (soluong * giatien);
                    }
                    TXT_payment_TongTien.setText(String.valueOf(money) +" VNĐ");

                    System.out.println(money);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Xóa bị lỗi!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan(){
        madondat = (int) donDatController.LayMaDonTheoMaBan(maban,"false");
        thanhToanModels = thanhToanController.LayDSMonTheoMaDon(madondat);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu, thanhToanModels);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }


}