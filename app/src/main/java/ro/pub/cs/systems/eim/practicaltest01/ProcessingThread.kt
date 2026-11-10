package ro.pub.cs.systems.eim.practicaltest01

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Date
import kotlin.math.sqrt
import kotlin.random.Random

class ProcessingThread(
    private val context: Context,
    firstNumber: Int,
    secondNumber: Int
) : Thread() {

    @Volatile
    private var isRunning = true

    private val arithmeticMean = (firstNumber + secondNumber) / 2.0
    private val geometricMean = sqrt(firstNumber.toDouble() * secondNumber.toDouble())

    override fun run() {
        while (isRunning) {
            sendMessage()
            try {
                sleep(10000)
            } catch (e: InterruptedException) {
                Log.e(Constants.TAG, "Thread interrupted")
            }
        }
    }

    private fun sendMessage() {
        val intent = Intent()
        intent.action = Constants.ACTION_TYPES[Random.nextInt(Constants.ACTION_TYPES.size)]
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_EXTRA,
            "${Date()} arithmetic mean: $arithmeticMean, geometric mean: $geometricMean"
        )
        context.sendBroadcast(intent)
    }

    fun stopThread() {
        isRunning = false
    }
}