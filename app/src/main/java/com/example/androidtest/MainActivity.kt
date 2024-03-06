package com.example.androidtest


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.androidtest.data.User
import com.example.androidtest.data.UserViewModel
import java.time.LocalTime
import java.util.Locale


class MainActivity : AppCompatActivity() {
    var brojac = 0
    var press = 0
    private lateinit var sharedPreferences: SharedPreferences

   private lateinit var mUserViewModel: UserViewModel

    companion object {
        const val STEPS = "steps"
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restore_counter -> {
                brojac = 0
                val steps = findViewById<TextView>(R.id.textViewCounter)
                steps.text = "$brojac" //displays the value
                true
                mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                mUserViewModel.deleteAll()
                Toast.makeText(applicationContext,
                    getString(R.string.storage_deleted), Toast.LENGTH_SHORT).show()
            }
            R.id.croatian -> {
                changeLanguage(this, "hr")
                recreate()
                return true
            }

            R.id.english -> {
                changeLanguage(this, "en-us")
                recreate()
                return true
            }

            R.id.analytics ->{
                val intent = Intent(this, AnalyticsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        // Restore
        brojac = sharedPreferences.getInt(STEPS, 0)

        // Update Text
        val textView = findViewById<TextView>(R.id.textViewCounter)
        textView.text = "$brojac"
        //context menu - long press
        val textCounter = findViewById<TextView>(R.id.textViewCounter)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        registerForContextMenu(textView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_float, menu)
        menu?.setHeaderTitle(getString(R.string.choose))
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.context_reset ->{
                Toast.makeText(this, getString(R.string.value_was_deleted), Toast.LENGTH_SHORT).show()
                brojac = 0
                val steps = findViewById<TextView>(R.id.textViewCounter)
                steps.text = "$brojac" //displays the value
                val textView = findViewById<TextView>(R.id.textViewCounter)
                val brojacValue = textView.text.toString().toIntOrNull() ?: 0
                val editor = sharedPreferences.edit()
                true

            }
            else -> super.onContextItemSelected(item)
        }
        super.onContextItemSelected(item)

    }

    override fun onPause() {
        super.onPause()
        // brojac u sharedPreferences
        val textView = findViewById<TextView>(R.id.textViewCounter)
        val brojacValue = textView.text.toString().toIntOrNull() ?: 0
        val editor = sharedPreferences.edit()
        editor.putInt(STEPS, brojacValue)
        editor.apply()
    }

    fun setOnClickListenerUp(view: View) {
        brojac++
        press ++
        insertDataToDatabase()
        Log.i("brojac", "Stannje je $brojac")
        val steps = findViewById<TextView>(R.id.textViewCounter) //promjeni to u neki broj
        steps.text = "$brojac"
        val number: Int = brojac
        if (number == 10) {
            val intent = Intent(this, SuccessActivity::class.java).apply {
                putExtra("name", findViewById<TextView>(R.id.plainTextName).text.toString())
            }
            startActivity(intent)
        }
    }

    fun setOnClickListenerDown(view: View) {
        if (brojac > 0) {
            brojac--
            Log.i("brojac", "Stannje je $brojac")
            val steps = findViewById<TextView>(R.id.textViewCounter)
            steps.text = "$brojac"
        } else {
            Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    //languge
    @Suppress("DEPRECATION")
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        res.updateConfiguration(config, res.displayMetrics)
    }

    private fun insertDataToDatabase() {
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val name = findViewById<TextView>(R.id.plainTextName).text.toString()
        val time = LocalTime.now()
        val timeString = time.toString() // this stores the value of time
        if(TextUtils.isEmpty(name)){
            Log.e("Database", "User didn't enter its name")
            Toast.makeText(applicationContext,
                getString(R.string.enter_your_name), Toast.LENGTH_SHORT).show()
        } else {
            if (press < 10) {
                Log.i("press","")
            } else {
                press -= 10
                val user = User(0, name, timeString, brojac)
                mUserViewModel.addUser(user)
            }
        }
    }

}