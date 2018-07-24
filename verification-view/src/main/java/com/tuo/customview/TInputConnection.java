package com.tuo.customview;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

/**
 * author slimvan
 * email: slimvan@163.com
 * date 2018/7/24.
 * desc: 参考https://blog.csdn.net/frained/article/details/78880579
 */

public class TInputConnection extends InputConnectionWrapper {

    private BackspaceListener mBackspaceListener;

    /**
     * Initializes a wrapper.
     * <p>
     * <p><b>Caveat:</b> Although the system can accept {@code (InputConnection) null} in some
     * places, you cannot emulate such a behavior by non-null {@link InputConnectionWrapper} that
     * has {@code null} in {@code target}.</p>
     *
     * @param target  the {@link InputConnection} to be proxied.
     * @param mutable set {@code true} to protect this object from being reconfigured to target
     *                another {@link InputConnection}.  Note that this is ignored while the target is {@code null}.
     */
    public TInputConnection(InputConnection target, boolean mutable) {
        super(target, mutable);
    }

    public interface BackspaceListener {
        /**
         * @return true 代表消费了这个事件
         * */
        boolean onBackspace();
    }

    /**
     * 当软键盘删除文本之前，会调用这个方法通知输入框，我们可以重写这个方法并判断是否要拦截这个删除事件。
     * 在谷歌输入法上，点击退格键的时候不会调用{@link #sendKeyEvent(KeyEvent event)}，
     * 而是直接回调这个方法，所以也要在这个方法上做拦截；
     * */
    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        if(mBackspaceListener != null){
            if(mBackspaceListener.onBackspace()){
                return true;
            }
        }

        return super.deleteSurroundingText(beforeLength, afterLength);
    }

    public void setBackspaceListener(BackspaceListener backspaceListener) {
        this.mBackspaceListener = backspaceListener;
    }

    /**
     * 当在软件盘上点击某些按钮（比如退格键，数字键，回车键等），该方法可能会被触发（取决于输入法的开发者），
     * 所以也可以重写该方法并拦截这些事件，这些事件就不会被分发到输入框了
     * */
    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if( event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN){
            if(mBackspaceListener != null && mBackspaceListener.onBackspace()){
                return true;
            }
        }
        return super.sendKeyEvent(event);
    }

}