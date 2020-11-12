package com.netproject

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.netproject.repository.common.NetConstants
import com.netproject.repository.component.RepositoryComponent
import com.netproject.repository.component.arashi
import com.netproject.web.WebActivity
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
            RepositoryComponent.api.getP("2", sjh.text.toString(), pwd.text.toString()).arashi({
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
                    ).arashi({ fhz ->
                        if (fhz["code"].asString == "0") {
                            RepositoryComponent.api.login(
                                "4",
                                sjh.text.toString(),
                                NetApplication.cookies,
                                fhz.toString()
                            ).arashi({ result ->
                                if (result["msg4"].asString.contains("保存成功")) {
                                    startActivity(
                                        Intent(this, WebActivity::class.java).putExtra(
                                            "url",
                                            NetConstants.LOGIN_SUCCESS + sjh.text.toString()
                                        )
                                    )
                                    finish()
                                } else {
                                    Toast.makeText(baseContext, "登陆失败", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } else if (fhz["code"].asString == "4") {
                            Toast.makeText(baseContext, fhz["dsc"].asString, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }, {
                        Toast.makeText(baseContext, "登陆失败", Toast.LENGTH_SHORT).show()
                    })
                } else {
                    Toast.makeText(baseContext, it["msg"].asString, Toast.LENGTH_SHORT).show()
                }
            }, { Toast.makeText(baseContext, "登陆失败", Toast.LENGTH_SHORT).show() })
        }
    }
}