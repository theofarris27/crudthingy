package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.loginandregistration.databinding.ActivityLoginBinding
import com.example.loginandregistration.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve any information from the intent

        var username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        var password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""



        //prefill the username and password fields
        //for EditTexts, you actually have to use the setText functions
        binding.editTextRegistrationUsername.setText(username)
        binding.editTextRegistrationPassword.setText(password)


        binding.button.setOnClickListener{
            password=binding.editTextRegistrationPassword.text.toString()
            username=binding.editTextRegistrationUsername.text.toString()
            val confirm=binding.editTextRegistrationConfirm.text.toString()
            val name=binding.editTextRegistrationName.text.toString()
            val email=binding.editTextRegistrationEmail.text.toString()





            if(RegistrationUtil.validatePassword(password, confirm)&&
                RegistrationUtil.validateUsername(username)&&
                RegistrationUtil.validateEmail(email)&&
                RegistrationUtil.validateName(name)){

                val resultIntent = Intent().apply {
                    // apply putExtra is doing the same thing as resultIntent.putExtra()
                    //apply lambda will call the functions inside it on the object
                    putExtra(LoginActivity.EXTRA_USERNAME,binding.editTextRegistrationUsername.text.toString())
                    putExtra(LoginActivity.EXTRA_PASSWORD, binding.editTextRegistrationPassword.text.toString())
                }
                setResult(Activity.RESULT_OK, resultIntent)

                val user = BackendlessUser()
                user.setProperty("username", "$username")
                user.setProperty("name", name)
                user.setProperty("email", email)
                user.password = "$password"
                Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(registeredUser: BackendlessUser?) {
                        Log.d(LoginActivity.TAG,"handlReponse: it worked)")
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d(LoginActivity.TAG,"handlReponse: ${fault.message}")
                    }
                })
                finish()
            }
        }
    }
}