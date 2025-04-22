package com.example.clock

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

data class Lap(
    val number: Int,
    val time: Long
)

class LapAdapter : RecyclerView.Adapter<LapAdapter.LapViewHolder>() {
    private val laps = mutableListOf<Lap>()

    class LapViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lapNumber: TextView = view.findViewById(R.id.lap_number)
        val lapTime: TextView = view.findViewById(R.id.lap_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LapViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lap, parent, false)
        return LapViewHolder(view)
    }

    override fun onBindViewHolder(holder: LapViewHolder, position: Int) {
        val lap = laps[position]
        holder.lapNumber.text = "Lap ${lap.number}"
        holder.lapTime.text = formatTime(lap.time)
    }

    override fun getItemCount() = laps.size

    fun addLap(lap: Lap) {
        laps.add(0, lap)
        notifyItemInserted(0)
    }

    fun clearLaps() {
        laps.clear()
        notifyDataSetChanged()
    }

    private fun formatTime(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        val millis = milliseconds % 1000

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis)
    }
}

class StopwatchFragment : Fragment() {
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning = false
    private var lapCount = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var stopwatchDisplay: TextView
    private lateinit var millisecondsDisplay: TextView
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button
    private lateinit var lapButton: Button
    private lateinit var lapsRecyclerView: RecyclerView
    private lateinit var lapAdapter: LapAdapter

    private val updateTime = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = System.currentTimeMillis()
                val totalTime = elapsedTime + (currentTime - startTime)
                updateDisplay(totalTime)
                handler.postDelayed(this, 10)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stopwatchDisplay = view.findViewById(R.id.stopwatch_display)
        millisecondsDisplay = view.findViewById(R.id.milliseconds_display)
        startStopButton = view.findViewById(R.id.start_stop_button)
        resetButton = view.findViewById(R.id.reset_button)
        lapButton = view.findViewById(R.id.lap_button)
        lapsRecyclerView = view.findViewById(R.id.laps_recycler_view)

        lapAdapter = LapAdapter()
        lapsRecyclerView.layoutManager = LinearLayoutManager(context)
        lapsRecyclerView.adapter = lapAdapter

        startStopButton.setOnClickListener {
            if (isRunning) {
                stopStopwatch()
            } else {
                startStopwatch()
            }
        }

        resetButton.setOnClickListener {
            resetStopwatch()
        }

        lapButton.setOnClickListener {
            if (isRunning) {
                recordLap()
            }
        }

        updateButtonStates()
    }

    private fun startStopwatch() {
        startTime = System.currentTimeMillis()
        isRunning = true
        handler.post(updateTime)
        updateButtonStates()
    }

    private fun stopStopwatch() {
        isRunning = false
        elapsedTime += System.currentTimeMillis() - startTime
        handler.removeCallbacks(updateTime)
        updateButtonStates()
    }

    private fun resetStopwatch() {
        isRunning = false
        elapsedTime = 0
        startTime = 0
        lapCount = 0
        handler.removeCallbacks(updateTime)
        updateDisplay(0)
        lapAdapter.clearLaps()
        updateButtonStates()
    }

    private fun recordLap() {
        val currentTime = System.currentTimeMillis()
        val totalTime = elapsedTime + (currentTime - startTime)
        lapCount++
        lapAdapter.addLap(Lap(lapCount, totalTime))
    }

    private fun updateDisplay(totalTime: Long) {
        val hours = TimeUnit.MILLISECONDS.toHours(totalTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime) % 60
        val millis = totalTime % 1000

        stopwatchDisplay.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        millisecondsDisplay.text = String.format(".%03d", millis)
    }

    private fun updateButtonStates() {
        startStopButton.text = if (isRunning) "Stop" else "Start"
        lapButton.isEnabled = isRunning
        resetButton.isEnabled = !isRunning && (elapsedTime > 0 || lapCount > 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateTime)
    }
} 