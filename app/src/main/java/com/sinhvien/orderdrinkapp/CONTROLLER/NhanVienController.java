package com.sinhvien.orderdrinkapp.CONTROLLER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.orderdrinkapp.MODEL.NhanVienModel;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienController {

    SQLiteDatabase database;
    public NhanVienController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemNhanVien(NhanVienModel nhanVienModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV, nhanVienModel.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN, nhanVienModel.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU, nhanVienModel.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL, nhanVienModel.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT, nhanVienModel.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH, nhanVienModel.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH, nhanVienModel.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN, nhanVienModel.getMAQUYEN());

        long ktra = database.insert(CreateDatabase.TBL_NHANVIEN,null,contentValues);
        return ktra;
    }

    public long SuaNhanVien(NhanVienModel nhanVienModel, int manv){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV, nhanVienModel.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN, nhanVienModel.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU, nhanVienModel.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL, nhanVienModel.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT, nhanVienModel.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH, nhanVienModel.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH, nhanVienModel.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN, nhanVienModel.getMAQUYEN());

        long ktra = database.update(CreateDatabase.TBL_NHANVIEN,contentValues,
                CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv,null);
        return ktra;
    }

    public int KiemTraDN(String tenDN, String matKhau){
        String query = "SELECT * FROM " +CreateDatabase.TBL_NHANVIEN+ " WHERE "
                +CreateDatabase.TBL_NHANVIEN_TENDN +" = '"+ tenDN+"' AND "+CreateDatabase.TBL_NHANVIEN_MATKHAU +" = '" +matKhau +"'";
        int manv = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manv = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)) ;
            cursor.moveToNext();
        }
        return manv;
    }

    public boolean KtraTonTaiNV(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<NhanVienModel> LayDSNV(){
        List<NhanVienModel> nhanVienModels = new ArrayList<NhanVienModel>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienModel nhanVienModel = new NhanVienModel();
            nhanVienModel.setHOTENNV(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
            nhanVienModel.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_EMAIL)));
            nhanVienModel.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
            nhanVienModel.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
            nhanVienModel.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_SDT)));
            nhanVienModel.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_TENDN)));
            nhanVienModel.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
            nhanVienModel.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)));
            nhanVienModel.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));

            nhanVienModels.add(nhanVienModel);
            cursor.moveToNext();
        }
        return nhanVienModels;
    }

    public boolean XoaNV(int manv){
        long ktra = database.delete(CreateDatabase.TBL_NHANVIEN,CreateDatabase.TBL_NHANVIEN_MANV+ " = " +manv
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public NhanVienModel LayNVTheoMa(int manv){
        NhanVienModel nhanVienModel = new NhanVienModel();
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN+" WHERE "+CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            nhanVienModel.setHOTENNV(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
            nhanVienModel.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_EMAIL)));
            nhanVienModel.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
            nhanVienModel.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
            nhanVienModel.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_SDT)));
            nhanVienModel.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_TENDN)));
            nhanVienModel.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
            nhanVienModel.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV)));
            nhanVienModel.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));

            cursor.moveToNext();
        }
        return nhanVienModel;
    }

    public int LayQuyenNV(int manv){
        int maquyen = 0;
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN+" WHERE "+CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MAQUYEN));

            cursor.moveToNext();
        }
        return maquyen;
    }


}
