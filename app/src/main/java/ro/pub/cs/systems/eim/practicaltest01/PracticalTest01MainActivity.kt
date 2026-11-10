package ro.pub.cs.systems.eim.practicaltest01

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat

class PracticalTest01MainActivity : AppCompatActivity() {

    private lateinit var leftEditText: EditText
    private lateinit var rightEditText: EditText
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button

    private lateinit var navigateButton: Button
    private val SECONDARY_ACTIVITY_REQUEST_CODE = 1

    private var serviceStarted = false

    private val intentFilter = IntentFilter()
    private val messageBroadcastReceiver = MessageBroadcastReceiver()

    private inner class MessageBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)
            Log.d(Constants.TAG, "Action: ${intent.action} | $message")
        }
    }

    private val buttonClickListener = View.OnClickListener { view ->
        var leftNumber = leftEditText.text.toString().toInt()
        var rightNumber = rightEditText.text.toString().toInt()
        when (view.id) {
            R.id.left_button -> {
                leftNumber++;
                leftEditText.setText(leftNumber.toString())
            }
            R.id.right_button -> {
                rightNumber++;
                rightEditText.setText(rightNumber.toString())
            }
        }
        if (leftNumber + rightNumber > Constants.NUMBER_OF_CLICKS_THRESHOLD && !serviceStarted) {
            val intent = Intent(applicationContext, PracticalTest01Service::class.java)
            intent.putExtra("firstNumber", leftNumber)
            intent.putExtra("secondNumber", rightNumber)
            applicationContext.startService(intent)
            serviceStarted = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_main)

        leftEditText = findViewById(R.id.left_edit_text)
        rightEditText = findViewById(R.id.right_edit_text)
        leftButton = findViewById(R.id.left_button)
        rightButton = findViewById(R.id.right_button)

        leftButton.setOnClickListener(buttonClickListener)
        rightButton.setOnClickListener(buttonClickListener)

        navigateButton = findViewById(R.id.navigate_to_secondary_activity_button)
        navigateButton.setOnClickListener {
            val intent = Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.PracticalTest01SecondaryActivity")
            val numberOfClicks = leftEditText.text.toString().toInt() +
                    rightEditText.text.toString().toInt()
            intent.putExtra("numberOfClicks", numberOfClicks)
            startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE)
        }
        for (action in Constants.ACTION_TYPES) {
            intentFilter.addAction(action)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        outState.putString("leftCount", leftEditText.text.toString())
        outState.putString("rightCount", rightEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("leftCount")?.let { leftEditText.setText(it) }
        savedInstanceState.getString("rightCount")?.let { rightEditText.setText(it) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result $resultCode", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            this,
            messageBroadcastReceiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    override fun onDestroy() {
        stopService(Intent(applicationContext, PracticalTest01Service::class.java))
        super.onDestroy()
    }
}