package com.hemanth.winuhall

import android.content.Context
import android.os.StrictMode
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hemanth.winuhall.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppController : DaggerApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}