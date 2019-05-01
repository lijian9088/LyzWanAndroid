package com.lyz.lyzwanandroid.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/04/16
 * @Describe
 */
public class TreeData implements Parcelable {

    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public boolean userControlSetTop;
    public int visible;
    public List<TreeData> children;

    @Override
    public String toString() {
        return "TreeData{" +
                "courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                ", children=" + children +
                '}';
    }

    protected TreeData(Parcel in) {
        courseId = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        userControlSetTop = in.readByte() != 0;
        visible = in.readInt();
        children = in.createTypedArrayList(TreeData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(parentChapterId);
        dest.writeByte((byte) (userControlSetTop ? 1 : 0));
        dest.writeInt(visible);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TreeData> CREATOR = new Creator<TreeData>() {
        @Override
        public TreeData createFromParcel(Parcel in) {
            return new TreeData(in);
        }

        @Override
        public TreeData[] newArray(int size) {
            return new TreeData[size];
        }
    };
}
