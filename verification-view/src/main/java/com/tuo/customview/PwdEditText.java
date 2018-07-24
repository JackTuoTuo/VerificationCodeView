package com.tuo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * author slimvan
 * email: slimvan@163.com
 * date 2018/7/24.
 * desc: 重写InputConnection 监听返回键
 */

public class PwdEditText extends android.support.v7.widget.AppCompatEditText {
    private TInputConnection inputConnection;

    public PwdEditText(Context context) {
        super(context);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inputConnection = new TInputConnection(null,true);
    }

    /**
     * 当输入法和EditText建立连接的时候会通过这个方法返回一个InputConnection。
     * 我们需要代理这个方法的父类方法生成的InputConnection并返回我们自己的代理类。
     * */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        inputConnection.setTarget(super.onCreateInputConnection(outAttrs));
        return inputConnection;
    }

    public void setBackSpaceListener(TInputConnection.BackspaceListener backSpaceLisetener){
        inputConnection.setBackspaceListener(backSpaceLisetener);
    }
}
