package com.example.diydialogdemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by RAYn on 2021/6/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyDialog.OnCenterItemClickListener {
    private Button mBtnBasicDialog;
    private Button mBtnListDialog;
    private Button mBtnSingleChooseDialog;
    private Button mBtnMultipleChooseDialog;
    private Button mBtnWaitingDialog;
    private Button mBtnProgressDialog;
    private Button mBtnHalfDiyDialog;
    private Button mBtnFullDiyDialog;
    private Button mBtnFullDiy;
    private MyDialog mMyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mBtnBasicDialog = findViewById(R.id.btn_basic_dialog);
        mBtnListDialog = findViewById(R.id.btn_list_dialog);
        mBtnSingleChooseDialog = findViewById(R.id.btn_single_choose_dialog);
        mBtnMultipleChooseDialog = findViewById(R.id.btn_multiple_choice_dialog);
        mBtnWaitingDialog = findViewById(R.id.btn_waiting_dialog);
        mBtnProgressDialog = findViewById(R.id.btn_progress_dialog);
        mBtnHalfDiyDialog = findViewById(R.id.btn_semi_diy_dialog);
        mBtnFullDiyDialog = findViewById(R.id.btn_full_diy_dialog);
        mBtnFullDiy = findViewById(R.id.btn_full_diy);
        mMyDialog = new MyDialog(this, R.layout.dialog_full_diy, new int[]{R.id.btn_dialog});

        mBtnBasicDialog.setOnClickListener(this);
        mBtnListDialog.setOnClickListener(this);
        mBtnSingleChooseDialog.setOnClickListener(this);
        mBtnMultipleChooseDialog.setOnClickListener(this);
        mBtnWaitingDialog.setOnClickListener(this);
        mBtnProgressDialog.setOnClickListener(this);
        mBtnHalfDiyDialog.setOnClickListener(this);
        mBtnFullDiyDialog.setOnClickListener(this);
        mBtnFullDiy.setOnClickListener(this);
        mMyDialog.setOnCenterItemClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //基本对话框
            case R.id.btn_basic_dialog:
                showBasicDialog();
                break;
            //列表对话框
            case R.id.btn_list_dialog:
                showListDialog();
                break;
            //单选对话框
            case R.id.btn_single_choose_dialog:
                showSingleChoiceDialog();
                break;
            //多选对话框
            case R.id.btn_multiple_choice_dialog:
                showMultipleChoiceDialog();
                break;
            //等待对话框
            case R.id.btn_waiting_dialog:
                showWaitingDialog();
                break;
            //进度条对话框
            case R.id.btn_progress_dialog:
                showProgressDialog();
                break;
            //半自定义对话框
            case R.id.btn_semi_diy_dialog:
                showHalfDiyDialog();
                break;
            //自定义对话框
            case R.id.btn_full_diy_dialog:
                showFullDiyDialog();
                break;
            //完全自定义
            case R.id.btn_full_diy:
                mMyDialog.show();
                break;
            default:
                break;
        }
    }

    /**
     * 自定义对话框
     */
    private void showFullDiyDialog() {
        AlertDialog.Builder fullDiyDialog = new AlertDialog.Builder(this, R.style.MyDialog);
        //加载DIY界面
        fullDiyDialog.setView(R.layout.dialog_diy);
        AlertDialog dialog = fullDiyDialog.create();
        //显示界面
        dialog.show();
    }

    /**
     * 半自定义对话框
     */
    private void showHalfDiyDialog() {
        AlertDialog.Builder halfDiyDialog = new AlertDialog.Builder(this);
        halfDiyDialog.setTitle("半自定义对话框");
        halfDiyDialog.setIcon(R.mipmap.ic_launcher);
        halfDiyDialog.setMessage("Live or death.");
        halfDiyDialog.setPositiveButton("生存", (dialog, which) ->
                Toast.makeText(MainActivity.this, "你选择了生存", Toast.LENGTH_SHORT).show());
        halfDiyDialog.setNegativeButton("死亡", (dialog, which) ->
                Toast.makeText(MainActivity.this, "你选择了死亡", Toast.LENGTH_SHORT).show());
        halfDiyDialog.setNeutralButton("不生不死", (dialog, which) ->
                Toast.makeText(MainActivity.this, "你选择了不生不死", Toast.LENGTH_SHORT).show());
        AlertDialog dialog = halfDiyDialog.create();
        //显示
        dialog.show();
        //自定义内容,放在show之后,否则有些属性是没有效果的,比如height和width
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        //获取屏幕宽和高
        Display display = windowManager.getDefaultDisplay();
        //获取对话框当前的参数值
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //设置高度和宽度
        //高度设置为屏幕的0.4
        layoutParams.height = (int) (display.getHeight() * 0.4);
        //宽度设置为屏幕的0.6
        layoutParams.width = (int) (display.getWidth() * 0.6);
        //设置位置
        layoutParams.gravity = Gravity.TOP;
        //设置透明度
        layoutParams.alpha = 0.8f;
        window.setAttributes(layoutParams);
    }

    /**
     * 进度条对话框
     */
    private void showProgressDialog() {
        final int max = 100;
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("这是一个进度条对话框");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(max);
        progressDialog.show();
        /**
         * 开个线程
         */
        new Thread(() -> {
            int p = 0;
            while (p < max) {
                try {
                    Thread.sleep(100);
                    p++;
                    progressDialog.setProgress(p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Looper.prepare();
            Toast.makeText(this, "Finish!Congratulation!", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
            Looper.loop();
        }).start();
    }

    /**
     * 等待对话框
     */
    private void showWaitingDialog() {
        final int max = 100;
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("这是一个等待的Dialog");
        progressDialog.setMessage("等待中...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    /**
     * 多选对话框
     */
    ArrayList<Integer> choices = new ArrayList<>();

    private void showMultipleChoiceDialog() {
        final String[] items = {"我是1", "我是2", "我是3"};
        //设置默认选择都是false
        final boolean[] initChoices = new boolean[]{false, false, false};
        choices.clear();
        AlertDialog.Builder multiChoiceDialog = new AlertDialog.Builder(this);
        multiChoiceDialog.setIcon(R.mipmap.ic_launcher);
        multiChoiceDialog.setTitle("这是一个多选Dialog");
        multiChoiceDialog.setMultiChoiceItems(items, initChoices, (dialog, which, isChecked) -> {
            if (isChecked) {
                choices.add(which);
                Toast.makeText(MainActivity.this, "你选中了 " + items[which], Toast.LENGTH_SHORT).show();
            } else {
                //坑--->不强转Integer会报错java.lang.IndexOutOfBoundsException: Invalid index 1, size is 1
                //需强制转换为Object类型,才会以对象的形式删除;否则会作为下标处理
                choices.remove((Integer) which);
                Toast.makeText(MainActivity.this, "你移除了 " + items[which], Toast.LENGTH_SHORT).show();
            }
        });
        multiChoiceDialog.setPositiveButton("确定", (dialog, which) -> {
            int size = choices.size();
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < size; i++) {
                str.append(items[choices.get(i)]);
            }
            Toast.makeText(MainActivity.this, "你选中的是 " + str, Toast.LENGTH_SHORT).show();
        });
        multiChoiceDialog.show();
    }

    /**
     * 单选对话框
     */
    int choice;

    private void showSingleChoiceDialog() {
        final String[] items = {"我是1", "我是2", "我是3"};
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(this);
        singleChoiceDialog.setIcon(R.mipmap.ic_launcher_round);
        singleChoiceDialog.setTitle("我是单选Dialog");
        //第二个参数checkedItem是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, (dialog, which) -> {
            choice = which;
            Toast.makeText(MainActivity.this, "Your choice is " + items[choice], Toast.LENGTH_SHORT).show();
        });
        singleChoiceDialog.setPositiveButton("确定", (dialog, which) -> {
            if (choice != -1) {
                Toast.makeText(MainActivity.this, "你最终选择了 " + items[choice], Toast.LENGTH_SHORT).show();
            }
        });
        singleChoiceDialog.show();
    }

    /**
     * 列表对话框
     */
    private void showListDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4", "我是5", "我是6", "我是7", "我是8"};
        final AlertDialog.Builder listDialog = new AlertDialog.Builder(this);
        listDialog.setIcon(R.mipmap.ic_launcher_round);
        listDialog.setTitle("这是列表对话框");
        listDialog.setItems(items, (dialog, which) -> Toast.makeText(MainActivity.this,
                "你点击了" + items[which], Toast.LENGTH_SHORT).show());
        listDialog.show();
    }

    /**
     * 基本对话框
     */
    private void showBasicDialog() {
        final AlertDialog.Builder basicDialog = new AlertDialog.Builder(this);
        //图标
        basicDialog.setIcon(R.mipmap.ic_launcher_round);
        //标题
        basicDialog.setTitle("基本对话框");
        //提示信息
        basicDialog.setMessage("Death or live.");
        //Positive积极
        basicDialog.setPositiveButton("生存", (dialog, which) ->
                Toast.makeText(this, "你点击了生存", Toast.LENGTH_SHORT).show());
        //Negative消极
        basicDialog.setNegativeButton("死亡", (dialog, which) ->
                Toast.makeText(this, "你点击了死亡", Toast.LENGTH_SHORT).show());
        //Neutral中立
        basicDialog.setNeutralButton("生不如死", (dialog, which) ->
                Toast.makeText(this, "你点击了生不如死", Toast.LENGTH_SHORT).show());
        basicDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCenterItemClick(MyDialog myDialog, View view) {
        switch (view.getId()) {
            case R.id.btn_dialog:
                Button button = view.findViewById(R.id.btn_dialog);
                String s = button.getText().toString();
                Toast.makeText(this, "请 " + s, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}