package com.example.task

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar


class MainActivity : ComponentActivity() {
    // MainActivity.kt


   // class MainActivity : AppCompatActivity() {
        private lateinit var db: AppDatabase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "my-database"
            ).build()

            setContent {
              //  Surface(color = MaterialTheme.colors.background) {
                    MyFormScreen(db.myDataDao())
                }
            }
        }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyFormScreen(myDataDao: MyDataDao?) {
      val channelState = remember { mutableStateOf("") }
        val nameState = remember { mutableStateOf("") }
        val timeState = remember { mutableStateOf(Calendar.getInstance()) }
        val dataList = remember { mutableStateListOf<MyData>() }
        var selectedTime by remember { mutableStateOf(Calendar.getInstance()) }
        val context = LocalContext.current

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = channelState.value,
                onValueChange = { channelState.value = it },
                label = { Text("Channel") }
            )

            TextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text("Name") }
            )

            Button(
                onClick = {
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedTime.set(Calendar.MINUTE, minute)
                        },
                        selectedTime.get(Calendar.HOUR_OF_DAY),
                        selectedTime.get(Calendar.MINUTE),
                        false
                    )
                    timePickerDialog.show()
                }
            ) {
                Text("Select Time")
            }


            Button(
                onClick = {

                    val data = MyData(
                        channel = channelState.value,
                        name = nameState.value,
                        time = timeState.value.timeInMillis
                    )

                    GlobalScope.launch(Dispatchers.IO) {
                        if (myDataDao != null) {
                            myDataDao.insertData(data)
                        }
                        dataList.add(data)
                    }

                    // Clear the form fields
                    channelState.value = ""
                    nameState.value = ""
                    timeState.value = Calendar.getInstance()
                }
            ) {
                Text("Submit")
            }

            // Display the list of data
           // dataList.forEach { data ->
          //     Text("Name: ${data.name}")
           //     Text("Time: ${data.time}")
           //     Divider() // Add a visual separator between items
           // }
        }
    }



@Preview(showBackground = true)
    @Composable
    fun PreviewMyFormScreen() {
        MyFormScreen(myDataDao = null) // Pass null for preview purposes
    }
