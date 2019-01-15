package com.wlf.commonlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlf.commonlib.R;
import com.wlf.commonlib.utils.ScreenUtils;

import java.util.UUID;

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

public class CommonRecycleViewItem extends RelativeLayout implements View.OnClickListener {
    public static final int TYPE_RIGHT_NONE = 0;
    public static final int TYPE_RIGHT_TEXTVIEW = 1;
    public static final int TYPE_RIGHT_IMAGE = 2;

    private final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    private int PADDING_5;
    private int PADDING_12;
    private Context context;


    /** 主视图 */
    private RelativeLayout rlMain;

    /** ------  分割线 ------ */

    /** 是否显示分割线 默认不显示 */
    private boolean showDividingLine;

    /** 分割线颜色 */
    private int dividingLineColor;

    /** 分割线高度 */
    private float dividingLineHeight;

    /** 分割线左边距 */
    private float dividingLineLeftPadding;

    /** 分割线右边距 */
    private float dividingLineRightPadding;

    /** 分割线 */
    private View dividingLine;



    /** ------  左边视图 ------ */

    /** 左边TextView文字 */
    private String leftText;

    /** 左边TextView颜色 */
    private int leftTextColor;

    /** 左边TextView文字大小 */
    private float leftTextSize;

    /** 左边TextView drawableLeft资源 */
    private int leftDrawable;

    /** 左边TextView drawablePadding */
    private float leftDrawablePadding;

    /** 左边 TextView */
    private TextView tvLeft;



    /** ------  右边视图 ------ */

    /** 右边视图类型 */
    private int rightType;

    /** 右边TextView文字 */
    private String rightText;

    /** 右边TextView颜色 */
    private int rightTextColor;

    /** 右边TextView文字大小 */
    private float rightTextSize;

    /** 右边图片资源 */
    private int rightImageResource;

    /** 右边 TextView */
    private TextView tvRight;

    /** 右边 ImageButton */
    private ImageButton ivBtnRight;

    ItemChildOnClickListener childOnClickListener;


    public CommonRecycleViewItem(Context context) {
        this(context, null);
    }

    public CommonRecycleViewItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonRecycleViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributeSet(context, attrs);
        initGlobalView(context);
        initMainView(context);
    }

    /**
     * 获取xml配置信息
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        PADDING_5 = ScreenUtils.dp2PxInt(context, 5);
        PADDING_12 = ScreenUtils.dp2PxInt(context, 12);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonRecycleViewItem);

        showDividingLine = array.getBoolean(R.styleable.CommonRecycleViewItem_showDividingLine, false);
        dividingLineColor = array.getColor(R.styleable.CommonRecycleViewItem_dividingLineColor, Color.parseColor("#d8d8d8"));
        dividingLineHeight = array.getDimension(R.styleable.CommonRecycleViewItem_dividingLineHeight, ScreenUtils.dp2PxInt(context, 0));
        dividingLineLeftPadding = array.getDimension(R.styleable.CommonRecycleViewItem_dividingLineLeftPadding, ScreenUtils.dp2PxInt(context, 0));
        dividingLineRightPadding = array.getDimension(R.styleable.CommonRecycleViewItem_dividingLineRightPadding, ScreenUtils.dp2PxInt(context, 0));

        leftText = array.getString(R.styleable.CommonRecycleViewItem_leftText);
        leftTextColor = array.getColor(R.styleable.CommonRecycleViewItem_leftText, Color.BLACK);
        leftTextSize = array.getDimension(R.styleable.CommonRecycleViewItem_leftTextSize, ScreenUtils.dp2PxInt(context, 15));
        leftDrawable = array.getResourceId(R.styleable.CommonRecycleViewItem_leftDrawable, 0);
        leftDrawablePadding = array.getDimension(R.styleable.CommonRecycleViewItem_leftDrawablePadding, 5);

        rightType = array.getInt(R.styleable.CommonRecycleViewItem_rightType, TYPE_RIGHT_NONE);
        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            rightText = array.getString(R.styleable.CommonRecycleViewItem_rightText);
            rightTextColor = array.getColor(R.styleable.CommonRecycleViewItem_rightTextColor, Color.BLACK);
            rightTextSize = array.getDimension(R.styleable.CommonRecycleViewItem_rightTextSize, ScreenUtils.dp2PxInt(context, 15));
        } else if (rightType == TYPE_RIGHT_IMAGE) {
            rightImageResource = array.getResourceId(R.styleable.CommonRecycleViewItem_rightImageResource, 0);
        }

        array.recycle();
    }

    /**
     * 初始化全局视图
     *
     * @param context       上下文
     */
    private void initGlobalView(Context context) {
        ViewGroup.LayoutParams globalParams = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setLayoutParams(globalParams);

        // 构建主视图
        rlMain = new RelativeLayout(context);
        rlMain.setId(getGenerateViewId());
        rlMain.setBackgroundColor(Color.WHITE);
        LayoutParams mainParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        // 计算主布局高度
        if (showDividingLine) {
            mainParams.height = MATCH_PARENT - Math.max(1, ScreenUtils.dp2PxInt(context, 0.4f));
        } else {
            mainParams.height = MATCH_PARENT;
        }
        addView(rlMain, mainParams);

        // 构建分割线视图
        if (showDividingLine) {
            // 已设置显示标题栏分隔线,5.0以下机型,显示分隔线
            dividingLine = new View(context);
            dividingLine.setBackgroundColor(dividingLineColor);
            LayoutParams dividingLineParams = new LayoutParams(MATCH_PARENT, Math.max(1, ScreenUtils.dp2PxInt(context, 0.4f)));
            dividingLineParams.setMargins(Math.round(dividingLineLeftPadding), 0, Math.round(dividingLineRightPadding), 0);
            dividingLineParams.addRule(RelativeLayout.BELOW, rlMain.getId());
            addView(dividingLine, dividingLineParams);
        }
    }

    /**
     * 初始化主视图
     *
     * @param context       上下文
     */
    private void initMainView(Context context) {
        initMainLeftViews(context);
        if (rightType != TYPE_RIGHT_NONE) {
            initMainRightViews(context);
        }
    }

    /**
     * 初始化主视图左边部分
     *
     * @param context       上下文
     */
    private void initMainLeftViews(Context context) {
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        // 初始化左边TextView
        tvLeft = new TextView(context);
        tvLeft.setId(getGenerateViewId());
        tvLeft.setText(leftText);
        tvLeft.setTextColor(leftTextColor);
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        tvLeft.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        tvLeft.setSingleLine(true);
        // 设置DrawableLeft及DrawablePadding
        if (leftDrawable != 0) {
            tvLeft.setCompoundDrawablePadding((int) leftDrawablePadding);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, 0, 0, 0);
            } else {
                tvLeft.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0);
            }
        }
        tvLeft.setPadding(PADDING_12, 0, PADDING_12, 0);
        rlMain.addView(tvLeft, leftInnerParams);
    }

    /**
     * 初始化主视图右边部分
     *
     * @param context       上下文
     */
    private void initMainRightViews(Context context) {
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            // 初始化右边TextView
            tvRight = new TextView(context);
            tvRight.setId(getGenerateViewId());
            tvRight.setText(rightText);
            tvRight.setTextColor(rightTextColor);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
            tvRight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            tvRight.setSingleLine(true);
            tvRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            tvRight.setOnClickListener(this);
            rlMain.addView(tvRight, rightInnerParams);
        } else if (rightType == TYPE_RIGHT_IMAGE) {
            // 初始化右边ImageButton
            ivBtnRight = new ImageButton(context);
            ivBtnRight.setId(getGenerateViewId());
            ivBtnRight.setImageResource(rightImageResource);
            ivBtnRight.setBackgroundColor(Color.TRANSPARENT);
            ivBtnRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ivBtnRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            ivBtnRight.setOnClickListener(this);
            rlMain.addView(ivBtnRight, rightInnerParams);
        }
    }


    /**
     * 计算View Id
     *
     * @return
     */
    private int getGenerateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    @Override
    public void setBackgroundColor(int color) {
        rlMain.setBackgroundColor(color);
    }

    /**
     * 设置背景图片
     *
     * @param resource
     */
    @Override
    public void setBackgroundResource(int resource) {
        setBackgroundColor(Color.TRANSPARENT);
        super.setBackgroundResource(resource);
    }

    /**
     * 获取分割线
     *
     * @return
     */
    public View getDividingLine() {
        return dividingLine;
    }

    /**
     * 获取左边TextView
     *
     * @return
     */
    public TextView getLeftTextView() {
        return tvLeft;
    }

    /**
     * 获取标题栏右边TextView，对应rightType = textView
     *
     * @return
     */
    public TextView getRightTextView() {
        return tvRight;
    }

    /**
     * 获取标题栏右边ImageButton，对应rightType = imageButton
     *
     * @return
     */
    public ImageButton getRightImageButton() {
        return ivBtnRight;
    }

    /**
     * 设置左边TextView 图片
     *
     * @param resId 图片id
     */
    public void setLeftTextDrawable(int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(resId, 0, 0, 0);
        } else {
            tvLeft.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        }
    }

    /**
     * 设置右边TextView 图片 (图右文左)
     *
     * @param resId 图片id
     */
    public void setRightTextDrawable (int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tvRight.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, resId, 0);
        } else {
            tvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
    }

    /**
     * 设置左边TextView 文本
     *
     * @param text 文本
     */
    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    /**
     * 设置左边TextView 颜色
     *
     * @param color 颜色
     */
    public void setLeftTextColor (int color) {
        tvLeft.setTextColor(ContextCompat.getColor(context, color));
    }

    /**
     * 设置左边TextView 文字大小
     *
     * @param size 大小
     */
    public void setLeftTextSize (int size) {
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置右边TextView 文本
     *
     * @param text 文本
     */
    public void setRightText (String text) {
        tvRight.setText(text);
    }

    /**
     * 设置右边TextView 颜色
     *
     * @param color 颜色
     */
    public void setRightTextColor (int color){
        tvRight.setTextColor(ContextCompat.getColor(context, color));
    }

    /**
     * 设置右边TextView 文字大小
     *
     * @param size 大小
     */
    public void setRightTextSize (int size) {
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置child 事件监听
     *
     * @param childOnClickListener 事件监听
     */
    public void setChildOnClickListener (ItemChildOnClickListener childOnClickListener) {
        this.childOnClickListener = childOnClickListener;
    }

    @Override
    public void onClick(View v) {
        if (childOnClickListener == null) {
            return;
        }

        if (v.equals(tvLeft)) {
            childOnClickListener.childOnClick(v, ACTION_LEFT_TEXT);
        }

        if (v.equals(tvRight)) {
            childOnClickListener.childOnClick(v, ACTION_RIGHT_TEXT);
        }

        if (v.equals(ivBtnRight)) {
            childOnClickListener.childOnClick(v, ACTION_RIGHT_BUTTON);
        }
    }

    /** 左边TextView被点击 */
    public static final int ACTION_LEFT_TEXT = 1;

    /** 右边TextView被点击 */
    public static final int ACTION_RIGHT_TEXT = 2;

    /** 右边ImageBtn被点击 */
    public static final int ACTION_RIGHT_BUTTON = 3;


    public interface  ItemChildOnClickListener {
        void childOnClick (View view, int action);
    }
}
