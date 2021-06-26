package com.example.diydialog_kotlin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.diydialog_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, MyDialog.OnCenterItemClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBasicDialog.setOnClickListener(this)
        binding.btnListDialog.setOnClickListener(this)
        binding.btnSingleChooseDialog.setOnClickListener(this)
        binding.btnMultipleChoiceDialog.setOnClickListener(this)
        binding.btnWaitingDialog.setOnClickListener(this)
        binding.btnProgressDialog.setOnClickListener(this)
        binding.btnSemiDiyDialog.setOnClickListener(this)
        binding.btnFullDiyDialog.setOnClickListener(this)
        binding.btnFullDiy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //基本对话框
            R.id.btn_basic_dialog -> showBasicDialog()
            //列表对话框
            R.id.btn_list_dialog -> showListDialog()
            //单选对话框
            R.id.btn_single_choose_dialog -> showSingleChooseDialog()
            //多选对话框
            R.id.btn_multiple_choice_dialog -> showMultipleChoiceDialog()
            //等待对话框
            R.id.btn_waiting_dialog -> showWaitingDialog()
            //进度条对话框
            R.id.btn_progress_dialog -> showProgressDialog()
            //半自定义对话框
            R.id.btn_semi_diy_dialog -> showSemiDiyDialog()
            //自定义对话框
            R.id.btn_full_diy_dialog -> showFullDiyDialog()
            //完全自定义
            R.id.btn_full_diy -> {
                val myDialog =
                    MyDialog(this, R.layout.dialog_full_diy, intArrayOf(R.id.btn_dialog)).apply {
                        setOnCenterItemClickListener(this@MainActivity)
                    }
                myDialog.show()
            }
        }
    }

    /**
     * 自定义对话框
     */
    private fun showFullDiyDialog() {
        val fullDiyDialog = AlertDialog.Builder(this, R.style.MyDialog).apply {
            setView(R.layout.dialog_diy)
        }
        fullDiyDialog.show()
    }

    /**
     * 半自定义对话框
     */
    private fun showSemiDiyDialog() {
        val semiDiyDialog = AlertDialog.Builder(this).apply {
            setTitle("这是半自定义对话框")
            setIcon(R.mipmap.ic_launcher)
            setMessage("Live or death.")
            setPositiveButton("live") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose live.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("death") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose death.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNeutralButton("as good as death") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose as good as death.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val dialogWindow = semiDiyDialog.show().window
        val dialogManager = windowManager
        //获取屏幕宽、高
        val dialogDisplay = dialogManager.defaultDisplay
        //获取对话框当前的参数值
        val p = dialogWindow?.attributes
        //高度设置为屏幕的0.4
        p?.height = (dialogDisplay.height * 0.4).toInt()
        //宽度设置为屏幕的0.6
        p?.width = (dialogDisplay.width * 0.6).toInt()
        //设置位置
        p?.gravity = Gravity.CENTER
        //设置透明度
        p?.alpha = 0.8f
        dialogWindow?.attributes = p
    }

    /**
     * 进度条对话框
     */
    private fun showProgressDialog() {
        val max = 100
        val progressDialog = ProgressDialog(this).apply {
            setTitle("这是进度条对话框")
            setIcon(R.mipmap.ic_launcher_round)
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            this.max = max
        }
        progressDialog.show()
        Thread {
            var p = 0
            while (p < max) {
                try {
                    Thread.sleep(100)
                    p++
                    progressDialog.progress = p
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            //不使用Looper弹出Toast会报错:Can't toast on a thread that has not called Looper.prepare()
            Looper.prepare()
            Toast.makeText(
                this,
                "进度完成",
                Toast.LENGTH_SHORT
            ).show()
            progressDialog.cancel()
            Looper.loop()
        }.start()
    }

    /**
     * 等待对话框
     */
    private fun showWaitingDialog() {
        val waitingDialog = ProgressDialog(this).apply {
            setTitle("这是等待对话框")
            setIcon(R.mipmap.ic_launcher_round)
            setMessage("Please waiting...")
            setCanceledOnTouchOutside(true)
        }
        waitingDialog.show()
    }

    /**
     * 多选对话框
     */
    private fun showMultipleChoiceDialog() {
        val choices = ArrayList<Int>()
        val items = arrayOf("Item1", "Item2", "Item3")
        val initChoices = booleanArrayOf(false, false, false)
        choices.clear()
        val multipleChoiceDialog = AlertDialog.Builder(this).apply {
            setTitle("这是多选对话框")
            setIcon(R.mipmap.ic_launcher)
            setMultiChoiceItems(items, initChoices) { _, which, isChecked ->
                if (isChecked) {
                    choices.add(which)
                    Toast.makeText(
                        this@MainActivity,
                        "你选中了 " + items[which],
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    choices.remove(which)
                    Toast.makeText(
                        this@MainActivity,
                        "你移除了 " + items[which],
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            setPositiveButton("Confirm") { _, which ->
                val size = choices.size
                val str = StringBuilder()
                for (i in 0 until size) {
                    str.append(items[choices[i]])
                }
                Toast.makeText(
                    this@MainActivity,
                    "你最终选择了 $str",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        multipleChoiceDialog.show()
    }

    /**
     * 单选对话框
     */
    private fun showSingleChooseDialog() {
        var choice = 0
        val items = arrayOf("Item1", "Item2", "Item3")
        val singleChooseDialog = AlertDialog.Builder(this).apply {
            setTitle("这是单选对话框")
            setIcon(R.mipmap.ic_launcher)
            setSingleChoiceItems(items, 0) { _, which ->
                choice = which
                Toast.makeText(
                    this@MainActivity,
                    "你选中了 " + items[choice],
                    Toast.LENGTH_SHORT
                ).show()
            }
            setPositiveButton("确定") { _, _ ->
                if (choice != -1) {
                    Toast.makeText(
                        this@MainActivity,
                        "你最终选择了 " + items[choice],
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        singleChooseDialog.show()
    }

    /**
     * 列表对话框
     */
    @SuppressLint("ShowToast")
    private fun showListDialog() {
        val items = arrayOf("Item1", "Item2", "Item3")
        val listDialog = AlertDialog.Builder(this).apply {
            setTitle("这是列表对话框")
            setIcon(R.mipmap.ic_launcher_round)
            setItems(items) { _, which ->
                Toast.makeText(
                    this@MainActivity,
                    "You click " + items[which],
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        listDialog.show()
    }

    /**
     * 基本对话框
     */
    private fun showBasicDialog() {
        val basicDialog = AlertDialog.Builder(this).apply {
            setTitle("这是基本对话框")
            setIcon(R.mipmap.ic_launcher)
            setMessage("Live or death.")
            setPositiveButton("Live") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose live.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("Death") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose death.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNeutralButton("As good as death") { _, _ ->
                Toast.makeText(
                    this@MainActivity,
                    "You choose as good as death.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        basicDialog.show()
    }

    override fun onCenterItemClick(myDialog: MyDialog, view: View) {
        when (view.id) {
            R.id.btn_dialog -> {
                val str = view.findViewById<Button>(R.id.btn_dialog).text.toString()
                Toast.makeText(this, "请 $str ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}