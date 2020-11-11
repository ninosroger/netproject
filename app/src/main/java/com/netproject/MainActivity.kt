package com.netproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.netproject.repository.component.RepositoryComponent
import com.netproject.repository.component.arashi
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yzm.setOnClickListener {
            if (sjh.text.isNullOrEmpty()) {
                Toast.makeText(baseContext, "请输入手机号", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            RepositoryComponent.api.getYzm("1", sjh.text.toString()).arashi({
                if (it["msg1"].asString == "1") {
                    Toast.makeText(baseContext, "获取验证码成功", Toast.LENGTH_SHORT).show()
                } else if (it["msg1"].toString().contains("积分余额")) {
                    Toast.makeText(baseContext, "获取验证码失败", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, it["msg1"].toString(), Toast.LENGTH_SHORT).show()
                }
            }, { Toast.makeText(baseContext, "获取验证码失败", Toast.LENGTH_SHORT).show() })
        }
        dl.setOnClickListener {
            if (sjh.text.isNullOrEmpty()) {
                Toast.makeText(baseContext, "请输入手机号", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (yzm.text.isNullOrEmpty()) {
                Toast.makeText(baseContext, "请输入动态密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            RepositoryComponent.api.getP("2", sjh.text.toString(), yzm.text.toString()).arashi({
                if (it["msg"].asString == "2") {
                    RepositoryComponent.other.radomLogin(
                        "true",
                        "0",
                        it["deviceId"].asString,
                        it["password"].asString,
                        "WIFI",
                        it["mobile"].asString,
                        "0",
                        it["timestamp"].asString,
                        "ChinaunicomMobileBusiness",
                        "",
                        "XiaoMI",
                        it["pip"].asString,
                        "general",
                        "android@5.94",
                        "XiaoMI6",
                        "android5.1.1",
                        it["deviceCode"].asString
                    ).arashi({
                        it.toString()
                    }, {
                        Toast.makeText(baseContext, it["dsc"].asString, Toast.LENGTH_SHORT).show()
                    })
                } else {
                    Toast.makeText(baseContext, it["msg"].asString, Toast.LENGTH_SHORT).show()
                }
            }, { Toast.makeText(baseContext, "登陆失败2", Toast.LENGTH_SHORT).show() })
        }
    }
}