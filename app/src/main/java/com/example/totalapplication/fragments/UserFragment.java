package com.example.totalapplication.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.totalapplication.R;
import com.example.totalapplication.Utils.ActivityUtil;
import com.example.totalapplication.Utils.GlideCacheUtil;
import com.example.totalapplication.Utils.ToastUtils;
import com.example.totalapplication.Utils.UserUtil;
import com.example.totalapplication.activities.cimoc.CimocMainActivity;
import com.example.totalapplication.activities.cimoc.VersionInfoActivity;
import com.example.totalapplication.config.BasicConfig;
import com.example.totalapplication.controllers.APKUpdateController;
import com.example.totalapplication.controllers.LoadingViewController;
import com.example.totalapplication.domain.entities.APKUpdate;
import com.example.totalapplication.domain.entities.MyUser;
import com.example.totalapplication.managers.LoadingViewManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView headingImageView;
    private TextView nicknameView;
    private TextView cacheSizeView;
    private CardView myBookshelfView;
    private ConstraintLayout clearCacheView;
    private ConstraintLayout checkUpdateView;
    private ConstraintLayout versionFeedbackView;
    private CardView logoutView;
    private BottomNavigationView navigationView;
    private boolean isLatestVersion = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BasicConfig.REQUEST_CODE_LOGIN) {
            if (resultCode == BasicConfig.RESULT_CODE_SUCCESS) {
                loadUserInfo((MyUser) data.getParcelableExtra(BasicConfig.LOGIN_INTENT_KEY));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInitialized) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    loadUserInfo(UserUtil.myUser);
                    loadCacheSize();
                }
            });
        }
    }

    /**
     * ???????????????
     */
    private void initData() {
        setDarkStatus(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                headingImageView = getActivity().findViewById(R.id.fg_user_heading);
                nicknameView = getActivity().findViewById(R.id.fg_user_nickname);
                myBookshelfView = getActivity().findViewById(R.id.fg_user_bookshelf);
                clearCacheView = getActivity().findViewById(R.id.fg_user_clear_cache);
                cacheSizeView = getActivity().findViewById(R.id.fg_user_cache_size);
                checkUpdateView = getActivity().findViewById(R.id.fg_user_check_update);
                versionFeedbackView = getActivity().findViewById(R.id.fg_user_version_info);
                logoutView = getActivity().findViewById(R.id.fg_user_logout);
                navigationView = getActivity().findViewById(R.id.fragment_bm_navigation);
                // ???????????????
                headingImageView.setOnClickListener(UserFragment.this);
                nicknameView.setOnClickListener(UserFragment.this);
                myBookshelfView.setOnClickListener(UserFragment.this);
                clearCacheView.setOnClickListener(UserFragment.this);
                checkUpdateView.setOnClickListener(UserFragment.this);
                versionFeedbackView.setOnClickListener(UserFragment.this);
                logoutView.setOnClickListener(UserFragment.this);
                loadUserInfo(UserUtil.myUser);
                loadCacheSize();
                isInitialized = true;
            }
        });
    }

    /**
     * ??????????????????
     * @param myUser
     */
    private void loadUserInfo(MyUser myUser) {
        if (myUser != null) {
            RequestOptions requestOptions = new RequestOptions().placeholder(getActivity().getResources().getDrawable(R.drawable.ic_user_heading));
            Glide.with(getActivity())
                    .load(myUser.getUserHeadingUrl())
                    .apply(requestOptions)
                    .into(headingImageView);
            nicknameView.setText(myUser.getUserNickname());
            logoutView.setEnabled(true);
        } else {
            headingImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_user_heading));
            nicknameView.setText("????????????");
            logoutView.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_user_heading:
                userClick();
                break;
            case R.id.fg_user_nickname:
                userClick();
                break;
            case R.id.fg_user_bookshelf:
                navigationView.setSelectedItemId(navigationView.getMenu().getItem(CimocMainActivity.FRAGMENT_BOOKSHELF).getItemId());
                break;
            case R.id.fg_user_clear_cache:
                clearCache();
                break;
            case R.id.fg_user_check_update:
                checkUpdate();
                break;
            case R.id.fg_user_version_info:
                getActivity().startActivity(new Intent(getContext(), VersionInfoActivity.class));
                break;
            case R.id.fg_user_logout:
                logout();
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     */
    private void userClick() {
        if (UserUtil.myUser == null) {
            ActivityUtil.launchLoginView(this, BasicConfig.REQUEST_CODE_LOGIN);
        } else {
            showToast(UserUtil.myUser.getUserNickname());
        }
    }

    /**
     * ????????????
     */
    private void clearCache() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("??????");
        dialog.setMessage("????????????????????????");
        dialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showLoadingAnimation();
                final GlideCacheUtil glideCacheUtil = GlideCacheUtil.getInstance();
                glideCacheUtil.clearImageAllCache(getContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long count = 0;
                            while (true) {
                                if (glideCacheUtil.getCacheSize(getContext()).equals("0MB") && count > 500) {
                                    mHandler.sendEmptyMessage(2);
                                    break;
                                }
                                count += 100;
                                Thread.sleep(100);
                            }
                        } catch (Exception e) {
                            Log.d(BasicConfig.TAG, "clearCache-run: " + e.toString());
                        }
                    }
                }).start();
            }
        });
        dialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    LoadingViewController.dismiss();
                    loadCacheSize();
                    LoadingViewManager.dismiss();
                    showToast("???????????????");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * ??????????????????
     */
    private void showLoadingAnimation() {
        LoadingViewManager.with(getActivity())
                .setHintText("??????????????????")
                .setAnimationStyle("PacmanIndicator")
                .setOutsideAlpha(0.6f)
                .setTouchOutsideToDismiss(true)
                .build();
    }

    /**
     * ??????????????????
     */
    private void loadCacheSize() {
        cacheSizeView.setText(GlideCacheUtil.getInstance().getCacheSize(getContext()));
    }

    /**
     * ????????????
     */
    private void logout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("??????");
        dialog.setMessage("????????????????????????");
        dialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserUtil.myUser = null;
                UserUtil.saveLoginInfo(getContext(), false);
                loadUserInfo(UserUtil.myUser);
            }
        });
        dialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * ??????APP??????
     */
    private void checkUpdate() {
        LoadingViewManager.with(this)
                .setHintText("??????????????????...")
                .setOutsideAlpha(0.2f)
                .setShowInnerRectangle(true)
                .setTouchOutsideToDismiss(false)
                .setOnAnimatingListener(new LoadingViewManager.OnAnimatingListener() {
                    @Override
                    public void onAnimating() {

                    }

                    @Override
                    public void onDismiss() {
                        if (isLatestVersion) {
                            isLatestVersion = false;
                            ToastUtils.shortToast(getContext(), "????????????????????????");
                        }
                    }
                })
                .build();
        final APKUpdateController updateController = new APKUpdateController(getActivity());
        updateController.checkNewVersion(new APKUpdateController.OnAPKUpdateListener() {
            @Override
            public void onQueryDone(APKUpdate update, boolean newVersion) {
                if (newVersion && update != null) {
                    updateController.showNewVersionDialog(update);
                } else {
                    isLatestVersion = true;
                }
                LoadingViewManager.dismiss();
            }

            @Override
            public void onQueryError(Exception e) {
                LoadingViewManager.dismiss(true);
                showToastWithLongShow("??????????????????" + e.toString());
            }
        });
    }
}