package com.wlf.commonrecycleviewitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlf.commonrecycleviewitem.adapter.Adapter;
import com.wlf.commonrecycleviewitem.bean.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    void initView () {
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        Adapter adapter = new Adapter(getData());
        recyclerView.setAdapter(adapter);
    }

    List<Data> getData () {
        List<Data> list = new ArrayList<>();
        for (int i = 0; i < 8; i ++){
            Data data = new Data();
            data.setTitle("第" + i + "行");
            data.setDesc("" + i);
            data.setImageId(R.mipmap.ic_launcher);
            list.add(data);
        }
        return list;
    }
}
