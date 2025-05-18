package com.college.courseevaluation.ui.theme
import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.college.courseevaluation.ui.theme.data.Course

class CommonComposables {
    @Composable
    fun MessageDialog(
        showMessage: Boolean,
        message: String,
        onDismiss: () -> Unit
    ) {
        if (showMessage) {
            Dialog(onDismissRequest = { onDismiss() }) {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Button(onClick = { onDismiss() }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun StudentIdInput(
        studentId: String,
        onValueChange: (String) -> Unit,
        isFilled: Boolean,
        modifier: Modifier = Modifier
    ) {
        OutlinedTextField(
            value = studentId,
            onValueChange = onValueChange,
            label = { Text("Registration Number") },
            leadingIcon = {
                Icon(
                    imageVector = if (isFilled) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                    contentDescription = if (isFilled) "Filled" else "Not Filled",
                    tint = if (isFilled) Color.Green else Color.Red
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = modifier.fillMaxWidth()
        )
    }


    @Composable
    fun TokenNumberInput(
        tokenNumber: String,
        onValueChange: (String) -> Unit,
        isFilled: Boolean,
        modifier: Modifier = Modifier
    ) {
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            value = tokenNumber,
            onValueChange = onValueChange,
            label = { Text("Token Number") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock, // Hii ndio ikoni ya funguo
                    contentDescription = "Funguo"
                )
            },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Ficha nenosiri" else "Onyesha nenosiri"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = if (isFilled) Color.Green else Color.Red
                    )

                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
        )
    }


    @SuppressLint("RememberReturnType")
    @Composable
    fun CourseSelectionDropdownCard(
        label: String,
        selectedCourseName: String,
        courses: List<Course>,
        onCourseSelected: (Course?) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        size: Size,
        onSizeChange: (Size) -> Unit,
        isFilled: Boolean,
        modifier: Modifier = Modifier,
        itemsPerPage: Int = 7 // Number of items per page, make this customizable
    ) {
        Card(modifier = modifier.fillMaxWidth()) {
            CourseSelectionDropdown(
                label = label,
                selectedCourseName = selectedCourseName,
                courses = courses,
                onCourseSelected = onCourseSelected,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                size = size,
                onSizeChange = onSizeChange,
                isFilled = isFilled,
                itemsPerPage = itemsPerPage
            )
        }
    }

    @SuppressLint("RememberReturnType")
    @Composable
    fun CourseSelectionDropdown(
        label: String,
        selectedCourseName: String,
        courses: List<Course>,
        onCourseSelected: (Course?) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        size: Size,
        onSizeChange: (Size) -> Unit,
        isFilled: Boolean,
        modifier: Modifier = Modifier,
        itemsPerPage: Int = 7 // Number of items per page, make this customizable
    ) {
        var searchText by remember { mutableStateOf("") }
        var currentPage by remember { mutableStateOf(1) } // Current page
        val filteredCourses = remember(courses, searchText) {
            courses.filter { it.name.contains(searchText, ignoreCase = true) }
        }
        val totalPages = remember(filteredCourses, itemsPerPage) {
            (filteredCourses.size + itemsPerPage - 1) / itemsPerPage // Calculate total pages
        }
        val pagedCourses = remember(filteredCourses, currentPage, itemsPerPage) {
            filteredCourses.drop((currentPage - 1) * itemsPerPage).take(itemsPerPage)
        }

        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()
        val selectedCourse = courses.find { it.name == selectedCourseName }

        LaunchedEffect(isFocused, expanded) {
            if (isFocused && !expanded) {
                onExpandedChange(true)
            }
        }

        Column(modifier = modifier) {
            OutlinedTextField(
                value = selectedCourseName,
                onValueChange = { },
                readOnly = true,
                label = { Text(label) },
                leadingIcon = {
                    Icon(
                        imageVector = if (isFilled) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        contentDescription = if (isFilled) "Filled" else "Not Filled",
                        tint = if (isFilled) Color.Green else Color.Red
                    )
                },
                trailingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (selectedCourse != null) {
                            IconButton(onClick = { onCourseSelected(null) }) { // Clear button
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                        IconButton(onClick = { onExpandedChange(!expanded) }) { // Dropdown toggle
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = if (expanded) "Close Dropdown" else "Open Dropdown"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        onSizeChange(coordinates.size.toSize())
                    },
                interactionSource = interactionSource
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.width(with(LocalDensity.current) { size.width.toDp() })
            ) {
                // Sehemu ya kutafuta
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        currentPage = 1 // Reset to first page when search text changes
                    },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (filteredCourses.isEmpty()) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "No courses found".uppercase(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        enabled = false,
                        onClick = {}
                    )
                } else {
                    pagedCourses.forEachIndexed { index, course -> // Use pagedCourses
                        DropdownMenuItem(
                            text = {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = course.name.uppercase(),
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.weight(1f, fill = false)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "[ ${course.code?.uppercase() ?: "N/A"} ]",
                                            color = Color.Red,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Text(
                                        text = "Program: ${course.program_name}",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            },
                            onClick = {
                                onCourseSelected(course)
                                onExpandedChange(false)
                                searchText = ""
                            }
                        )
                        if (index < pagedCourses.size - 1) {
                            Divider()
                        }
                    }
                    // Pagination controls
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Page: $currentPage of $totalPages",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row {

                            IconButton(
                                onClick = {
                                    if (currentPage > 1) currentPage--
                                },
                                enabled = currentPage > 1
                            ) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Page")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    if (currentPage < totalPages) currentPage++
                                },
                                enabled = currentPage < totalPages
                            ) {
                                Icon(Icons.Filled.ArrowForward, contentDescription = "Next Page")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DropdownInput(
        label: String,
        value: String,
        options: List<String>,
        onValueChange: (String) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        size: Size,
        onSizeChange: (Size) -> Unit,
        isFilled: Boolean,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            OutlinedTextField(
                value = value,
                onValueChange = { },
                readOnly = true,
                label = { Text(label) },
                leadingIcon = {
                    Icon(
                        imageVector = if (isFilled) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                        contentDescription = if (isFilled) "Filled" else "Not Filled",
                        tint = if (isFilled) Color.Green else Color.Red
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { onExpandedChange(!expanded) }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Open Dropdown")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        onSizeChange(coordinates.size.toSize())
                    }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.width(with(LocalDensity.current) { size.width.toDp() })
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun SuggestionsInput(
        suggestions: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        OutlinedTextField(
            value = suggestions,
            onValueChange = onValueChange,
            label = { Text("Suggestions") },
            modifier = modifier.fillMaxWidth()
        )
    }

    @Composable
    fun SubmitButton(
        onClick: () -> Unit,
        enabled: Boolean,
        modifier: Modifier = Modifier
    ) {
        Button(onClick = onClick, modifier = modifier, enabled = enabled) {
            Text("Submit")
        }
    }
}