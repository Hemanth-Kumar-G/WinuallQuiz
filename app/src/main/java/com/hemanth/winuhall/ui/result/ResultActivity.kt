package com.hemanth.winuhall.ui.result

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.hemanth.winuhall.R
import com.hemanth.winuhall.common.Constants
import com.hemanth.winuhall.data.model.ResultResponse
import com.hemanth.winuhall.databinding.ActivityResultBinding
import dagger.android.support.DaggerAppCompatActivity

/**
 *<h1>ResultActivity</h1>
 * <p>this activity is for displaying the result of the user</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class ResultActivity : DaggerAppCompatActivity() {

    lateinit var mBinding: ActivityResultBinding
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        fetchDataFromFireBase()
    }

    /**
     * <h2>fetchDataFromFireBase</h2>
     * this method is to fetch result of the user from firebase
     */
    private fun fetchDataFromFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.USERS)
        mDatabase.child(Constants.HARD_CODED_USER)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val result: ResultResponse? = dataSnapshot.getValue(ResultResponse::class.java)
                    result?.let { mBinding.result = it }
                }

                override fun onCancelled(error: DatabaseError) {
                    showError(error.message)
                }
            })
    }

    private fun showError(error: String) {
        val snackBar: Snackbar = Snackbar.make(mBinding.root, error, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(this, R.color.snackbar_error_background_color)
        )
        snackBar.show()
    }

}