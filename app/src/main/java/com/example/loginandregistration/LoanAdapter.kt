package com.example.loginandregistration

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault


class LoanAdapter(var dataSet: MutableList<Loan>) : RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewReason: TextView
        val textViewAmountOwed: TextView
        val textViewDateLent: TextView
        val textViewDateRepaid: TextView
        val textViewAmountRemaining: TextView
        val checkBoxRepaid: CheckBox
        val layout: ConstraintLayout

        init {
            textViewName = view.findViewById(R.id.textView_name)
            textViewReason = view.findViewById(R.id.textView_reason)
            textViewAmountOwed = view.findViewById(R.id.textView_amount)
            textViewDateLent = view.findViewById(R.id.textView_dateLent)
            textViewDateRepaid = view.findViewById(R.id.textView_dateRepay)
            textViewAmountRemaining = view.findViewById(R.id.textView_amountRemaining)
            checkBoxRepaid = view.findViewById(R.id.checkBox_repaid)
            layout = view.findViewById(R.id.layout_loanItem)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_loan, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var loan = dataSet[position]
        val context = holder.textViewName.context
        holder.textViewName.text = loan.name
        holder.textViewReason.text = loan.reason
        holder.textViewAmountOwed.text = loan.amountOwed.toString()
        holder.textViewDateLent.text = loan.dateLent.toString()
        holder.textViewDateRepaid.text = loan.dateOfCompletion.toString()
        holder.textViewAmountRemaining.text = loan.calculateAmountRemaining().toString()
        holder.checkBoxRepaid.isChecked = loan.fullyRepaid == true
        holder.layout.isLongClickable = true
        holder.layout.setOnLongClickListener {
            val popMenu = PopupMenu(context, holder.textViewName)
            popMenu.inflate(R.menu.menu_loan_list_context)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_loanList_delete -> {  
                        deleteFromBackendless(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }
        holder.layout.setOnClickListener {
            val loanDetailActivity = Intent( it.context, LoanDetailActivity::class.java)
            loanDetailActivity.putExtra(LoanDetailActivity.EXTRA_LOAN, loan)
            it.context.startActivity(loanDetailActivity)
        }
    }

    private fun deleteFromBackendless(position: Int) {
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete ${dataSet[position]}")
        Backendless.Data.of(Loan::class.java).remove(dataSet[position],
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Contact has been deleted. The response is the
                    // time in milliseconds when the object was deleted
                }

                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be
                    // retrieved with fault.getCode()
                }
            })
    }

    override fun getItemCount() = dataSet.size

}