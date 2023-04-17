package com.example.loginandregistration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class Loan(
    var name: String? = "",
    var reason: String? = "",
    var amountOwed: Double? = 0.0,
    var dateLent: Date? = Date(1679585858900),
    var amountRepaid: Double? = 0.0,
    var dateOfCompletion: Date? = null,
    var fullyRepaid: Boolean? = false,
    var ownerId: String? = null,
    var objectId: String? = null
): Parcelable
{
     fun calculateAmountRemaining(): Double?{
        return amountOwed?.minus(amountRepaid!!)
    }
}
