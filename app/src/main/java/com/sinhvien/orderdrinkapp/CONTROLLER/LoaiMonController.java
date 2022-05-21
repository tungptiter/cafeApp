package com.sinhvien.orderdrinkapp.CONTROLLER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.orderdrinkapp.MODEL.LoaiMonModel;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonController {

    SQLiteDatabase database;
    public LoaiMonController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaiMonModel loaiMonModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI, loaiMonModel.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH, loaiMonModel.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAIMON,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIMON,CreateDatabase.TBL_LOAIMON_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(LoaiMonModel loaiMonModel, int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI, loaiMonModel.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH, loaiMonModel.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAIMON,contentValues
                ,CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaiMonModel> LayDSLoaiMon(){
        List<LoaiMonModel> loaiMonModelList = new ArrayList<LoaiMonModel>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonModel loaiMonModel = new LoaiMonModel();
            loaiMonModel.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonModel.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonModel.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH)));
            loaiMonModelList.add(loaiMonModel);

            cursor.moveToNext();
        }
        return loaiMonModelList;
    }

    public LoaiMonModel LayLoaiMonTheoMa(int maloai){
        LoaiMonModel loaiMonModel = new LoaiMonModel();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON+" WHERE "+CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiMonModel.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonModel.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonModel.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH)));

            cursor.moveToNext();
        }
        return loaiMonModel;
    }

}
