package com.example.loginandregistration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.loginandregistration.databinding.ActivityLoanListBinding


class LoanListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanListBinding
    private lateinit var adapter: LoanAdapter

    companion object{
        var THEO = "theo"
        var EXTRA_USER_ID = "userId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userId = intent.getStringExtra(EXTRA_USER_ID)!!
        binding.fabCreateNewLoan.setOnClickListener{
            val loanDetailIntent = Intent(this, LoanDetailActivity::class.java).apply{
                putExtra(EXTRA_USER_ID, userId)
            }
            startActivity(loanDetailIntent)
        }
    }

    private fun retrieveAllData(userId : String){
        val whereClause = "ownerId='$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Loan::class.java).find(queryBuilder, object :
        AsyncCallback<List<Loan?>?> {
            override fun handleResponse(response: List<Loan?>?) {
                Log.d("LoanList", "handleResponse : $response")
                adapter = LoanAdapter(response as MutableList<Loan>)
                binding.recyclerViewLoanList.adapter = adapter
                binding.recyclerViewLoanList.layoutManager = LinearLayoutManager(this@LoanListActivity)
            }

            override fun handleFault(fault: BackendlessFault?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        var userId = intent.getStringExtra(EXTRA_USER_ID)!!
        retrieveAllData(userId)
    }

}