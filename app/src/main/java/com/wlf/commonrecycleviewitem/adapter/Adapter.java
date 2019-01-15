package com.wlf.commonrecycleviewitem.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlf.commonlib.widget.CommonRecycleViewItem;
import com.wlf.commonrecycleviewitem.R;
import com.wlf.commonrecycleviewitem.bean.Data;

import java.util.List;

import static com.wlf.commonlib.widget.CommonRecycleViewItem.ACTION_RIGHT_BUTTON;
import static com.wlf.commonlib.widget.CommonRecycleViewItem.ACTION_RIGHT_TEXT;

/**
 * =============================================
 *
 * @author: wlf
 * @date: 2019/1/14
 * @eamil: 845107244@qq.com
 * 描述:
 * 备注:
 * =============================================
 */

public class Adapter extends BaseQuickAdapter<Data, BaseViewHolder> {

    public Adapter(List<Data> list) {
        super(R.layout.item_recycleview, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Data item) {
        boolean b = helper.getLayoutPosition() % 2 == 0;
        CommonRecycleViewItem recycleViewItem = helper.getView(R.id.recycleview_item);
        recycleViewItem.setChildOnClickListener(childOnClickListener);
        recycleViewItem.setLeftText(item.getTitle());
        recycleViewItem.setLeftTextDrawable(item.getImageId());
        recycleViewItem.setRightText(b ? "" : item.getDesc());
        if (b){
            recycleViewItem.setRightTextDrawable(R.mipmap.icon_arrow_right);
        }
    }

    CommonRecycleViewItem.ItemChildOnClickListener childOnClickListener = new CommonRecycleViewItem.ItemChildOnClickListener() {
        @Override
        public void childOnClick(View view, int action) {
            if (action == ACTION_RIGHT_TEXT) {
                Log.e("----", view.getId() + "ACTION_RIGHT_TEXT");
            }

            if (action == ACTION_RIGHT_BUTTON) {
                Log.e("----", view.getId() + "ACTION_RIGHT_BUTTON");
            }
        }
    };
}
