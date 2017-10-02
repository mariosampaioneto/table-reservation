package com.mfactory.tablereservation.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mfactory.tablereservation.MainApplication;
import com.mfactory.tablereservation.MainComponent;
import com.mfactory.tablereservation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @BindView(R.id.toolbar_include)
    @Nullable
    View mViewToolbarInclude;

    @BindView(R.id.include_loading_view)
    @Nullable
    public ConstraintLayout mLoadingView;

    @BindView(R.id.include_error_view)
    @Nullable
    public ConstraintLayout mErrorViewConstraintLayout;

    @BindView(R.id.view_flipper)
    @Nullable
    public ViewFlipper mViewFlipper;

    public ToolbarContainer mToolbarContainer;
    public ErrorView mErrorView;

    public MaterialDialog mLoadingDialog;

    protected MainComponent getMainComponent() {
        return getMainApplication().getComponent();
    }

    protected MainApplication getMainApplication() {
        return (MainApplication) getApplication();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void handleButterknife() {
        ButterKnife.bind(this);
        if (mViewToolbarInclude != null) {
            mToolbarContainer = new ToolbarContainer();
            ButterKnife.bind(mToolbarContainer, mViewToolbarInclude);
        }

        if (mErrorViewConstraintLayout != null) {
            mErrorView = new ErrorView();
            ButterKnife.bind(mErrorView, mErrorViewConstraintLayout);
        }
    }

    public void handleButterknife(View view) {
        ButterKnife.bind(this, view);
        if (mViewToolbarInclude != null) {
            mToolbarContainer = new ToolbarContainer();
            ButterKnife.bind(mToolbarContainer, mViewToolbarInclude);
        }

        if (mErrorViewConstraintLayout != null) {
            mErrorView = new ErrorView();
            ButterKnife.bind(mErrorView, mErrorViewConstraintLayout);
        }
    }

    public void setupToolbar() {
        if (mToolbarContainer.mToolbar != null) {
            setSupportActionBar(mToolbarContainer.mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public void setupToolbar(String title) {
        setupToolbar();
        setTitle(title);
    }

    public void setTitle(String title) {
        if (mToolbarContainer.mToolbarTitleTextView != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("");
            }

            mToolbarContainer.mToolbarTitleTextView.setText(title);
        } else {
            if (mToolbarContainer.mToolbar != null) {
                mToolbarContainer.mToolbar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Base loading/error/toolbar methods
    @OnClick(R.id.error_btn)
    @Optional
    public void errorRetryBtnPressed() {

    }

    public static class ToolbarContainer {
        @BindView(R.id.toolbar)
        @Nullable
        public Toolbar mToolbar;

        @BindView(R.id.toolbar_title)
        @Nullable
        public TextView mToolbarTitleTextView;
    }

    public static class ErrorView {
        @BindView(R.id.error_message)
        @Nullable
        public TextView mErrorTextView;

        @BindView(R.id.error_btn)
        @Nullable
        public Button mErrorBtn;
    }

    public void setDisplayedChild(ViewFlipper flipper, View child) {
        if (flipper != null && child != null) {
            int index = flipper.indexOfChild(child);
            if (index < flipper.getChildCount() && index >= 0) {
                flipper.setDisplayedChild(index);
            }
        }
    }
}
