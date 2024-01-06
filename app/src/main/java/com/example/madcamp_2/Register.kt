package com.example.madcamp_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import android.util.Log
import android.widget.Toast
import com.example.madcamp_2.databinding.ActivityRegisterBinding
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val api = RetroInterface.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            binding.apply {
                val id = inputid.text.toString()
                val pw = inputpassword.text.toString()
                val pwcheck = inputpasswordconfirm.text.toString()
                val classes = inputclass.text.toString()

                if(id == "" || pw == "" || classes == "") {
                    Toast.makeText(applicationContext, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(pw != pwcheck) {
                    Toast.makeText(applicationContext, "패스워드를 다시한번 확인해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            val newUser = RegisterModel(binding.inputid.text.toString(), binding.inputpassword.text.toString(), binding.inputclass.text.toString())
            api.register(newUser).enqueue(object: retrofit2.Callback<RegisterResult>{
                override fun onResponse(call: Call<RegisterResult>, response: Response<RegisterResult>) {
                    val result = response.body()?.message ?: return
                    if(result)
                        Toast.makeText(applicationContext, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(applicationContext, "회원가입 실패, 이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                    Log.d("testt", t.message.toString())
                }
            })
        }
        binding.idsubmit.setOnClickListener {
            binding.apply {
                val id = inputid.text.toString()

                if(id == "") {
                    Toast.makeText(applicationContext, "id를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val idCert = IdCertificationModel(binding.inputid.text.toString())
            api.idcert(idCert).enqueue(object: Callback<IdCertificationResult>{
                override fun onResponse(call: Call<IdCertificationResult>, response: Response<IdCertificationResult>) {
                    val isExist = response.body()?.isExist ?: return
                    if(isExist) {
                        Toast.makeText(applicationContext, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                        Log.d("이미 존재하는 아이디입니다.", isExist.toString())
                    }
                    else{
                        Toast.makeText(applicationContext, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                        Log.d("사용 가능한 아이디입니다.", isExist.toString())
                    }
                }

                override fun onFailure(call: Call<IdCertificationResult>, t: Throwable) {
                    Log.d("testt", t.message.toString())
                }
            })
        }

        binding.arrowback.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}