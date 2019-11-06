package com.lanren.easydialog;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @ClassName EasyDialog
 * @Description TODO 自定义弹框，能完成几乎所有显示效果
 * @Author chongheng.wang
 * @Date 2019/8/9 14:23
 * @Version 1.0
 */
public abstract class EasyDialog {
    private OutsideClickDialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dilaogVh;
    private View mRootView;

    private boolean cancelable = false;
    private boolean cancelableOnTouchOutside = false;

    private boolean isCustomAnima = false;//判断是否是自定义动画

    private int mInAnimaType;
    private int mOutAnimaType;

    public EasyDialog(final Context context, int layoutId) {
        dilaogVh = DialogViewHolder.get(context, layoutId);
        mRootView = dilaogVh.getConvertView();
        mDialog = new OutsideClickDialog(context, R.style.dialog) {
            @Override
            protected void onTouchOutside() {
//                LogUtils.d("EasyDialog onTouchOutside " + isCustomAnima);
                startOutAinma(cancelableOnTouchOutside);
            }
        };

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
//                LogUtils.d("EasyDialog onShow " + isCustomAnima);
                if (isCustomAnima) {
                    AnimatorHelper.getAnimator(mRootView, mInAnimaType).start();
                }
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                LogUtils.d("EasyDialog onKey " + isCustomAnima + " " + keyCode + " " + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    startOutAinma(cancelable);
                    return true;
                }
                return false;
            }
        });

        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        onBindViewHolder(dilaogVh);
    }


    /**
     * 把弹出框view holder传出去
     */
    public abstract void onBindViewHolder(DialogViewHolder holder);

    /**
     * 显示dialog
     */
    public EasyDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        return this;
    }

    /**
     * @param style 显示一个Dialog自定义一个弹出方式  具体怎么写 可以模仿上面的
     * @return
     */
    public EasyDialog showDialog(@StyleRes int style) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialogWindow.setWindowAnimations(style);
            mDialog.show();
        }
        return this;
    }

    /**
     * @param isAnimation 如果为true 就显示默认的一个缩放动画
     * @return
     */
    public EasyDialog showDialog(boolean isAnimation) {
        if (mDialog != null && !mDialog.isShowing()) {
            if (isAnimation) {
                mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
            }
            mDialog.show();
        }
        return this;
    }

    /**
     * @param light 弹出时背景亮度 值为0.0~1.0    1.0表示全黑  0.0表示全白
     * @return
     */
    public EasyDialog backgroundLight(double light) {
        if (mDialogWindow != null) {
            if (light < 0.0 || light > 1.0) {
                return this;
            }
            WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
            lp.dimAmount = (float) light;
            mDialogWindow.setAttributes(lp);
        }
        return this;
    }

    /**
     * 上方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] + (view.getWidth() / 2) - (dilaogVh.getWidth() / 2);
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight();
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 下方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] + (view.getWidth() / 2) - (dilaogVh.getWidth() / 2);
            params.y = location[1] + 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 左方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewLeft(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] - dilaogVh.getWidth();
            params.y = location[1] - (dilaogVh.getHeight() / 2) - (view.getHeight() / 2);
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 右方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewRigh(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - dilaogVh.getWidth() - view.getWidth();
            params.y = location[1] - (dilaogVh.getHeight() / 2) - (view.getHeight() / 2);
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 左下方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewLeftBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0];
            params.y = location[1] + 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 右下方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewRighBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - view.getWidth();
            params.y = location[1] + 5;
//            LogUtils.d("setViewRighBottom =" + params.x + " " + dilaogVh.getScreenWidth() + " " + location[0] + " " + view.getWidth());
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 左上方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewLeftTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0];
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight() - 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * 右上方
     *
     * @param view 在哪个控件位置显示
     * @return
     */
    public EasyDialog setViewRighTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - view.getWidth();
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight() - 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }


    /**
     * 自定义设置动画
     */
    public EasyDialog setCustomAnimations(final int inAnimType, int outAnimType) {
        isCustomAnima = true;
        this.mInAnimaType = inAnimType;
        this.mOutAnimaType = outAnimType;
        return this;
    }


    /**
     * 从底部一直弹到中间
     */
    @SuppressLint("NewApi")
    public EasyDialog fromBottomToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
        }
        return this;
    }

    /**
     * 从底部弹出
     */
    public EasyDialog fromBottom() {
        if (mDialogWindow != null) {
            fromBottomToMiddle();
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        }
        return this;
    }

    /**
     * 从左边一直弹到中间退出也是到左边
     */
    public EasyDialog fromLeftToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_left_in_left_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
        }
        return this;
    }

    /**
     * 从右边一直弹到中间退出也是到右边
     *
     * @return
     */
    public EasyDialog fromRightToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_right_in_right_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialogWindow.setGravity(Gravity.RIGHT);
        }
        return this;
    }

    /**
     * 从顶部弹出 从顶部弹出  保持在顶部
     *
     * @return
     */
    public EasyDialog fromTop() {
        if (mDialogWindow != null) {
            fromTopToMiddle();
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        }
        return this;
    }

    /**
     * 从顶部谈到中间  从顶部弹出  保持在中间
     *
     * @return
     */
    public EasyDialog fromTopToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return this;
    }


    /**
     * 全屏显示
     */
    public EasyDialog fullScreen() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }


    public EasyDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        if (mDialogWindow != null) {
            mDialog.setOnKeyListener(onKeyListener);
        }
        return this;
    }

    /**
     * 全屏宽度
     */
    public EasyDialog fullWidth() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * 全屏高度
     */
    public EasyDialog fullHeight() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * @param width  自定义的宽度
     * @param height 自定义的高度
     * @return
     */
    public EasyDialog setWidthAndHeight(int width, int height) {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.width = width;
            wl.height = height;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * cancel dialog
     */
    public void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            startOutAinma(true);
        }
    }

    /**
     * cancel dialog
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            startOutAinma(true);
        }
    }

    /**
     * 设置监听
     */
    public EasyDialog setDialogDismissListener(OnDismissListener listener) {
        if (mDialog != null) {
            mDialog.setOnDismissListener(listener);
        }
        return this;
    }

    /**
     * 设置监听
     */
    public EasyDialog setOnCancelListener(OnCancelListener listener) {
        if (mDialog != null) {
            mDialog.setOnCancelListener(listener);
        }
        return this;
    }

    /**
     * 设置是否能取消
     */
    public EasyDialog setCancelAble(boolean cancel) {
        if (mDialog != null) {
            cancelable = cancel;
//            mDialog.setCancelable(cancel);
        }
        return this;
    }


    /**
     * 设置触摸其他地方是否能取消
     */
    public EasyDialog setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null) {
            cancelableOnTouchOutside = cancel;
            mDialog.setCanceledOnTouchOutside(cancel);
        }
        return this;
    }

    private void startOutAinma(final boolean isCancelable) {
        if (isCustomAnima) {// 判断是否设置自定义动画，true 执行自定义动画
            if (cancelable || cancelableOnTouchOutside) {
                Animator animator = AnimatorHelper.getAnimator(mRootView, mOutAnimaType);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
//                        LogUtils.d("EasyDialog onAnimationStart ");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        LogUtils.d("EasyDialog onAnimationEnd ");
                        mDialog.dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
//                        LogUtils.d("EasyDialog onAnimationCancel ");

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
//                        LogUtils.d("EasyDialog onAnimationRepeat ");

                    }
                });
                if (isCancelable) {//从返回键或弹框外点击 设置true时执行 否则不执行
                    animator.start();
                }
            }
        } else {
            if (isCancelable) {//从返回键或弹框外点击 设置true时执行 否则不执行
                mDialog.dismiss();
            }
        }
    }
}
