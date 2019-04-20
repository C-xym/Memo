package com.example.x.memo;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.x.memo.Base.MemoData;
import com.example.x.memo.Base.MemoList;

import java.util.List;


public class HideActivity extends AppCompatActivity {

    public List<MemoData> memoDataList;
    private MemoAdapter memoAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//View.SYSTEM_UI_FLAG_LAYOUT_STABLE   View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        recyclerView = (RecyclerView) findViewById(R.id.memo_Rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemoData memoData = new MemoData();
                memoData.setmHide(true);
                MemoList.get(HideActivity.this).addMemo(memoData);
                int i = memoData.getmId();
                Intent intent = new Intent(HideActivity.this, EditActivity.class);
                intent.putExtra("edit", i);
                HideActivity.this.startActivity(intent);
            }
        });

        initMemo();




    }
    @Override
    protected void onStart() {
        super.onStart();
        initMemo();
    }

    private void initMemo() {

        memoDataList=MemoList.get(HideActivity.this).getMemos(1);
        if(memoAdapter==null)
        {
            memoAdapter=new MemoAdapter(memoDataList);
            recyclerView.setAdapter(memoAdapter);
        }
        else {
            memoAdapter.setMemoDataList(memoDataList);
            memoAdapter.notifyDataSetChanged();
        }
    }
}
