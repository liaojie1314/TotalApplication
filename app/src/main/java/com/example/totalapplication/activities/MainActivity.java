package com.example.totalapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.totalapplication.R;
import com.example.totalapplication.adapters.FragmentAdapter;
import com.example.totalapplication.domain.ShopInfoBean;
import com.example.totalapplication.fragments.HomeFragment;
import com.example.totalapplication.fragments.MapFragment;
import com.example.totalapplication.fragments.MovieFragment;
import com.example.totalapplication.fragments.MusicFragment;
import com.example.totalapplication.fragments.MyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    BottomNavigationView bnView;
    ViewPager viewPager;
    Toolbar toolBar;
    DrawerLayout drawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new MusicFragment());
        fragments.add(new MovieFragment());
        fragments.add(new MapFragment());
        fragments.add(new MyFragment());

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        /*
        mApiService.uploadBody("参数")
                .subscribeOn(Schedulers.io())//切换到io线程
                .observeOn(AndroidScheduler.mainThread())//切换到主线程
                .subscribe(new Consumer<ResponseData<String>>() {
                    @Override
                    public void accept(ResponseData<String> stringResponseData) throws Exception {
                        Log.i(TAG, "stringResponseData.getData()====>" + stringResponseData.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //处理异常
                    }
                });
                */

//        mApiService.uploadBody("参数")
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

        //同步请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response<String> response = mHttpBinService.test("参数").execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        //异步请求
//        mHttpBinService.test("参数").enqueue(new Callback<String>() {
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

    private void initListener() {
        setSupportActionBar(toolBar);
        //全屏横竖切换
//        ViewGroup.LayoutParams mLayoutParams = navigationView.getLayoutParams();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        mLayoutParams.width = width;
//        navigationView.setLayoutParams(mLayoutParams);

        // 设置阴影部分颜色为透明
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        //BottomNavigationView 点击事件监听
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
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
        // ViewPager 滑动事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
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

        //Home 点击事件与 DrawerLayout 关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                R.string.app_name, R.string.app_name);

        /*
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = drawerLayout.getChildAt(0); //获得主界面View
                if ("left".equals(drawerView.getTag())) {  //判断是否是左菜单
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
    }
    /*
     onCreateOptionsMenu(Menu menu)
    每次Activity一创建就会执行，一般只执行一次，创建并保留Menu的实例；
    onPrepareOptionsMenu(Menu menu)
    每次menu被打开时，该方法就会执行一次，可用于对传入的旧Menu对象进行修改操作；
    onOptionsItemSelected(MenuItem item)
    每次menu菜单项被点击时，该方法就会执行一次；
    invalidateOptionsMenu()
    刷新menu里的选项里内容，它会调用onCreateOptionsMenu(Menu menu)方法
    onCreateContextMenu()
    创建控件绑定的上下文菜单menu，根据方法里的View参数识别是哪个控件绑定
    onContextItemSelected(MenuItem item)
    点击控件绑定的上下菜单menu的内容项
    invalidateOptionsMenu()
    通知系统刷新Menu，之后，onPrepareOptionsMenu会被调用
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
     * 菜单打开后发生的动作。
     * 让 options menu 显示icon
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

        /*//动态获取权限
        AndPermission.with(AnimalDetailActivity.this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        // 申请的权限全部允许
                        //调用相册
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
    /*上传文件
    public void uploadFile(String path){
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("xxx.png"),file);//生成requestBody
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

    //将json格式的字符串{}转换为Java对象
    private void jsonToJavaObjectByNative() {

        //获取或创建json数据
        String json = "{\n" +
                "\t\"id\":2,\"name\":\"大虾\",\n" +
                "\t\"price\":12.3,\n" +
                "\t\"imagePath\":\"http://xxxxx\"\n" +
                "}\n";
        //解析json
        try {
            JSONObject jsonObject = new JSONObject(json);//将json字符串解析为json对象
            int id = jsonObject.getInt("id");//可能会报空指针异常
            int id1 = jsonObject.optInt("id");
            String name = jsonObject.optString("name");
            double price = jsonObject.optDouble("price");
            String imagePath = jsonObject.optString("imagePath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        //通过创建bean类显示数据
    }

    //将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaListByNative() {

        //获取或创建json数据
        String json = "[\n" +
                "   {\n" +
                "       \t\"id\":2,\"name\":\"大虾\",\n" +
                "       \t\"price\":12.3,\n" +
                "       \t\"imagePath\":\"http://xxxxx\"\n" +
                "   },\n" +
                "   {\n" +
                "       \t\"id\":2,\"name\":\"大虾\",\n" +
                "       \t\"price\":12.3,\n" +
                "       \t\"imagePath\":\"http://xxxxx\"\n" +
                "   }\n" +
                "]";
        //解析json
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    int id = jsonObject.optInt("id");
                    String name = jsonObject.optString("name");
                    double price = jsonObject.optDouble("price");
                    String imagePath = jsonObject.optString("imagePath");
                    //封装java对象 list集合存储
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        //通过创建bean类显示数据
    }

    //复杂json数据解析
    private void jsonToJavaOfComplex() {
        //获取或创建json数据
        String json = "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"成功!\",\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1OBC8LO0514BCL4.html\",\n" +
                "            \"image\": \"http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"被指偷拿半卷卫生纸 63岁女洗碗工服药自杀 酒店回应\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1O1Q9Q2053469M5.html\",\n" +
                "            \"image\": \"http://cms-bucket.ws.126.net/2021/0201/9860dbd3p00qntxlo00iqc000s600e3c.png?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"警方通报＂19岁女大学生学车后失联＂:已遇害 全力侦办\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //解析json
        try {
            JSONObject jsonObject = new JSONObject(json);//将json字符串解析为json对象
            //第一层解析
            int code = jsonObject.optInt("code");
            String message = jsonObject.optString("message");
            JSONArray result = jsonObject.optJSONArray("result");
            //第二层解析
            for (int i = 0; i < result.length(); i++) {
                //若数组中每一个json对象存在键值 "0" "1"
                /*
                String json = "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"成功!\",\n" +
                "    \"result\": [\n" +
                "        "0":{\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1OBC8LO0514BCL4.html\",\n" +
                "            \"image\": \"http://dingyue.ws.126.net/2021/0201/b63f2e50j00qntwfh0020c000hs00npg.jpg?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"被指偷拿半卷卫生纸 63岁女洗碗工服药自杀 酒店回应\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        },\n" +
                "        "1":{\n" +
                "            \"path\": \"https://www.163.com/dy/article/G1O1Q9Q2053469M5.html\",\n" +
                "            \"image\": \"http://cms-bucket.ws.126.net/2021/0201/9860dbd3p00qntxlo00iqc000s600e3c.png?imageView&thumbnail=140y88&quality=85\",\n" +
                "            \"title\": \"警方通报＂19岁女大学生学车后失联＂:已遇害 全力侦办\",\n" +
                "            \"passtime\": \"2021-02-02 10:00:51\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

                此时:JSONObject jsonObject1 = result.optJSONObject(i+"");//转换为string类型
                 */
                JSONObject jsonObject1 = result.optJSONObject(i);
                if (jsonObject1 != null) {
                    String path = jsonObject1.optString("path");
                    String image = jsonObject1.optString("image");
                    String title = jsonObject1.optString("title");
                    String passtime = jsonObject1.optString("passtime");
                }
            }
            //一层解析对应一层封装
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示json数据
        //通过创建bean类显示数据
    }

    //使用Gson
    private void jsonJavaByGson() {
        //json to java
        //获取或创建json数据
        String json = "";
        //解析json
        Gson gson = new Gson();
        //将json格式的字符串{}转换为Java对象
        ShopInfoBean shopInfoBean = gson.fromJson(json, ShopInfoBean.class);
        //将json格式的字符串[]转换为Java对象的List
        List<ShopInfoBean> shops = gson.fromJson(json, new TypeToken<List<ShopInfoBean>>() {
        }.getType());
        //显示json数据
        //通过创建bean类显示数据

        //java to json
        //获取或创建Java对象
        ShopInfoBean xxx = new ShopInfoBean(1, "xxx", 250.0, "http://xxx");
        //生成json数据
        Gson gson1 = new Gson();
        gson1.toJson(xxx);
        //展示数据

        //获取或创建Java对象
        List<ShopInfoBean> shopInfoBeans = new ArrayList<>();
        ShopInfoBean x = new ShopInfoBean(1, "x", 250.0, "http://x");
        ShopInfoBean xx = new ShopInfoBean(2, "xx", 250.0, "http://xx");
        shopInfoBeans.add(x);
        shopInfoBeans.add(xx);
        //生成json数据
        Gson gson2 = new Gson();
        gson2.toJson(shopInfoBeans);
        //展示数据
    }

    //使用FastJson
    private void jsonJavaByFastJson() {
        //json to java
        //获取或创建json数据
        String json = "";
        //解析json
        //将json格式的字符串{}转换为Java对象
        ShopInfoBean shopInfoBean = JSON.parseObject(json, ShopInfoBean.class);
        //将json格式的字符串[]转换为Java对象的List
        List<ShopInfoBean> shopInfoBeans = JSON.parseArray(json, ShopInfoBean.class);
        //显示json数据
        //通过创建bean类显示数据

        //java to json
        //获取或创建Java对象
        ShopInfoBean xxx = new ShopInfoBean(1, "xxx", 250.0, "http://xxx");
        //生成json数据
        String s = JSON.toJSONString(xxx);
        //展示数据

        //将java对象的List转换为json字符串[]
        //获取或创建Java对象
        List<ShopInfoBean> shops = new ArrayList<>();
        //生成json数据
        ShopInfoBean x = new ShopInfoBean(1, "x", 250.0, "http://x");
        ShopInfoBean xx = new ShopInfoBean(2, "xx", 250.0, "http://xx");
        shops.add(x);
        shops.add(xx);
        //展示数据
    }
}