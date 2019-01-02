package com.tuo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
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

    private LinearLayout containerEt;

    private PwdEditText et;
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
    //输入框没有焦点时背景
    private Drawable mEtBackgroundDrawableNormal;
    //是否是密码模式
    private boolean mEtPwd;
    //密码模式时圆的半径
//    private float mEtPwdRadius;
    //输入框查看明文密文按钮打开时候的图标
    private Drawable mEtToggleIconOpen;
    //输入框查看明文密文按钮关闭时候的图标
    private Drawable mEtToggleIconClose;
    //存储TextView的数据 数量由自定义控件的属性传入
    private TextView[] mTextViews;

    // 密码模式
    private int mEtPwdMode;

    private MyTextWatcher myTextWatcher = new MyTextWatcher();

    //明文属性转为密文属性 handler
    private Handler mRefreshHandler = new Handler(Looper.getMainLooper());


    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    //初始化 布局和属性
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_identifying_code, this);
        containerEt = (LinearLayout) this.findViewById(R.id.container_et);
        et = (PwdEditText) this.findViewById(R.id.et);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView, defStyleAttr, 0);
        mEtNumber = typedArray.getInteger(R.styleable.VerificationCodeView_icv_et_number, 1);
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_width, 42);
        mEtDividerDrawable = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_divider_drawable);
        mEtTextSize = typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_text_size, (int) sp2px(16, context));
        mEtTextColor = typedArray.getColor(R.styleable.VerificationCodeView_icv_et_text_color, Color.BLACK);
        mEtBackgroundDrawableFocus = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_focus);
        mEtBackgroundDrawableNormal = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_bg_normal);
        mEtPwd = typedArray.getBoolean(R.styleable.VerificationCodeView_icv_et_pwd, false);
        mEtToggleIconOpen = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_toggle_icon_open);
        mEtToggleIconClose = typedArray.getDrawable(R.styleable.VerificationCodeView_icv_et_toggle_icon_close);
//        mEtPwdRadius = typedArray.getDimensionPixelSize(R.styleable.VerificationCodeView_icv_et_pwd_radius, 0);

        mEtPwdMode = typedArray.getInteger(R.styleable.VerificationCodeView_icv_et_pwd_mode, 1);

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
        mTextViews = new TextView[etNumber]; //创建一个储存输入的数据 TextView 控件数组
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
            if (mEtToggleIconOpen != null && mEtToggleIconClose != null) {
                this.findViewById(R.id.btn_watch_paw).setVisibility(VISIBLE);
                this.findViewById(R.id.btn_watch_paw).setBackgroundDrawable(mEtToggleIconClose);
            }
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

        this.findViewById(R.id.btn_watch_paw).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtToggleIconOpen != null && mEtToggleIconClose != null) {
                    if (!mEtPwd) {
                        //当VerificationCodeView 中最后一个TextView也写入了数据，明文和密码切换按钮才生效
                        if (!TextUtils.isEmpty(mTextViews[mEtNumber - 1].getText().toString().trim())) {
                            showCiphertext();
                            mEtPwd = true;
                        }
                    } else {
                        if (!TextUtils.isEmpty(mTextViews[mEtNumber - 1].getText().toString().trim())) {
                            showPlaintext();
                            mEtPwd = false;
                        }
                    }
                }
            }
        });
    }

    /**
     * 明文显示VerificationCodeView 中的内容
     */
    private void showPlaintext() {
        for (int i = 0; i < mTextViews.length; i++) {
            final TextView tv = mTextViews[i];
            mRefreshHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                }
            }, 0);
        }
    }

    /**
     * 密文显示VerificationCodeView中的内容
     */
    private void showCiphertext() {
        for (int i = 0; i < mTextViews.length; i++) {
            final TextView tv = mTextViews[i];
            mRefreshHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv.setTransformationMethod(new CustomPasswordTransformationMethod(mEtPwdMode));
                }
            }, 0);
        }
    }


    // 给TextView 设置文字
    private void setText(String inputContent) {
        for (int i = 0; i < mTextViews.length; i++) {
            final TextView tv = mTextViews[i];
            if (tv.getText().toString().trim().equals("")) {
                tv.setText(inputContent);
                if (mEtPwd) {
                    mRefreshHandler.postDelayed(new Runnable() {// 将改变TextView属性为展示密文的属性，我改动的
                        @Override
                        public void run() {
                            tv.setTransformationMethod(new CustomPasswordTransformationMethod(mEtPwdMode));
                        }
                    }, 200);
                } else {
                    mRefreshHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                        }
                    }, 0);
                }
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
        if (!TextUtils.isEmpty(mTextViews[mEtNumber - 1].getText().toString())) {
            if (mEtToggleIconOpen != null && mEtToggleIconClose != null) {
                this.findViewById(R.id.btn_watch_paw).setVisibility(VISIBLE);
                this.findViewById(R.id.btn_watch_paw).setBackgroundDrawable(mEtToggleIconOpen);
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
        if (TextUtils.isEmpty(mTextViews[mEtNumber - 1].getText().toString())) {
            if (mEtToggleIconOpen != null && mEtToggleIconClose != null) {
                this.findViewById(R.id.btn_watch_paw).setVisibility(VISIBLE);
                this.findViewById(R.id.btn_watch_paw).setBackgroundDrawable(mEtToggleIconClose);
            }
        }
    }


    /**
     * 获取输入文本
     *
     * @return string
     */
    public String getInputContent() {
        StringBuffer buffer = new StringBuffer();
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
     *
     * @param etNumber
     */
    public void setEtNumber(int etNumber) {
        this.mEtNumber = etNumber;
        et.removeTextChangedListener(myTextWatcher);
        containerEt.removeAllViews();
        initUI();
    }


    /**
     * 获取输入的位数
     *
     * @return int
     */
    public int getEtNumber() {
        return mEtNumber;
    }


    /**
     * 设置是否是密码模式 默认不是
     *
     * @param isPwdMode
     */
    public void setPwdMode(boolean isPwdMode) {
        this.mEtPwd = isPwdMode;
    }


    /**
     * 获取输入的EditText 用于外界设置键盘弹出
     *
     * @return EditText
     */
    public EditText getEditText() {
        return et;
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


    public float dp2px(float dpValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    public float sp2px(float spValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
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
                String[] strArray = inputStr.split("");
                for (int i = 0; i < strArray.length; i++) {
                    // 不能大于输入框个数
                    if (i > mEtNumber) {
                        break;
                    }
                    setText(strArray[i]);
                    et.setText("");
                }
            }
        }
    }
}
