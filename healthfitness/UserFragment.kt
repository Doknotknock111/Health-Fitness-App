package com.example.healthfitness

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader

class UserFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var editTextBMI: EditText
    private lateinit var editTextCurrentWeight: EditText
    private lateinit var editTextTargetWeight: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var buttonSave: Button
    private lateinit var buttonRetrieve: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonDelete: Button
    private lateinit var textViewUserData: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        // Initialize views
        editTextName = view.findViewById(R.id.editTextName)
        editTextAge = view.findViewById(R.id.editTextAge)
        editTextHeight = view.findViewById(R.id.editTextHeight)
        editTextBMI = view.findViewById(R.id.editTextBMI)
        editTextCurrentWeight = view.findViewById(R.id.editTextCurrentWeight)
        editTextTargetWeight = view.findViewById(R.id.editTextTargetWeight)
        datePicker = view.findViewById(R.id.datePicker)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonRetrieve = view.findViewById(R.id.buttonRetrieve)
        buttonClear = view.findViewById(R.id.buttonClear)
        buttonDelete = view.findViewById(R.id.buttonDelete)
        textViewUserData = view.findViewById(R.id.textViewUserData)

        // Set click listeners
        buttonSave.setOnClickListener {
            saveUserData(requireActivity())
            //showToast("User data saved successfully")
        }

        buttonRetrieve.setOnClickListener {
            displayUserData()
            //showToast("User data retrieved successfully")
        }

        buttonClear.setOnClickListener {
            clearUserData()
            //showToast("User data cleared successfully")
        }

        buttonDelete.setOnClickListener {
            if (deleteUserData(requireActivity())) {
                showToast("User data deleted successfully")
            } else {
                showToast("No user data found to delete")
            }
        }

        return view
    }

    private fun displayUserData() {
        val filename = "user_data.txt"

        try {
            val fileInputStream = requireActivity().openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()

            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }

            textViewUserData.text = stringBuilder.toString()

            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveUserData(activity: Context) {
        val userData = StringBuilder()
        userData.append("Name: ").append(editTextName.text.toString()).append("\n")
        userData.append("Age: ").append(editTextAge.text.toString()).append("\n")
        userData.append("Height: ").append(editTextHeight.text.toString()).append(" cm\n")
        userData.append("BMI: ").append(editTextBMI.text.toString()).append("\n")
        userData.append("Current Weight: ").append(editTextCurrentWeight.text.toString()).append("\n")
        userData.append("Target Weight: ").append(editTextTargetWeight.text.toString()).append("\n")
        userData.append("Start Date: ").append(datePicker.month + 1).append("/").append(datePicker.dayOfMonth).append("/").append(datePicker.year).append("\n")

        val filename = "user_data.txt"
        val fileContents = userData.toString()

        try {
            activity.openFileOutput(filename, Context.MODE_PRIVATE).use { out ->
                out.write(fileContents.toByteArray())
            }
            // Optionally, display a toast or message indicating successful data saving
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle any errors that may occur during file writing
        }
    }

    private fun clearUserData() {
        editTextName.text.clear()
        editTextAge.text.clear()
        editTextHeight.text.clear()
        editTextBMI.text.clear()
        editTextCurrentWeight.text.clear()
        editTextTargetWeight.text.clear()
        textViewUserData.text = "" // Clear the displayed user data
    }


    private fun deleteUserData(activity: Context): Boolean {
        val filename = "user_data.txt"

        try {
            val file = activity.getFileStreamPath(filename)
            if (file.exists()) {
                file.delete()
                textViewUserData.text = "" // Clear the displayed user data
                return true // Indicate successful deletion
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false // Indicate failure to delete
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
