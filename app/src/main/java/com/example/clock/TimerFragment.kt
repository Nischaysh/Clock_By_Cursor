package com.example.clock

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment

class TimerFragment : Fragment() {
    private var hourPicker: NumberPicker? = null
    private var minutePicker: NumberPicker? = null
    private var secondPicker: NumberPicker? = null
    private var startButton: Button? = null
    private var resetButton: Button? = null
    private var timerDisplay: TextView? = null
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    private var isRunning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            hourPicker = view.findViewById(R.id.hour_picker)
            minutePicker = view.findViewById(R.id.minute_picker)
            secondPicker = view.findViewById(R.id.second_picker)
            startButton = view.findViewById(R.id.start_button)
            resetButton = view.findViewById(R.id.reset_button)
            timerDisplay = view.findViewById(R.id.timer_display)

            setupNumberPickers()
            setupButtons()
            updateDisplay()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupNumberPickers() {
        hourPicker?.apply {
            minValue = 0
            maxValue = 23
            value = 0
            setOnValueChangedListener { _, _, _ -> updateDisplay() }
        }

        minutePicker?.apply {
            minValue = 0
            maxValue = 59
            value = 0
            setOnValueChangedListener { _, _, _ -> updateDisplay() }
        }

        secondPicker?.apply {
            minValue = 0
            maxValue = 59
            value = 0
            setOnValueChangedListener { _, _, _ -> updateDisplay() }
        }
    }

    private fun setupButtons() {
        startButton?.setOnClickListener {
            if (isRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }

        resetButton?.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        try {
            val hours = hourPicker?.value ?: 0
            val minutes = minutePicker?.value ?: 0
            val seconds = secondPicker?.value ?: 0
            timeLeftInMillis = ((hours * 3600) + (minutes * 60) + seconds) * 1000L

            if (timeLeftInMillis > 0) {
                isRunning = true
                startButton?.text = "Stop"
                countDownTimer?.cancel()
                countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeLeftInMillis = millisUntilFinished
                        updateDisplay()
                    }

                    override fun onFinish() {
                        isRunning = false
                        startButton?.text = "Start"
                        updateDisplay()
                    }
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopTimer() {
        try {
            isRunning = false
            startButton?.text = "Start"
            countDownTimer?.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun resetTimer() {
        try {
            stopTimer()
            hourPicker?.value = 0
            minutePicker?.value = 0
            secondPicker?.value = 0
            timeLeftInMillis = 0
            updateDisplay()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateDisplay() {
        try {
            if (isRunning) {
                val hours = (timeLeftInMillis / 3600000).toInt()
                val minutes = ((timeLeftInMillis % 3600000) / 60000).toInt()
                val seconds = ((timeLeftInMillis % 60000) / 1000).toInt()
                timerDisplay?.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            } else {
                val hours = hourPicker?.value ?: 0
                val minutes = minutePicker?.value ?: 0
                val seconds = secondPicker?.value ?: 0
                timerDisplay?.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
        hourPicker = null
        minutePicker = null
        secondPicker = null
        startButton = null
        resetButton = null
        timerDisplay = null
    }
} 