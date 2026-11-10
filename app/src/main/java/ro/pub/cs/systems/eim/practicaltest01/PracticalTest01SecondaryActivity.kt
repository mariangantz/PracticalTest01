package ro.pub.cs.systems.eim.practicaltest01

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01SecondaryActivity : AppCompatActivity() {

    private val buttonClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.ok_button -> setResult(Activity.RESULT_OK)
            R.id.cancel_button -> setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_secondary)

        val numberOfClicksTextView: TextView = findViewById(R.id.number_of_clicks_text_view)
        val numberOfClicks = intent.getIntExtra("numberOfClicks", 0)
        numberOfClicksTextView.text = numberOfClicks.toString()

        findViewById<Button>(R.id.ok_button).setOnClickListener(buttonClickListener)
        findViewById<Button>(R.id.cancel_button).setOnClickListener(buttonClickListener)
    }
}