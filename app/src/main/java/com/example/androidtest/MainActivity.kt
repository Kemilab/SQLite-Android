package com.example.androidtest


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class MainActivity : AppCompatActivity() {
    var brojac = 0
    private lateinit var sharedPreferences: SharedPreferences

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
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main)
        //Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onCreate")
        Log.d("My Log D", "onCreate")
        Log.v("My Log V", "onCreate")
        Log.w("My Log V", "onCreate")
        Log.e("My Log V", "onCreate")*/
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
        if (menu != null) {
            menu.setHeaderTitle(getString(R.string.choose))
        }
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

    override fun onStart() {
        super.onStart()
        //Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onStart")
        Log.d("My Log D", "onStart")
        Log.v("My Log V", "onStart")
        Log.w("My Log V", "onStart")
        Log.e("My Log V", "onStart")*/
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onResume")
        Log.d("My Log D", "onResume")
        Log.v("My Log V", "onResume")
        Log.w("My Log V", "onResume")
        Log.e("My Log V", "onResum")*/
    }

    override fun onPause() {
        super.onPause()
        //Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
        // brojac u sharedPreferences
        val textView = findViewById<TextView>(R.id.textViewCounter)
        val brojacValue = textView.text.toString().toIntOrNull() ?: 0
        val editor = sharedPreferences.edit()
        editor.putInt(STEPS, brojacValue)
        editor.apply()
        /*Log.i("MyLog", "onPause")
        Log.d("My Log D", "onPause")
        Log.v("My Log V", "onPause")
        Log.w("My Log V", "onPause")
        Log.e("My Log V", "onPause")*/
    }

    override fun onStop() {
        super.onStop()
        //Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onStop")
        Log.d("My Log D", "onStop")
        Log.v("My Log V", "onStop")
        Log.w("My Log V", "onStop")
        Log.e("My Log V", "onStop")*/
    }

    override fun onRestart() {
        super.onRestart()
        //Toast.makeText(applicationContext, "onRestart", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onRestart")
        Log.d("My Log D", "onRestart")
        Log.v("My Log V", "onRestart")
        Log.w("My Log V", "onRestart")
        Log.e("My Log V", "onRestart")*/
    }

    override fun onDestroy() {
        super.onDestroy()
        //Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
        /*Log.i("MyLog", "onDestroy")
        Log.d("My Log D", "onDestroy")
        Log.v("My Log V", "onDestroy")
        Log.w("My Log V", "onDestroy")
        Log.e("My Log V", "onDestroy")*/
    }

    fun setOnClickListenerUp(view: View) {
        brojac++
        Log.i("brojac", "Stannje je $brojac")
        val steps = findViewById<TextView>(R.id.textViewCounter) //promjeni to u neki broj
        steps.text = "$brojac"
        if (brojac == 10) {
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
            val firstName = findViewById<TextView>(R.id.textViewCounter)
            firstName.text = "$brojac"
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

}