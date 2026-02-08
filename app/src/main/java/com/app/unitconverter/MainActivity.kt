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

/**
 * Main entry Activity. Hosts Compose content.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent replaces the normal XML layout system with Compose UI.
        setContent {
            // App theme wrapper (colors, typography, shapes).
            UnitConverterTheme {
                // Surface is a Material container that applies background color, etc.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Render the main screen composable
                    UnitConverter()
                }
            }
        }
    }
}

/**
 * Main UI for the Unit Converter screen.
 *
 * This implementation converts length units:
 * cm, m, ft, mm
 *
 * Internally, conversion factors are relative to meters (m).
 * Example: 1 ft = 0.3048 m, so factor for "ft" is 0.3048.
 */
@Composable
fun UnitConverter() {

    // Text entered by the user
    var inputValue by remember { mutableStateOf("") }

    // Output text shown in Result field
    var outputValue by remember { mutableStateOf("") }

    // Labels shown on the dropdown buttons
    var inputUnit by remember { mutableStateOf("m") }
    var outputUnit by remember { mutableStateOf("m") }

    // Whether each dropdown is currently open
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }

    // Conversion factors relative to meters.
    // Using mutableStateOf allows recomposition when they change.
    val iConversionFactor = remember { mutableStateOf(1.00) }
    val oConversionFactor = remember { mutableStateOf(1.00) }

    /**
     * Converts the inputValue from input unit -> output unit.
     *
     * Formula (based on meters as base unit):
     * inputInMeters = input * inputFactor
     * output = inputInMeters / outputFactor
     */
    fun convertUnits() {
        // Safely parse input (if empty or invalid -> 0.0)
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0

        // Convert via meters as base unit
        val raw = inputValueDouble * iConversionFactor.value / oConversionFactor.value

        // Round to 2 decimals (simple rounding)
        val result = (raw * 100).roundToInt() / 100.0

        // Update output state (UI updates automatically)
        outputValue = result.toString()
    }

    // Main layout: center content on screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Row of two text fields: input and result
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User input field
            OutlinedTextField(
                value = inputValue,
                onValueChange = { newText ->
                    // Update input and immediately convert
                    inputValue = newText
                    convertUnits()
                },
                label = { Text("Enter value") },
                modifier = Modifier.weight(1f)
            )

            // Output/result field (read-only)
            OutlinedTextField(
                value = outputValue,
                onValueChange = { /* readOnly, do nothing */ },
                label = { Text("Result") },
                modifier = Modifier.weight(1f),
                readOnly = true
            )
        }

        // Row with two dropdown unit selectors
        Row {
            // INPUT unit selector box
            Box(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down"
                    )

                    // Input dropdown menu
                    DropdownMenu(
                        expanded = iExpanded,
                        onDismissRequest = { iExpanded = false }
                    ) {
                        // Each item sets the unit label + conversion factor then converts

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

            // OUTPUT unit selector box
            Box(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down"
                    )

                    // Output dropdown menu
                    DropdownMenu(
                        expanded = oExpanded,
                        onDismissRequest = { oExpanded = false }
                    ) {
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

/**
 * Preview in Android Studio (Design/Preview panel).
 */
@Preview
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}