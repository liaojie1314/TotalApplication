package com.example.totalapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.totalapplication.R;
import com.example.totalapplication.adapters.FragmentAdapter;
import com.example.totalapplication.api.AndroidScheduler;
import com.example.totalapplication.api.Api;
import com.example.totalapplication.api.ApiService;
import com.example.totalapplication.api.NetWorkModule;
import com.example.totalapplication.api.exception.ApiException;
import com.example.totalapplication.api.exception.ErrorConsumer;
import com.example.totalapplication.domain.ShopInfoBean;
import com.example.totalapplication.domain.UserDetail;
import com.example.totalapplication.fragments.HomeFragment;
import com.example.totalapplication.fragments.MapFragment;
import com.example.totalapplication.fragments.MovieFragment;
import com.example.totalapplication.fragments.MusicFragment;
import com.example.totalapplication.fragments.MyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    ApiService mApiService;

    BottomNavigationView bnView;
    ViewPager viewPager;
    Toolbar toolBar;
    DrawerLayout drawerLayout;
    private NavigationView mNavigationView;
    private ImageView mImagePhoto;
    private TextView mNickNameTv;

    private long mId;
    private String mNickname;
    private String mAvatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        mId = intent.getLongExtra("id", 0);
        initView();
        initListener();

        //??????????????????????????????
        checkNotificationPermission();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new MusicFragment());
        fragments.add(new MovieFragment());
        fragments.add(new MapFragment());
        fragments.add(new MyFragment());

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        /*
        mApiService.uploadBody("??????")
                .subscribeOn(Schedulers.io())//?????????io??????
                .observeOn(AndroidScheduler.mainThread())//??????????????????
                .subscribe(new Consumer<ResponseData<String>>() {
                    @Override
                    public void accept(ResponseData<String> stringResponseData) throws Exception {
                        Log.i(TAG, "stringResponseData.getData()====>" + stringResponseData.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //????????????
                    }
                });
                */

//        mApiService.uploadBody("??????")
//                .compose(ResponseTransformer.obtain())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i(TAG, "data =====>" + s);
//                    }
//                }, new ErrorConsumer() {
//                    @Override
//                    protected void error(ApiException e) {
//
//                    }
//                });

        //????????????
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response<String> response = mHttpBinService.test("??????").execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        //????????????
//        mHttpBinService.test("??????").enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i(TAG, "response" + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
    }

    private void checkNotificationPermission() {
        boolean areNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled();
        if (!areNotificationsEnabled) {
            gotoNotificationSetting();
        }
    }

    /**
     * ??????????????????
     */

    private void getUserDetail() {
        mApiService.userDetail(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<UserDetail>() {
                    @Override
                    public void accept(UserDetail userDetail) throws Exception {
                        mNickname = userDetail.getProfile().getNickname();
                        mAvatarUrl = userDetail.getProfile().getAvatarUrl();
                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {

                    }
                });
    }

    /**
     * ??????????????????
     */
    private void getUserAccount() {
        mApiService.userAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {

                    }
                });
    }

    /**
     * ?????????????????? , ??????????????????mv, dj ??????
     */
    private void getUserSubCount() {
        mApiService.userSubCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }, new ErrorConsumer() {
                    @Override
                    protected void error(ApiException e) {

                    }
                });
    }

    private void gotoNotificationSetting() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0??????
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
        } else {
            // ??????
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initListener() {
        setSupportActionBar(toolBar);
        //??????????????????
//        ViewGroup.LayoutParams mLayoutParams = navigationView.getLayoutParams();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        mLayoutParams.width = width;
//        navigationView.setLayoutParams(mLayoutParams);

        // ?????????????????????????????????
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        //BottomNavigationView ??????????????????
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // ?????????????????????Fragment
                switch (menuId) {
                    case R.id.tab_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_music:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_movie:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_map:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.tab_my:
                        viewPager.setCurrentItem(4);
                }
                return false;
            }
        });
        // ViewPager ??????????????????
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //?????????????????????????????? menu ?????????????????????
                bnView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_favor:
                        Toast.makeText(MainActivity.this, "favor", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_balance:
                        break;
                    case R.id.nav_contract:
                        break;
                    case R.id.nav_message:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        //Home ??????????????? DrawerLayout ??????
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                R.string.app_name, R.string.app_name);

        /*
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = drawerLayout.getChildAt(0); //???????????????View
                if ("left".equals(drawerView.getTag())) {  //????????????????????????
                    int offset = (int) (drawerView.getWidth() * (slideOffset - 1));
                    content.setTranslationX(offset);
                }
            }
        }
         */
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initView() {
        bnView = findViewById(R.id.bottom_nav_view);
        viewPager = findViewById(R.id.view_pager);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolBar = findViewById(R.id.tool_bar);
        mNavigationView = findViewById(R.id.navigation_view);
        View headerView = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mImagePhoto = headerView.findViewById(R.id.img_photo);
        mNickNameTv = headerView.findViewById(R.id.nickNameTv);
        if (!TextUtils.isEmpty(mNickname)) {
            mNickNameTv.setText(mNickname);
        }
        if (!TextUtils.isEmpty(mAvatarUrl)) {
            Picasso.with(this).load(mAvatarUrl).into(mImagePhoto);
        }
    }
    /*
     onCreateOptionsMenu(Menu menu)
    ??????Activity???????????????????????????????????????????????????????????????Menu????????????
    onPrepareOptionsMenu(Menu menu)
    ??????menu?????????????????????????????????????????????????????????????????????Menu???????????????????????????
    onOptionsItemSelected(MenuItem item)
    ??????menu??????????????????????????????????????????????????????
    invalidateOptionsMenu()
    ??????menu????????????????????????????????????onCreateOptionsMenu(Menu menu)??????
    onCreateContextMenu()
    ????????????????????????????????????menu?????????????????????View?????????????????????????????????
    onContextItemSelected(MenuItem item)
    ?????????????????????????????????menu????????????
    invalidateOptionsMenu()
    ??????????????????Menu????????????onPrepareOptionsMenu????????????
         */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:
                Toast.makeText(this, "option 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_2:
                Toast.makeText(this, "option 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_3:
                Toast.makeText(this, "option 3", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ?????????????????????????????????
     * ??? options menu ??????icon
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    private void postAsync(View view) {

        /*//??????????????????
        AndPermission.with(AnimalDetailActivity.this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        // ???????????????????????????
                        //????????????
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                    }
                })
                .start();
        */
        /*
        Call<ResponseBody> call = mApiService.post("bhcb", "1243");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i(TAG, "postAsync:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
         */
    }
    /*????????????
    public void uploadFile(String path){
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("xxx.png"),file);//??????requestBody
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        mApiService.uploadFile(part).enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                Log.i(TAG, "response.body().getData():" + response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {

            }
        });
    }*/

    //???json??????????????????{}?????????Java??????
    private void jsonToJavaObjectByNative() {

        //???????????????json??????
        String json = "{\n" +
                "\t\"id\":2,\"name\":\"??????\",\n" +
                "\t\"price\":12.3,\n" +
                "\t\"imagePath\":\"http://xxxxx\"\n" +
                "}\n";
        //??????json
        try {
            JSONObject jsonObject = new JSONObject(json);//???json??????????????????json??????
            int id = jsonObject.getInt("id");//???????????????????????????
            int id1 = jsonObject.optInt("id");
            String name = jsonObject.optString("name");
            double price = jsonObject.optDouble("price");
            String imagePath = jsonObject.optString("imagePath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //??????json??????
        //????????????bean???????????????
    }

    //???json??????????????????[]?????????Java?????????List
    private void jsonToJavaListByNative() {

        //???????????????json??????
        String json = "[\n" +
                "   {\n" +
                "       \t\"id\":2,\"name\":\"??????\",\n" +
                "       \t\"price\":12.3,\n" +
                "       \t\"imagePath\":\"http://xxxxx\"\n" +
                "   },\n" +
                "   {\n" +
                "       \t\"id\":2,\"name\":\"??????\",\n" +
                "       \t\"price\":12.3,\n" +
                "       \t\"imagePath\":\"http://xxxxx\"\n" +
                "   }\n" +
                "]";
        //??????json
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    int id = jsonObject.optInt("id");
                    String name = jsonObject.optString("name");
                    double price = jsonObject.optDouble("price");
                    String imagePath = jsonObject.optString("imagePath");
                    //??????java?????? list????????????
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //??????json??????
        //????????????bean???????????????
    }

    //??????json????????????
    private void jsonToJavaOfComplex() {
        //???????????????json??????
        String json = "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"??????!\",\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1OBC8LO0514BCL4.html\",\n" +
                "            \"image\": \"http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"??????????????????????????? 63??????????????????????????? ????????????\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1O1Q9Q2053469M5.html\",\n" +
                "            \"image\": \"http://cms-bucket.ws.126.net/2021/0201/9860dbd3p00qntxlo00iqc000s600e3c.png?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"???????????????19?????????????????????????????????:????????? ????????????\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //??????json
        try {
            JSONObject jsonObject = new JSONObject(json);//???json??????????????????json??????
            //???????????????
            int code = jsonObject.optInt("code");
            String message = jsonObject.optString("message");
            JSONArray result = jsonObject.optJSONArray("result");
            //???????????????
            for (int i = 0; i < result.length(); i++) {
                //?????????????????????json?????????????????? "0" "1"
                /*
                String json = "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"??????!\",\n" +
                "    \"result\": [\n" +
                "        "0":{\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1OBC8LO0514BCL4.html\",\n" +
                "            \"image\": \"http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"??????????????????????????? 63??????????????????????????? ????????????\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        },\n" +
                "        "1":{\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1O1Q9Q2053469M5.html\",\n" +
                "            \"image\": \"http://cms-bucket.ws.126.net/2021/0201/9860dbd3p00qntxlo00iqc000s600e3c.png?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"???????????????19?????????????????????????????????:????????? ????????????\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

                ??????:JSONObject jsonObject1 = result.optJSONObject(i+"");//?????????string??????
                 */
                JSONObject jsonObject1 = result.optJSONObject(i);
                if (jsonObject1 != null) {
                    String path = jsonObject1.optString("path");
                    String image = jsonObject1.optString("image");
                    String title = jsonObject1.optString("title");
                    String passtime = jsonObject1.optString("passtime");
                }
            }
            //??????????????????????????????
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //??????json??????
        //????????????bean???????????????
    }

    //??????Gson
    private void jsonJavaByGson() {
        //json to java
        //???????????????json??????
        String json = "";
        //??????json
        Gson gson = new Gson();
        //???json??????????????????{}?????????Java??????
        ShopInfoBean shopInfoBean = gson.fromJson(json, ShopInfoBean.class);
        //???json??????????????????[]?????????Java?????????List
        List<ShopInfoBean> shops = gson.fromJson(json, new TypeToken<List<ShopInfoBean>>() {
        }.getType());
        //??????json??????
        //????????????bean???????????????

        //java to json
        //???????????????Java??????
        ShopInfoBean xxx = new ShopInfoBean(1, "xxx", 250.0, "http://xxx");
        //??????json??????
        Gson gson1 = new Gson();
        gson1.toJson(xxx);
        //????????????

        //???????????????Java??????
        List<ShopInfoBean> shopInfoBeans = new ArrayList<>();
        ShopInfoBean x = new ShopInfoBean(1, "x", 250.0, "http://x");
        ShopInfoBean xx = new ShopInfoBean(2, "xx", 250.0, "http://xx");
        shopInfoBeans.add(x);
        shopInfoBeans.add(xx);
        //??????json??????
        Gson gson2 = new Gson();
        gson2.toJson(shopInfoBeans);
        //????????????
    }

    //??????FastJson
    private void jsonJavaByFastJson() {
        //json to java
        //???????????????json??????
        String json = "";
        //??????json
        //???json??????????????????{}?????????Java??????
        ShopInfoBean shopInfoBean = JSON.parseObject(json, ShopInfoBean.class);
        //???json??????????????????[]?????????Java?????????List
        List<ShopInfoBean> shopInfoBeans = JSON.parseArray(json, ShopInfoBean.class);
        //??????json??????
        //????????????bean???????????????

        //java to json
        //???????????????Java??????
        ShopInfoBean xxx = new ShopInfoBean(1, "xxx", 250.0, "http://xxx");
        //??????json??????
        String s = JSON.toJSONString(xxx);
        //????????????

        //???java?????????List?????????json?????????[]
        //???????????????Java??????
        List<ShopInfoBean> shops = new ArrayList<>();
        //??????json??????
        ShopInfoBean x = new ShopInfoBean(1, "x", 250.0, "http://x");
        ShopInfoBean xx = new ShopInfoBean(2, "xx", 250.0, "http://xx");
        shops.add(x);
        shops.add(xx);
        //????????????
    }
}