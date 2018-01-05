package com.tuo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * description: 自定义view 验证码 输入框
 * Created by Jack on 2017/6/2.
 * 邮箱：839539179@qq.com
 */

public class VerificationCodeView extends RelativeLayout {

    private static final int DEF_ET_NUM = 1;
    private static final int DEF_ET_WIDTH = 42;
    private static final int DEF_ET_TEXT_SIZE = 16;
    private static final int DEF_ET_TEXT_COLOR = Color.BLACK;

    private LinearLayout containerEt;

    private EditText et;
    // 输入框数量
    private int mEtNumber;
    // 输入框的宽度
    private int mEtWidth;
    //输入框分割线
    private Drawable mEtDividerDrawable;
    //输入框文字颜色
    private int mEtTextColor;
    //输入框文字大小
    private float mEtTextSize;
    //输入框获取焦点时背景
    private Drawable mEtBackgroundDrawableFocus;
    // 输入框没有焦点时背景
    private Drawable mEtBackgroundDrawableNormal;

    //存储TextView的数据 数量由自定义控件的属性传入
    private TextView[] mTextViews;

    private MyTextWatcher myTextWatcher = new MyTextWatcher();


    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //初始化 布局和属性
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_identifying_code, this);
        containerEt = findViewById(R.id.container_et);
        et = findViewById(R.id.et);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        mEtNumber = typedArray.getInteger(R.styleable.VerificationCodeView_icv_et_number, DEF_ET_NUM);
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_width, DEF_ET_WIDTH);
        mEtDividerDrawable = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_divider_drawable);
        mEtTextSize = typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_text_size, DEF_ET_TEXT_SIZE);
        mEtTextColor = typedArray.getColor(R.styleable.VerificationCodeView_icv_et_text_color, DEF_ET_TEXT_COLOR);
        mEtBackgroundDrawableFocus = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_focus);
        mEtBackgroundDrawableNormal = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_normal);
        //释放资源
        typedArray.recycle();


        // 当xml中未配置时 这里进行初始配置默认图片
        if (mEtDividerDrawable == null) {
            mEtDividerDrawable = context.getResources().getDrawable(R.drawable.shape_divider_identifying);
        }

        if (mEtBackgroundDrawableFocus == null) {
            mEtBackgroundDrawableFocus = context.getResources().getDrawable(R.drawable.shape_icv_et_bg_focus);
        }

        if (mEtBackgroundDrawableNormal == null) {
            mEtBackgroundDrawableNormal = context.getResources().getDrawable(R.drawable.shape_icv_et_bg_normal);
        }

        initUI();
    }

    // 初始UI
    private void initUI() {
        initTextViews(getContext(), mEtNumber, mEtWidth, mEtDividerDrawable, mEtTextSize, mEtTextColor);
        initEtContainer(mTextViews);
        setListener();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置当 高为 warpContent 模式时的默认值 为 50dp
        int mHeightMeasureSpec = heightMeasureSpec;

        int heightMode = MeasureSpec.getMode(mHeightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            mHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int) dp2px(50, getContext()), MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, mHeightMeasureSpec);
    }


    //初始化TextView
    private void initTextViews(Context context, int etNumber, int etWidth, Drawable etDividerDrawable, float etTextSize, int etTextColor) {
        // 设置 editText 的输入长度
        et.setCursorVisible(false);//将光标隐藏
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(etNumber)}); //最大输入长度
        // 设置分割线的宽度
        if (etDividerDrawable != null) {
            etDividerDrawable.setBounds(0, 0, etDividerDrawable.getMinimumWidth(), etDividerDrawable.getMinimumHeight());
            containerEt.setDividerDrawable(etDividerDrawable);
        }
        mTextViews = new TextView[etNumber];
        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = new TextView(context);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, etTextSize);
            textView.setTextColor(etTextColor);
            textView.setWidth(etWidth);
            textView.setHeight(etWidth);
            if (i == 0) {
                textView.setBackgroundDrawable(mEtBackgroundDrawableFocus);
            } else {
                textView.setBackgroundDrawable(mEtBackgroundDrawableNormal);
            }
            textView.setGravity(Gravity.CENTER);

            textView.setFocusable(false);

            mTextViews[i] = textView;
        }
    }

    //初始化存储TextView 的容器
    private void initEtContainer(TextView[] mTextViews) {
        for (TextView mTextView : mTextViews) {
            containerEt.addView(mTextView);
        }
    }


    private void setListener() {
        // 监听输入内容
        et.addTextChangedListener(myTextWatcher);

        // 监听删除按键
        et.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onKeyDelete();
                    return true;
                }
                return false;
            }
        });
    }


    // 给TextView 设置文字
    private void setText(String inputContent) {
        for (int i = 0; i < mTextViews.length; i++) {
            TextView tv = mTextViews[i];
            if (tv.getText().toString().trim().equals("")) {
                tv.setText(inputContent);
                // 添加输入完成的监听
                if (inputCompleteListener != null) {
                    inputCompleteListener.inputComplete();
                }
                tv.setBackgroundDrawable(mEtBackgroundDrawableNormal);
                if (i < mEtNumber - 1) {
                    mTextViews[i + 1].setBackgroundDrawable(mEtBackgroundDrawableFocus);
                }
                break;
            }
        }
    }

    // 监听删除
    private void onKeyDelete() {
        for (int i = mTextViews.length - 1; i >= 0; i--) {
            TextView tv = mTextViews[i];
            if (!tv.getText().toString().trim().equals("")) {
                tv.setText("");
                // 添加删除完成监听
                if (inputCompleteListener != null) {
                    inputCompleteListener.deleteContent();
                }
                tv.setBackgroundDrawable(mEtBackgroundDrawableFocus);
                if (i < mEtNumber - 1) {
                    mTextViews[i + 1].setBackgroundDrawable(mEtBackgroundDrawableNormal);
                }
                break;
            }
        }
    }


    /**
     * 获取输入文本
     *
     * @return string
     */
    public String getInputContent() {
        StringBuilder buffer = new StringBuilder();
        for (TextView tv : mTextViews) {
            buffer.append(tv.getText().toString().trim());
        }
        return buffer.toString();
    }

    /**
     * 删除输入内容
     */
    public void clearInputContent() {
        for (int i = 0; i < mTextViews.length; i++) {
            if (i == 0) {
                mTextViews[i].setBackgroundDrawable(mEtBackgroundDrawableFocus);
            } else {
                mTextViews[i].setBackgroundDrawable(mEtBackgroundDrawableNormal);
            }
            mTextViews[i].setText("");
        }
    }

    /**
     * 设置输入框个数
     */
    public void setEtNumber(int etNumber) {
        this.mEtNumber = etNumber;
        et.removeTextChangedListener(myTextWatcher);
        containerEt.removeAllViews();
        initUI();
    }

    /**
     * 获取内部的EditText。
     * 原因：控制键盘的显示和隐藏，在Fragment中，键盘是不会自动弹出的。
     */
    public EditText getEditText() {
        return et;
    }


    /**
     * 获取输入的位数
     *
     * @return int
     */
    public int getEtNumber() {
        return mEtNumber;
    }

    // 输入完成 和 删除成功 的监听
    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent();
    }


    private float dp2px(float dpValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String inputStr = editable.toString();
            if (!TextUtils.isEmpty(inputStr)) {
                setText(inputStr);
                et.setText("");
            }
        }
    }


}
