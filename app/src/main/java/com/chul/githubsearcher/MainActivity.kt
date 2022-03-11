package com.chul.githubsearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            val searchUserFragment = SearchUserFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, searchUserFragment)
                .commit()
        }
    }
}