package com.wutianyi.DataMing_ID3;

import java.util.ArrayList;

/**
 * Created by hanjiewu on 2016/3/2.
 */
public class AttrNode {
    //当前属性的名字
    private String attrName;
    //父节点的分类属性值
    private String parentAttrValue;
    //属性子节点
    private AttrNode[] childAttrNode;
    //孩子叶子节点
    private ArrayList<String> childDataIndex;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public AttrNode[] getChildAttrNode() {
        return childAttrNode;
    }

    public void setChildAttrNode(AttrNode[] childAttrNode) {
        this.childAttrNode = childAttrNode;
    }

    public String getParentAttrValue() {
        return parentAttrValue;
    }

    public void setParentAttrValue(String parentAttrValue) {
        this.parentAttrValue = parentAttrValue;
    }

    public ArrayList<String> getChildDataIndex() {
        return childDataIndex;
    }

    public void setChildDataIndex(ArrayList<String> childDataIndex) {
        this.childDataIndex = childDataIndex;
    }
}
