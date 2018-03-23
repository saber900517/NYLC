package com.nylc.nylc.character.supplier;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.model.UploadImage;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品
 * Created by 吴曰阳 on 2018/3/3.
 */

public class AddProductActivity extends BaseActivity implements View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {

    private ImageView iv_back;
    private ImageView iv_img;
    private EditText et_productName;//商品名称
    private EditText et_price;//单价
    private Spinner sp_type;//规格
    private EditText et_description;//介绍
    private Button bt_confirm;//确定按钮
    private ArrayList<ProductType> types;
    private Product product;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private boolean isAdd = true;//是否是新增商品，true新增，false修改
    //小麦种子
    private String wheatSeed = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520875222192&di=f80b4ed26db00bcfd957e1818fe535d4&imgtype=0&src=http%3A%2F%2Fwww.cnjidan.com%2Fupload%2Fpictures%2F2015%2F10%2F0-HMM6UY.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_img = findViewById(R.id.iv_img);
        et_productName = findViewById(R.id.et_productName);
        et_price = findViewById(R.id.et_price);
        sp_type = findViewById(R.id.sp_type);
        et_description = findViewById(R.id.et_description);
        bt_confirm = findViewById(R.id.bt_confirm);

//        registerForContextMenu(iv_img);
        iv_img.setOnCreateContextMenuListener(this);
        iv_img.setOnClickListener(this);


        iv_back.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        Intent intent = getIntent();
        types = intent.getParcelableArrayListExtra("types");
        ArrayAdapter<ProductType> adapter = new ArrayAdapter<ProductType>(this, android.R.layout.simple_list_item_1, types);
        sp_type.setAdapter(adapter);

        int typeIndex = intent.getIntExtra("typeIndex", 0);
        sp_type.setSelection(typeIndex);
        if (intent.hasExtra("product")) {
            isAdd = false;
            product = intent.getParcelableExtra("product");
            et_productName.setText(product.getGOODS_NAME());
            et_price.setText(product.getGOODS_PRICE());
            et_description.setText(product.getGOODS_DESCRIPTION());
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("拍照");
        menu.add("从相册选择");
    }

    private File file;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        CropOptions cropOptions;
        CompressConfig compressConfig;
        file = new File(Environment.getExternalStorageDirectory(), "/nylc/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        TakePhoto takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(compressConfig, true);
        switch (title) {
            case "拍照":
                takePhoto.onPickFromCaptureWithCrop(Uri.fromFile(file), cropOptions);
                break;
            case "从相册选择":
                takePhoto.onPickFromGalleryWithCrop(Uri.fromFile(file), cropOptions);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.bt_confirm:
                addGoods();
                break;
            case R.id.iv_img:
                view.showContextMenu();
                break;
        }
    }

    /**
     * 添加商品
     */
    private void addGoods() {

        if (et_productName.getText() == null) {
            Toast.makeText(this, "请输入商品名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_price.getText() == null) {
            Toast.makeText(this, "请输入商品单价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_description.getText() == null) {
            Toast.makeText(this, "请输入商品介绍", Toast.LENGTH_SHORT).show();
            return;
        }
        if (file == null || !file.exists() || file.length() <= 0) {
            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadImage(file);
    }

    private void uploadParams(String imageName) {

        String name = et_productName.getText().toString();
        String price = et_price.getText().toString();
        String type = sp_type.getSelectedItem().toString();
        String description = et_description.getText().toString();
        RequestParams params = new RequestParams(isAdd ? Urls.addGoodsAction : Urls.updateGoodsAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("goodsName", name);
        params.addBodyParameter("goodsPrice", price);
        params.addBodyParameter("goodsType", type);
        params.addBodyParameter("goodsDescription", description);
        params.addBodyParameter("goodsName", imageName);
        if (!isAdd) {
            params.addBodyParameter("goodsId", product.getGOODS_ID());
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //TODO 图片没有写入数据库
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(AddProductActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    Toast.makeText(AddProductActivity.this, (isAdd ? "新增" : "修改") + "商品成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, (isAdd ? "新增" : "修改") + "商品失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(AddProductActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        file = new File(compressPath);
        Bitmap bitmap = BitmapFactory.decodeFile(compressPath);
        iv_img.setImageBitmap(bitmap);

    }

    private void uploadImage(File image) {
        RequestParams params = new RequestParams(Urls.uploadGoodsPicAction);
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("file", file));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        params.setMultipart(true);
//        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(AddProductActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    String data = baseResult.getData();
                    UploadImage image = JSON.parseObject(data, UploadImage.class);
                    uploadParams(image.getImageNewName());
                } else {
                    Toast.makeText(AddProductActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(AddProductActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(this, "取消选择图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
