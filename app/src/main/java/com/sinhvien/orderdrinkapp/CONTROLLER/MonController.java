package com.sinhvien.orderdrinkapp.CONTROLLER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.orderdrinkapp.MODEL.MonModel;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonController {
    SQLiteDatabase database;
    public MonController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(MonModel monModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monModel.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monModel.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monModel.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monModel.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_MON,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_MON,CreateDatabase.TBL_MON_MAMON+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(MonModel monModel, int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI, monModel.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON, monModel.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN, monModel.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH, monModel.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG, monModel.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_MON,contentValues,
                CreateDatabase.TBL_MON_MAMON+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<MonModel> LayDSMonTheoLoai(int maloai){
        List<MonModel> monModelList = new ArrayList<MonModel>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_MON+ " WHERE " +CreateDatabase.TBL_MON_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonModel monModel = new MonModel();
            monModel.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monModel.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monModel.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monModel.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monModel.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monModel.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));
            monModelList.add(monModel);

            cursor.moveToNext();
        }
        return monModelList;
    }

    public MonModel LayMonTheoMa(int mamon){
        MonModel monModel = new MonModel();
        String query = "SELECT * FROM "+CreateDatabase.TBL_MON+" WHERE "+CreateDatabase.TBL_MON_MAMON+" = "+mamon;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            monModel.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH)));
            monModel.setTenMon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON)));
            monModel.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI)));
            monModel.setMaMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON)));
            monModel.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN)));
            monModel.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG)));

            cursor.moveToNext();
        }
        return monModel;
    }

}
