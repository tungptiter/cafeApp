package com.sinhvien.orderdrinkapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sinhvien.orderdrinkapp.Activities.AddMenuActivity;
import com.sinhvien.orderdrinkapp.Activities.AmountMenuActivity;
import com.sinhvien.orderdrinkapp.Activities.HomeActivity;
import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterDisplayMenu;
import com.sinhvien.orderdrinkapp.CONTROLLER.MonController;
import com.sinhvien.orderdrinkapp.MODEL.MonModel;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class DisplayMenuFragment extends Fragment {

    int maloai, maban;
    String tenloai,tinhtrang;
    GridView gvDisplayMenu;
    MonController monController;
    List<MonModel> monModelList;
    AdapterDisplayMenu adapterDisplayMenu;
    int maquyen = 0;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themmon"))
                        {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Th??m th???t b???i",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"S???a th??nh c??ng",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"S???a th???t b???i",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaymenu_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Qu???n l?? th???c ????n");
        monController = new MonController(getActivity());

        gvDisplayMenu = (GridView)view.findViewById(R.id.gvDisplayMenu);
        // L???y file share m?? quy???n
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);


        Bundle bundle = getArguments();
        if(bundle !=null){
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvDisplayMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //n???u l???y ??c m?? b??n m???i m???
                    tinhtrang = monModelList.get(position).getTinhTrang();
                    if(maban != 0){
                        if(tinhtrang.equals("true")){
//                          so luong
                            Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                            iAmount.putExtra("maban",maban);
                            iAmount.putExtra("mamon", monModelList.get(position).getMaMon());
                            startActivity(iAmount);
                        }else {
                            Toast.makeText(getActivity(),"M??n ???? h???t, kh??ng th??? th??m", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                     getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    //t???o 1 menu context show l???a ch???n
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //T???o ph???n s???a v?? x??a trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = monModelList.get(vitri).getMaMon();

        switch (id){

            case R.id.itEdit:
                // chec quy???n
                if(maquyen == 1){

                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("mamon",mamon);
                iEdit.putExtra("maLoai",maloai);
                iEdit.putExtra("tenLoai",tenloai);
                resultLauncherMenu.launch(iEdit);
                }else{
                    Toast.makeText(getActivity(),"B???n kh??ng c?? quy???n s???a",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.itDelete:
                // check quyen
                if(maquyen == 1){

                    boolean ktra = monController.XoaMon(mamon);
                    if(ktra){
                        HienThiDSMon();
                        Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                                ,Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                                ,Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"B???n kh??ng c?? quy???n x??a",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("maLoai",maloai);
                intent.putExtra("tenLoai",tenloai);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void HienThiDSMon(){
        monModelList = monController.LayDSMonTheoLoai(maloai);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(),R.layout.custom_layout_displaymenu, monModelList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }

}
