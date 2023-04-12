package com.example.gptalk.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.data.utils.PrefUtil
import com.example.gptalk.R
import com.example.gptalk.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val finishtimeed: Long = 1000
    private var presstime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        this.onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val prefUtil = PrefUtil(applicationContext)

        if(prefUtil.getBoolean("IS_SHARE")){
            menuInflater.inflate(R.menu.menu_share, menu)
        }else{
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val prefUtil = PrefUtil(applicationContext)

        return when (item.itemId) {
            R.id.action_share ->{
                // 화면 새로고침
                finish()
                overridePendingTransition(0, 0)
                val intentRefresh = intent
                startActivity(intentRefresh)
                overridePendingTransition(0, 0)

                // 다른앱으로 텍스트 공유
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, prefUtil.getString("SHARE_ITEM"))
                }
                val shareIntent = Intent.createChooser(intent, "공유하기")
                startActivity(shareIntent)

                // 초기화
                prefUtil.setBoolean("IS_SHARE",false)
                prefUtil.setString("SHARE_ITEM","")
                true
            }
            R.id.action_settings -> {
                if (getForegroundFragment() is ChattingFragment) {
                    navController.navigate(
                        R.id.action_FirstFragment_to_SecondFragment
                    )
                } else {
                    navController.navigateUp(appBarConfiguration)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private val TAG = this.javaClass.simpleName
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val prefUtil = PrefUtil(applicationContext)
            if(getForegroundFragment() is ChattingFragment ){
                if(prefUtil.getBoolean("IS_SHARE")){// 공유하기 상태
                    // 초기화
                    prefUtil.setBoolean("IS_SHARE",false)
                    prefUtil.setString("SHARE_ITEM","")
                    // 화면 새로고침
                    finish()
                    overridePendingTransition(0, 0)
                    val intentRefresh = intent
                    startActivity(intentRefresh)
                    overridePendingTransition(0, 0)
                }else{
                    val tempTime = System.currentTimeMillis()
                    val intervalTime: Long = tempTime - presstime

                    if (intervalTime in 0..finishtimeed) {
                        finish()
                    } else {
                        presstime = tempTime
                        Toast.makeText(applicationContext, "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }else{
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigateUp()
            }
        }
    }


    private fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }
}