package com.yey.vcodevy;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

public class VCodeViewY extends FrameLayout {
    private final static String TAG = VCodeViewY.class.getName();
    private LinearLayout mContainerText;
    private PwdEditText mPet;
    private int mBoxNum;
    private int mBoxMargin;
    private float mBoxSizeRate;
    private int mBoxTextSize;
    private int mBoxFocus;
    private int mBoxNotFcous;
    private boolean mBoxPwdModle;
    private ArrayList<AppCompatTextView> mTextViewList;
    private int mBoxTextColor;
    private int mInputIndex;//输入索引
    private int mInputType;
    //明文属性转为密文属性 handler
    private Handler mRefreshHandler = new Handler(Looper.getMainLooper());

    public VCodeViewY(Context context) {
        this(context, null);
    }

    public VCodeViewY(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VCodeViewY(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initRes(context, attrs, defStyleAttr);
        creatView();
        initLister();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_vcodeviewy, this);
        mContainerText = (LinearLayout) findViewById(R.id.ll_text);
        mPet = (PwdEditText) findViewById(R.id.pet);
    }

    private void initRes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VCodeViewY, defStyleAttr, 0);
        mBoxNum = typedArray.getInteger(R.styleable.VCodeViewY_box_bum, 1);
        mBoxMargin = DensityUtil.dip2px(context, typedArray.getDimensionPixelSize(R.styleable.VCodeViewY_box_margin, 2));
        mBoxSizeRate = typedArray.getFloat(R.styleable.VCodeViewY_box_size_parent_height_rate, 0.5f);
        mBoxTextSize = DensityUtil.dip2px(context, typedArray.getDimensionPixelSize(R.styleable.VCodeViewY_box_text_size, 4));
        mBoxTextColor = typedArray.getColor(R.styleable.VCodeViewY_box_text_color, getResources().getColor(R.color.vcvy_balck));
        mBoxFocus = typedArray.getResourceId(R.styleable.VCodeViewY_box_focus, R.drawable.box_focus);
        mBoxNotFcous = typedArray.getResourceId(R.styleable.VCodeViewY_box_not_focus, R.drawable.box_notfoucs);
        mBoxPwdModle = typedArray.getBoolean(R.styleable.VCodeViewY_box_pwd_modle, false);
        mInputType = typedArray.getInt(R.styleable.VCodeViewY_box_input_type, 0);
        typedArray.recycle();
    }

    private void creatView() {
        initEditText();
        mTextViewList = new ArrayList<>();
        mTextViewList.clear();
        for (int i = 0; i < mBoxNum; i++) {
            //TODO 这里Text的大小与边距都没有设置, 在onLayout中去设置
            AppCompatTextView mTextView = new AppCompatTextView(getContext());
            mTextView.setTextColor(mBoxTextColor);
            mTextView.setGravity(Gravity.CENTER);
//            if (mBoxPwdModle){
//                //密码模式
//                mTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
            if (i == 0) {
                mTextView.setBackgroundResource(mBoxFocus);
            } else {
                mTextView.setBackgroundResource(mBoxNotFcous);
            }
            mTextViewList.add(mTextView);
            mContainerText.addView(mTextView);
        }

    }

    private void initEditText() {
        switch (mInputType) {
            case 0:
                mPet.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 1:
                mPet.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int mBoxSize = (int) (getHeight() * mBoxSizeRate);
        for (int i = 0; i < mBoxNum; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mBoxSize, mBoxSize);
            if (i != 0) {
                layoutParams.leftMargin = mBoxMargin;
            }
            mTextViewList.get(i).setLayoutParams(layoutParams);
        }
    }

    private boolean mInputComplete;//当输入完成之后, 再输入的话就不让输入了

    private void initLister() {
        mPet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence inputStr, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, editable.toString());
                if (!TextUtils.isEmpty(editable) && mInputIndex >= 0 && mInputIndex < mBoxNum && !mInputComplete) {
                    final AppCompatTextView notFouceTextView = mTextViewList.get(mInputIndex);
                    notFouceTextView.setText(editable);
                    notFouceTextView.setBackgroundResource(mBoxNotFcous);
                    if (mBoxPwdModle) {
                        mRefreshHandler.postDelayed(new Runnable() {// 将改变TextView属性为展示密文的属性，我改动的
                            @Override
                            public void run() {
                                notFouceTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                        }, 200);
                    }
                    mInputIndex++;
                    //当mIndex 值超出了索引最大值, 将mIndex --, 此时输入框完成输入.
                    if (mInputIndex > mBoxNum - 1) {
                        mInputIndex--;
                        mInputComplete = true;
                    }
                    AppCompatTextView fouceTextView = mTextViewList.get(mInputIndex);
                    fouceTextView.setBackgroundResource(mBoxFocus);
                    mPet.setText("");
                }
                Editable text = mPet.getText();
                if (!TextUtils.isEmpty(text)) {
                    mPet.setText("");
                }
            }
        });
        mPet.setBackSpaceListener(new TInputConnection.BackspaceListener() {
            @Override
            public boolean onBackspace() {
                if (mInputIndex >= 0 && mInputIndex < mBoxNum) {
                    //删除了一个空格, 此时可以再输入内容
                    mInputComplete = false;
                    AppCompatTextView mLastText = mTextViewList.get(mBoxNum - 1);
                    String mLastString = mLastText.getText().toString().trim();
                    if (!TextUtils.isEmpty(mLastString)) {
                        //此时输入完成
                        mLastText.setText("");
                        if (mBoxPwdModle) {
                            mLastText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    } else {
                        //如果用户此时还没输入完成进行删除
                        AppCompatTextView fouceTextView = mTextViewList.get(mInputIndex);
                        fouceTextView.setBackgroundResource(mBoxNotFcous);
                        mInputIndex--;
                        //当mIndex值比0还小, 将mIndex置为0
                        if (mInputIndex < 0) {
                            mInputIndex = 0;
                        }
                        AppCompatTextView notFouceTextView = mTextViewList.get(mInputIndex);
                        notFouceTextView.setText("");
                        notFouceTextView.setBackgroundResource(mBoxFocus);
                        if (mBoxPwdModle) {
                            notFouceTextView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                    AppCompatTextView fouceTextView = mTextViewList.get(mInputIndex);
                    fouceTextView.setBackgroundResource(mBoxFocus);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRefreshHandler.removeCallbacksAndMessages(null);
    }
}
