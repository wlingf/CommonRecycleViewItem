package com.wlf.commonrecycleviewitem.bean;

import java.io.Serializable;

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

public class Data implements Serializable {

    private int imageId;

    private String title;

    private String desc;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title == null || "null".equals(title) ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc == null || "null".equals(desc) ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
