package com.errsecers.universaldialoglib;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Ivan Danilov on 07.08.2015.
 */
public abstract class AbstractUniversalDialog extends Dialog {
    private static AbstractUniversalDialog prevDialog;
    private Activity activity;

    public AbstractUniversalDialog(Activity context) {
        super(context);
        this.activity = context;
    }

    @Override
    public void show() {
        if (prevDialog != null && prevDialog.isShowing()) {
            prevDialog.dismiss();
        }
        super.show();
        getWindow().setLayout(windowWidth()
                , windowHeight());
        prevDialog = this;
    }

    /**
     * if you do not override default value is 600dp
     *
     * @return dialog width
     */
    protected int windowWidth() {
        //default value, can override
        return (int) getContext().getResources().getDimension(R.dimen.dialog_width);
    }

    /**
     * if you do not override default value is 400dp
     *
     * @return dialog height
     */
    protected int windowHeight() {
        //default value, can override
        return (int) getContext().getResources().getDimension(R.dimen.dialog_height);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(true);
        setContentView(getContentView());
        initButterKnife();
        initView();
    }

    /**
     * This method performs data manipulations
     */
    protected abstract void initView();

    /**
     * If you use ButterKnife override this method.
     * <br>
     * Example:
     * <br>
     * <code>
     * protected void initButterKnife() {
     * ButterKnife.inject(this);
     * }
     * </code>
     */
    protected void initButterKnife() {
    }

    /**
     * This method defines root dialog view
     * <br>
     * Example:
     * <br>
     * <code>
     * protected View getContentView() {
     * return getLayoutInflater().inflate(R.layout.dialog_no_connect_error, null);
     * }
     * </code>
     */
    protected abstract View getContentView();

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().getDecorView().setSystemUiVisibility(activity.getWindow().getDecorView().getSystemUiVisibility());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
