package com.app.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.app.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("m") }
    var outputUnit by remember { mutableStateOf("m") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf((false) )}
    val iConversionFactor = remember { mutableStateOf(1.00)}
    val oConversionFactor = remember { mutableStateOf(1.00)}

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0

        val raw = inputValueDouble * iConversionFactor.value / oConversionFactor.value
        val result = (raw * 100).roundToInt() / 100.0   // round to 2 decimals

        outputValue = result.toString()
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = inputValue,
                onValueChange = {
                    inputValue = it
                    convertUnits()
                },
                label = { Text("Enter value") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = outputValue,
                onValueChange = {},
                label = { Text("Result") },
                modifier = Modifier.weight(1f),
                readOnly = true
            )
        }
        Row {
            // Here all the Ui elements will be stacked next to each other
            // Input Box
            Box (
                modifier = Modifier.padding(16.dp)
            ){
                // Input Button
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down"
                    )
                    DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("cm") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "cm"
                                iConversionFactor.value = 0.01
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("m") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "m"
                                iConversionFactor.value = 1.0
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("ft") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "ft"
                                iConversionFactor.value = 0.3048
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("mm") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "mm"
                                iConversionFactor.value = 0.001
                                convertUnits()
                            }
                        )
                    }
                }
            }
            // Output Box
            Box (
                modifier = Modifier.padding(16.dp)
            ){
                // Output Button
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down"
                    )
                    DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("cm") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "cm"
                                oConversionFactor.value = 0.01
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("m") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "m"
                                oConversionFactor.value = 1.00
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("ft") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "ft"
                                oConversionFactor.value = 0.3048
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("mm") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "mm"
                                oConversionFactor.value = 0.001
                                convertUnits()
                            }
                        )
                    }
                }
            }
        }
    }
}

    @Preview
    @Composable
    fun UnitConverterPreview() {
        UnitConverter()
    }
