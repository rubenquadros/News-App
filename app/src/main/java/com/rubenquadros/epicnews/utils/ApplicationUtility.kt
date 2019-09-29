package com.rubenquadros.epicnews.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class ApplicationUtility {

    companion object {
        fun formatDate(dateString: String): String {
            val formatter = SimpleDateFormat("yyyy-mm-dd")
            val date = formatter.parse(dateString)
            return formatter.format(date)
        }

        fun showSnack(msg: String, view: View, action: String){
            val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(action) {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }
}