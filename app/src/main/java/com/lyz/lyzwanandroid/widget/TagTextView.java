package com.lyz.lyzwanandroid.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.lyz.lyzwanandroid.R;


/**
 * @author liyanze
 * @create 2019/03/19
 * @Describe
 */
public class TagTextView extends android.support.v7.widget.AppCompatTextView {

    private float textSize = 16f;
    private int padding = 10;

    private TagTextView(Context context) {
        this(context, null);
    }

    private TagTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TagTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_selector));
        setGravity(TEXT_ALIGNMENT_CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        setTextColor(Color.BLACK);
        setPadding(padding, padding, padding, padding);
    }

    public static TagTextView newInstance(Context context) {
        return new TagTextView(context);
    }

}
