package ro.pub.cs.systems.eim.practicaltest01

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01MainActivity : AppCompatActivity() {

    private lateinit var leftEditText: EditText
    private lateinit var rightEditText: EditText
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button

    private val buttonClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.left_button -> {
                val number = leftEditText.text.toString().toInt()
                leftEditText.setText((number + 1).toString())
            }
            R.id.right_button -> {
                val number = rightEditText.text.toString().toInt()
                rightEditText.setText((number + 1).toString())
            }
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
    }
}