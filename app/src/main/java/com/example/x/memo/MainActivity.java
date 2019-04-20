package com.example.x.memo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.x.memo.Base.PermissionSetting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.x.memo.Base.MemoData;
import com.example.x.memo.Base.MemoList;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<MemoData> memoDataList;
    private MemoAdapter memoAdapter;
    private RecyclerView recyclerView;
    private PermissionSetting mPermissionSetting;

    private String[] Permissions=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//View.SYSTEM_UI_FLAG_LAYOUT_STABLE   View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        recyclerView = (RecyclerView) findViewById(R.id.memo_Rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mPermissionSetting=new PermissionSetting(MainActivity.this);

        ImageButton search=(ImageButton)findViewById(R.id.search);
        search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(MainActivity.this,LockActivity.class);
                MainActivity.this.startActivity(intent);
                return true;
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "请授予权限以正常使用应用", Toast.LENGTH_SHORT).show();
                    mPermissionSetting.start();
                }
                else {
                    MemoData memoData = new MemoData();
                    MemoList.get(MainActivity.this).addMemo(memoData);
                    int i = memoData.getmId();
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("edit", i);
                    MainActivity.this.startActivity(intent);
                }

            }
        });

        checkPermission();

        //initMemo();

        /*
        memoAdapter.setListener(new MemoAdapter.onclickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String i = memoAdapter.getItemSid(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                MainActivity.this.startActivity(intent);
            }
        });

        recyclerView.setItemViewSwipeEnabled(true);

        recyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return false;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                // 此方法在Item在侧滑删除时被调用。
                int position = srcHolder.getAdapterPosition();
                String s=memoAdapter.getItemSid(position);
                MemoList.get(MainActivity.this).deleteMemo(s);
                memoDataList=MemoList.get(MainActivity.this).getMemos("false");
                memoAdapter.setMemoDataList(memoDataList);
                memoAdapter.notifyItemRemoved(position);
            }
        });
        */
    }

    private void checkPermission(){
        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < Permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(Permissions[i]);
            }
        }
        if (!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        else {
            initMemo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initMemo();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();//没有权限
                    mPermissionSetting.start();//跳转应用权限设置界面
                }
                break;
            default:
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        memoDataList=MemoList.get(MainActivity.this).getMemos(0);
        memoAdapter.setMemoDataList(memoDataList);
        memoAdapter.notifyDataSetChanged();
    }

    private void initMemo() {

        memoDataList=MemoList.get(MainActivity.this).getMemos(0);
        memoAdapter=new MemoAdapter(memoDataList);
        recyclerView.setAdapter(memoAdapter);
        memoAdapter.setMemoDataList(memoDataList);
        memoAdapter.notifyDataSetChanged();
    }
}
