// Calisthenics.kt
package com.example.healthfitness

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Calisthenics : AppCompatActivity() {

    private lateinit var muscleGroupSpinner: Spinner
    private lateinit var workoutTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calisthenics)

        muscleGroupSpinner = findViewById(R.id.muscle_group_spinner)
        workoutTextView = findViewById(R.id.workout_textview)

        val muscleGroups = arrayOf("Chest", "Shoulder", "Bicep", "Tricep", "Back", "Abs", "Quads", "Calves")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, muscleGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        muscleGroupSpinner.adapter = adapter

        muscleGroupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedMuscleGroup = parent.getItemAtPosition(position).toString()
                val workouts = getWorkouts(selectedMuscleGroup)
                val workoutText = workouts.joinToString(separator = "\n")
                workoutTextView.text = workoutText
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun getWorkouts(muscleGroup: String): Array<String> {
        return when (muscleGroup) {
            "Chest" -> arrayOf("Push-ups", "Dips", "Wide Arm Push-ups", "Diamond Push-ups", "Pike Push-ups")
            "Shoulder" -> arrayOf("Handstand Push-ups", "Pike Push-ups", "Dolphin Push-ups", "Shoulder Taps", "Wall Walks")
            "Bicep" -> arrayOf("Chin-ups", "Pull-ups", "Inverted Rows", "Close Grip Chin-ups", "Commando Pull-ups")
            "Tricep" -> arrayOf("Diamond Push-ups", "Tricep Dips", "Tricep Bench Dips", "Close Grip Push-ups", "Pike Push-ups")
            "Back" -> arrayOf("Pull-ups", "Chin-ups", "Inverted Rows", "Wide Grip Pull-ups", "Commando Pull-ups")
            "Abs" -> arrayOf("Planks", "Russian Twists", "Hanging Leg Raises", "Bicycle Crunches", "Mountain Climbers")
            "Quads" -> arrayOf("Squats", "Lunges", "Jump Squats", "Pistol Squats", "Step-ups")
            "Calves" -> arrayOf("Calf Raises", "Single Leg Calf Raises", "Jump Rope", "Box Jumps", "Calf Stretch")

            else -> arrayOf()
        }
    }
}
