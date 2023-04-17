package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder

import com.example.loginandregistration.Constants.API_KEY
import com.example.loginandregistration.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    companion object{
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
        val TAG = "LoginActivity"
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            // Handle the Intent
            binding.editTextLoginUsername.setText(intent?.getStringExtra(LoginActivity.EXTRA_USERNAME))
            binding.editTextLoginPassword.setText(intent?.getStringExtra(LoginActivity.EXTRA_PASSWORD))
        }
    }



    private lateinit var binding: ActivityLoginBinding

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityLoginBinding.inflate(layoutInflater)
                setContentView(binding.root)
                Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)
                binding.textViewLoginSignup.setOnClickListener {
                    val registrationIntent = Intent(this, RegistrationActivity::class.java)
                    registrationIntent.putExtra(EXTRA_USERNAME,
                    binding.editTextLoginUsername.text.toString())
                    registrationIntent.putExtra(EXTRA_PASSWORD, binding.editTextLoginPassword.text.toString())
                    //3a. launch the new activity using the intent
                    //startActivity(registrationIntent)
                    //3. launch the activity for a result using the variable from the register for result contract above
                    startForResult.launch(registrationIntent)
                }



                binding.buttonMainLogin.setOnClickListener(){
                    Backendless.UserService.login(
                        binding.editTextLoginUsername.text.toString(),
                        binding.editTextLoginPassword.text.toString(),
                        object : AsyncCallback<BackendlessUser?> {
                            override fun handleResponse(response: BackendlessUser?) {
                                Log.d(TAG, "handleResponseL ${response?.getProperty("username")} has logged in")
                                val userId = response!!.objectId
                                val loginIntent = Intent(this@LoginActivity, LoanListActivity::class.java)
                                loginIntent.putExtra(LoanListActivity.EXTRA_USER_ID,userId)
                                startForResult.launch(loginIntent)
                            }

                            override fun handleFault(fault: BackendlessFault?) {
                                    Log.d(TAG, "handleFault: ${fault?.message}")
                            }
                        }
                    )

                }

        }

    }