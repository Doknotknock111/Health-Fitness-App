package com.example.healthfitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PedoActivity : AppCompatActivity(), SensorEventListener {
    private var sensor: Sensor? = null
    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedo)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onResume() {
        super.onResume()

        if (sensor == null) {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val stepsTaken = event?.values?.get(0)
        Log.d("StepCounter", "Step count: $stepsTaken")
        val steps = findViewById<TextView>(R.id.stepstaken)
        steps.text = stepsTaken.toString()
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }
}
