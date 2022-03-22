package com.example.totalapplication.activities.audio;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.totalapplication.R;
import com.example.totalapplication.Utils.AudioInfoUtils;
import com.example.totalapplication.Utils.PermissionDialogUtils;
import com.example.totalapplication.Utils.StartSystemPageUtils;
import com.example.totalapplication.Utils.dialogs.audio.AudioInfoDialog;
import com.example.totalapplication.Utils.dialogs.audio.RenameDialog;
import com.example.totalapplication.activities.recoder.RecorderActivity;
import com.example.totalapplication.adapters.audio.AudioListAdapter;
import com.example.totalapplication.base.Contacts;
import com.example.totalapplication.databinding.ActivityAudioListBinding;
import com.example.totalapplication.domain.AudioBean;
import com.example.totalapplication.services.AudioService;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AudioListActivity extends AppCompatActivity {

    private ActivityAudioListBinding mBinding;
    private List<AudioBean> mDatas;
    private AudioListAdapter mAdapter;
    private AudioService mAudioService;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.AudioBinder audioBinder = (AudioService.AudioBinder) service;
            mAudioService = audioBinder.getService();
            mAudioService.setOnPlayChangeListener(mPlayChangeListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    AudioService.OnPlayChangeListener mPlayChangeListener = new AudioService.OnPlayChangeListener() {
        @Override
        public void PlayChange(int changePosition) {
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAudioListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //绑定服务
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        //为ListView设置数据源和适配器
        mDatas = new ArrayList<>();
        mAdapter = new AudioListAdapter(this, mDatas);
        mBinding.audioLV.setAdapter(mAdapter);
        //将音频对象集合保存到全局变量当中
        Contacts.setAudioBeanList(mDatas);
        //加载数据
        loadDatas();
        //设置监听事件
        setEvents();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断点击了返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            StartSystemPageUtils.goToHomePage(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置监听
     */
    private void setEvents() {
        mAdapter.setOnItemPlayClickListener(mPlayClickListener);
        mBinding.audioLV.setOnItemLongClickListener(mLongClickListener);
        mBinding.audioIB.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.关闭音乐
            mAudioService.closeMusic();
            //2.同意权限跳转到录音界面
            startActivity(new Intent(AudioListActivity.this, RecorderActivity.class));
            //3.注销当前activity
            finish();
        }
    };

    //点击每一个播放按钮会回调的方法
    AudioListAdapter.OnItemPlayClickListener mPlayClickListener = new AudioListAdapter.OnItemPlayClickListener() {
        @Override
        public void OnItemPlayClick(AudioListAdapter adapter, View convertView, View playView, int position) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (i == position) {
                    continue;
                }
                AudioBean bean = mDatas.get(i);
                bean.setPlaying(false);
            }
            //获取当前条目播放状态
            boolean playing = mDatas.get(position).isPlaying();
            mDatas.get(position).setPlaying(!playing);
            mAdapter.notifyDataSetChanged();
            mAudioService.CutMusicOrPause(position);
        }
    };

    AdapterView.OnItemLongClickListener mLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            showPopMenu(view, position);
            mAudioService.closeMusic();
            return false;
        }
    };

    private void showPopMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.RIGHT);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.audio_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_info:
                        showFileInfoDialog(position);
                        break;
                    case R.id.menu_del:
                        deleteFileByPos(position);
                        break;
                    case R.id.menu_rename:
                        showRenameDialog(position);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * 显示文件详情对话框
     *
     * @param position 对应item位置
     */
    private void showFileInfoDialog(int position) {
        AudioBean bean = mDatas.get(position);
        AudioInfoDialog dialog = new AudioInfoDialog(this);
        dialog.show();
        dialog.setDialogWidth();
        dialog.setFileInfo(bean);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示重命名对话框
     *
     * @param position 选中的item
     */
    private void showRenameDialog(int position) {
        AudioBean bean = mDatas.get(position);
        String title = bean.getTitle();
        RenameDialog dialog = new RenameDialog(this);
        dialog.show();
        dialog.setDialogWidth();
        dialog.setTipText(title);
        dialog.setOnEnsureListener(new RenameDialog.OnEnsureListener() {
            @Override
            public void OnEnSure(String msg) {
                renameByPosition(msg, position);
            }
        });
    }

    /**
     * 对于指定位置的文件重命名
     */
    private void renameByPosition(String msg, int position) {
        AudioBean audioBean = mDatas.get(position);
        if (audioBean.getTitle().equals(msg)) {
            return;
        }
        String path = audioBean.getPath();
        String fileSuffix = audioBean.getFileSuffix();
        File srcFile = new File(path);//原来的文件
        //获取修改路径
        String destPath = srcFile.getParent() + File.separator + msg + fileSuffix;
        File destFile = new File(destPath);
        //进行重命名物理操作
        srcFile.renameTo(destFile);
        //从内存当中进行修改
        audioBean.setTitle(msg);
        audioBean.setPath(destPath);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 删除指定位置文件
     *
     * @param position 要删除文件位置
     */
    private void deleteFileByPos(int position) {
        AudioBean bean = mDatas.get(position);
        String title = bean.getTitle();
        String path = bean.getPath();
        PermissionDialogUtils.showNormalDialog(this, "提示信息", "删除文件后将无法恢复,是否确定删除",
                "确定", new PermissionDialogUtils.onLeftClickListener() {
                    @Override
                    public void OnLeftClick() {
                        File file = new File(path);
                        file.getAbsoluteFile().delete();//物理删除
                        mDatas.remove(bean);
                        mAdapter.notifyDataSetChanged();
                    }
                }, "取消", null);
    }


    private void loadDatas() {
        //1.获取指定路径下的音频文件
        File fetchFile = new File(Contacts.PATH_FETCH_DIR_RECORD);
        File[] listFiles = fetchFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (new File(dir, name).isDirectory()) {
                    return false;
                }
                if (name.endsWith(".mp3") || name.endsWith(".amr")) {
                    return true;
                }
                return false;
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        AudioInfoUtils audioInfoUtils = AudioInfoUtils.getInstance();
        //2.遍历数组中的文件 依次得到文件信息
        for (int i = 0; i < listFiles.length; i++) {
            File audioFile = listFiles[i];
            String fName = audioFile.getName();//文件名带后缀
            String title = fName.substring(0, fName.lastIndexOf("."));
            String suffix = fName.substring(fName.lastIndexOf("."));
            //获取文件最后修改时间
            long lastModified = audioFile.lastModified();
            String time = sdf.format(lastModified);//转换为固定格式的时间字符串
            //获取文件的字节数
            long fLength = audioFile.length();
            //获取文件路径
            String audioPath = audioFile.getAbsolutePath();
            long duration = audioInfoUtils.getAudioFileDuration(audioPath);
            String formatDuration = audioInfoUtils.getAudioFileFormatDuration(duration);
            AudioBean audioBean = new AudioBean(i + "", title, time, formatDuration, audioPath,
                    duration, lastModified, suffix, fLength);
            mDatas.add(audioBean);
        }
        audioInfoUtils.releaseRetriever();//释放多媒体资料的资源对象
        //将集合中的元素重新排序,按照时间先后顺序
        Collections.sort(mDatas, new Comparator<AudioBean>() {
            @Override
            public int compare(AudioBean o1, AudioBean o2) {
                if (o1.getLastModified() < o2.getLastModified()) {
                    return 1;
                } else if (o1.getLastModified() == o2.getLastModified()) {
                    return 0;
                }
                return -1;
            }
        });
        mAdapter.notifyDataSetChanged();
    }
}