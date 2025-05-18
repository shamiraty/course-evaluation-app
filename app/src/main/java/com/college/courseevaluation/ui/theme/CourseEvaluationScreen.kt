package com.college.courseevaluation.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.college.courseevaluation.ui.theme.data.Course
import com.college.courseevaluation.ui.theme.data.CourseEvaluationRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CourseEvaluationScreen(apiService: ApiService = ApiClient.apiService) {
    // State variables to hold form data
    var studentId by rememberSaveable { mutableStateOf("") }
    var tokenNumber by rememberSaveable { mutableStateOf("") }
    var selectedCourse by rememberSaveable { mutableStateOf<Course?>(null) }
    var teachingModality by rememberSaveable { mutableStateOf("") }
    var learningMaterials by rememberSaveable { mutableStateOf("") }
    var lectureTimeStart by rememberSaveable { mutableStateOf("") }
    var lectureTimeEnd by rememberSaveable { mutableStateOf("") }
    var lecturerPunctuality by rememberSaveable { mutableStateOf("") }
    var contentUnderstanding by rememberSaveable { mutableStateOf("") }
    var studentEngagement by rememberSaveable { mutableStateOf("") }
    var useOfTechnology by rememberSaveable { mutableStateOf("") }
    var assessmentFeedback by rememberSaveable { mutableStateOf("") }
    var courseRelevance by rememberSaveable { mutableStateOf("") }
    var overallSatisfaction by rememberSaveable { mutableStateOf("") }
    var suggestions by rememberSaveable { mutableStateOf("") }

    // State for managing dialog visibility and messages
    var showMessage by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf("") }
    val onDismiss = { showMessage = false }

    //Coroutines
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Define a Saver for Size
    val sizeSaver = Saver<Size, Pair<Float, Float>>(
        save = { size -> Pair(size.width, size.height) },
        restore = { pair -> Size(pair.first, pair.second) }
    )

    //Dropdown states
    var isCourseSelectionExpanded by rememberSaveable { mutableStateOf(false) }
    var isTeachingModalityExpanded by rememberSaveable { mutableStateOf(false) }
    var isLearningMaterialsExpanded by rememberSaveable { mutableStateOf(false) }
    var isLectureTimeStartExpanded by rememberSaveable { mutableStateOf(false) }
    var isLectureTimeEndExpanded by rememberSaveable { mutableStateOf(false) }
    var isLecturerPunctualityExpanded by rememberSaveable { mutableStateOf(false) }
    var isContentUnderstandingExpanded by rememberSaveable { mutableStateOf(false) }
    var isStudentEngagementExpanded by rememberSaveable { mutableStateOf(false) }
    var isUseOfTechnologyExpanded by rememberSaveable { mutableStateOf(false) }
    var isAssessmentFeedbackExpanded by rememberSaveable { mutableStateOf(false) }
    var isCourseRelevanceExpanded by rememberSaveable { mutableStateOf(false) }
    var isOverallSatisfactionExpanded by rememberSaveable { mutableStateOf(false) }

    //Size for the dropdown
    var courseSelectionSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var teachingModalitySize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var learningMaterialsSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var lectureTimeStartSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var lectureTimeEndSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var lecturerPunctualitySize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var contentUnderstandingSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var studentEngagementSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var useOfTechnologySize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var assessmentFeedbackSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var courseRelevanceSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }
    var overallSatisfactionSize by rememberSaveable(stateSaver = sizeSaver) { mutableStateOf(Size.Zero) }

    // State to manage current section
    var currentSection by rememberSaveable { mutableStateOf(1) }
    val totalSections = 5 // Update this as you add more sections

    // State for loading indicator
    var isSubmitting by rememberSaveable { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()

    val courses = remember { mutableStateListOf<Course>() }
    var isCoursesLoading by remember { mutableStateOf(true) }
    var coursesLoadError by remember { mutableStateOf<String?>(null) }

    // Fetch courses when the screen is created
    LaunchedEffect(key1 = true) {
        isCoursesLoading = true
        val call = apiService.getCourses()
        call.enqueue(object : Callback<List<Course>> {
            override fun onResponse(
                call: Call<List<Course>>,
                response: Response<List<Course>>
            ) {
                isCoursesLoading = false
                if (response.isSuccessful) {
                    response.body()?.let { fetchedCourses ->
                        courses.addAll(fetchedCourses)
                    }
                } else {
                    coursesLoadError = "Failed to load courses: ${response.code()} - ${response.message()}"
                    Log.e("Course Fetch Error", coursesLoadError!!)
                    message = coursesLoadError!!
                    showMessage = true
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                isCoursesLoading = false
                coursesLoadError = "Network error while loading courses: ${t.message}"
                Log.e("Course Fetch Error", coursesLoadError!!)
                message = coursesLoadError!!
                showMessage = true
            }
        })
    }

    // Function to check if all fields in the current section are filled
    fun isCurrentSectionFilled(): Boolean {
        return when (currentSection) {
            1 -> studentId.isNotBlank() && tokenNumber.isNotBlank() && selectedCourse != null
            2 -> teachingModality.isNotBlank() && learningMaterials.isNotBlank() && lectureTimeStart.isNotBlank()
            3 -> lectureTimeEnd.isNotBlank() && lecturerPunctuality.isNotBlank() && contentUnderstanding.isNotBlank()
            4 -> studentEngagement.isNotBlank() && useOfTechnology.isNotBlank() && assessmentFeedback.isNotBlank()
            5 -> courseRelevance.isNotBlank() && overallSatisfaction.isNotBlank()
            else -> true
        }
    }

    Scaffold(
        //removed topbar
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) // Add vertical spacing between items
            )
            {
                item {
                    Text(
                        text = "SEMISTER 1: | COURSE EVALUATION",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp) // Add some space below the title
                    )
                }

                item {
                    // Section Indicators
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..totalSections) {
                            val isFilled = when (i) {
                                1 -> studentId.isNotBlank() && tokenNumber.isNotBlank() && selectedCourse != null
                                2 -> teachingModality.isNotBlank() && learningMaterials.isNotBlank() && lectureTimeStart.isNotBlank()
                                3 -> lectureTimeEnd.isNotBlank() && lecturerPunctuality.isNotBlank() && contentUnderstanding.isNotBlank()
                                4 -> studentEngagement.isNotBlank() && useOfTechnology.isNotBlank() && assessmentFeedback.isNotBlank()
                                5 -> courseRelevance.isNotBlank() && overallSatisfaction.isNotBlank()
                                else -> false
                            }
                            Icon(
                                imageVector = if (isFilled) Icons.Filled.CheckCircle else Icons.Filled.Cancel,

                                contentDescription = "Section $i",
                                tint = if (i == currentSection) MaterialTheme.colorScheme.primary else if (isFilled) Color.Green else Color.Red
                            )
                        }
                    }
                }

                item {
                    // Form Sections
                    if (isCoursesLoading) {
                        Dialog(onDismissRequest = { /* prevent dismissal */ }) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator()
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text("Loading Courses...", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    } else if (coursesLoadError != null) {
                        val activity = context as? Activity
                        Dialog(onDismissRequest = { /* Optional: Keep empty to force user to close */ }) {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(12.dp)) // Light Red
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Unable to connect to the server",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = "• Please check your internet connection.\n" +
                                                "• The server may be temporarily unavailable.\n" +
                                                "• Try again later.",
                                        color = Color.DarkGray,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Button(
                                        onClick = { activity?.finish() }, // Close the app
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                    ) {
                                        Text("Close App", color = Color.White)
                                    }
                                }
                            }
                        }
                    } else {
                        val dropDownInput = CommonComposables()
                        when (currentSection) {
                            1 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Section 1: Basic Information
                                    Text(
                                        "Section 1: Basic Student Information",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    val studentInput = CommonComposables()
                                    studentInput.StudentIdInput(
                                        studentId = studentId,
                                        onValueChange = { studentId = it },
                                        isFilled = studentId.isNotBlank()
                                    )
                                    val token = CommonComposables()
                                    token.TokenNumberInput(
                                        tokenNumber = tokenNumber,
                                        onValueChange = { tokenNumber = it },
                                        isFilled = tokenNumber.isNotBlank()
                                    )
                                    val coursesSelect = CommonComposables()
                                    coursesSelect.CourseSelectionDropdown(
                                        label = "Course",
                                        selectedCourseName = selectedCourse?.name ?: "Evaluated Course",
                                        courses = courses, // Use the fetched courses list
                                        onCourseSelected = { selectedCourse = it },
                                        expanded = isCourseSelectionExpanded,
                                        onExpandedChange = { isCourseSelectionExpanded = it },
                                        size = courseSelectionSize,
                                        onSizeChange = { courseSelectionSize = it },
                                        isFilled = selectedCourse != null,
                                    )
                                }
                            }

                            2 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Section 2: Course Details
                                    Text(
                                        "Section 2:Evaluate Teaching Modality",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Teaching Modality",
                                        value = teachingModality,
                                        options = listOf("Good", "Better", "Best"),
                                        onValueChange = { teachingModality = it },
                                        expanded = isTeachingModalityExpanded,
                                        onExpandedChange = { isTeachingModalityExpanded = it },
                                        size = teachingModalitySize,
                                        onSizeChange = { teachingModalitySize = it },
                                        isFilled = teachingModality.isNotBlank(),

                                        )

                                    dropDownInput.DropdownInput(
                                        label = "Learning Materials",
                                        value = learningMaterials,
                                        options = listOf(
                                            "Available",
                                            "Not Available",
                                            "Complex to Understand"
                                        ),
                                        onValueChange = { learningMaterials = it },
                                        expanded = isLearningMaterialsExpanded,
                                        onExpandedChange = { isLearningMaterialsExpanded = it },
                                        size = learningMaterialsSize,
                                        onSizeChange = { learningMaterialsSize = it },
                                        isFilled = learningMaterials.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Lecture Time Start",
                                        value = lectureTimeStart,
                                        options = listOf("Early Start", "Coming Late"),
                                        onValueChange = { lectureTimeStart = it },
                                        expanded = isLectureTimeStartExpanded,
                                        onExpandedChange = { isLectureTimeStartExpanded = it },
                                        size = lectureTimeStartSize,
                                        onSizeChange = { lectureTimeStartSize = it },
                                        isFilled = lectureTimeStart.isNotBlank()
                                    )
                                }
                            }

                            3 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Section 3: Lecture Feedback
                                    Text(
                                        "Section 3:Evaluate Lecture Feedback",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Lecture Time End",
                                        value = lectureTimeEnd,
                                        options = listOf("Early End", "Ending Late"),
                                        onValueChange = { lectureTimeEnd = it },
                                        expanded = isLectureTimeEndExpanded,
                                        onExpandedChange = { isLectureTimeEndExpanded = it },
                                        size = lectureTimeEndSize,
                                        onSizeChange = { lectureTimeEndSize = it },
                                        isFilled = lectureTimeEnd.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Lecturer Punctuality",
                                        value = lecturerPunctuality,
                                        options = listOf(
                                            "Always On Time",
                                            "Sometimes Late",
                                            "Always Late"
                                        ),
                                        onValueChange = { lecturerPunctuality = it },
                                        expanded = isLecturerPunctualityExpanded,
                                        onExpandedChange = { isLecturerPunctualityExpanded = it },
                                        size = lecturerPunctualitySize,
                                        onSizeChange = { lecturerPunctualitySize = it },
                                        isFilled = lecturerPunctuality.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Content Understanding",
                                        value = contentUnderstanding,
                                        options = listOf("Very Clear", "Average", "Confusing"),
                                        onValueChange = { contentUnderstanding = it },
                                        expanded = isContentUnderstandingExpanded,
                                        onExpandedChange = { isContentUnderstandingExpanded = it },
                                        size = contentUnderstandingSize,
                                        onSizeChange = { contentUnderstandingSize = it },
                                        isFilled = contentUnderstanding.isNotBlank()
                                    )
                                }
                            }

                            4 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Section 4: Student Engagement and Tech
                                    Text(
                                        "Section 4: Student Engagement and Technology",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Student Engagement",
                                        value = studentEngagement,
                                        options = listOf(
                                            "Highly Interactive",
                                            "Moderate",
                                            "Not Interactive"
                                        ),
                                        onValueChange = { studentEngagement = it },
                                        expanded = isStudentEngagementExpanded,
                                        onExpandedChange = { isStudentEngagementExpanded = it },
                                        size = studentEngagementSize,
                                        onSizeChange = { studentEngagementSize = it },
                                        isFilled = studentEngagement.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Use of Technology",
                                        value = useOfTechnology,
                                        options = listOf("Effective", "Moderate", "Not Used"),
                                        onValueChange = { useOfTechnology = it },
                                        expanded = isUseOfTechnologyExpanded,
                                        onExpandedChange = { isUseOfTechnologyExpanded = it },
                                        size = useOfTechnologySize,
                                        onSizeChange = { useOfTechnologySize = it },
                                        isFilled = useOfTechnology.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Assessment Feedback",
                                        value = assessmentFeedback,
                                        options = listOf(
                                            "Timely & Helpful",
                                            "Late Feedback",
                                            "No Feedback"
                                        ),
                                        onValueChange = { assessmentFeedback = it },
                                        expanded = isAssessmentFeedbackExpanded,
                                        onExpandedChange = { isAssessmentFeedbackExpanded = it },
                                        size = assessmentFeedbackSize,
                                        onSizeChange = { assessmentFeedbackSize = it },
                                        isFilled = assessmentFeedback.isNotBlank()
                                    )
                                }
                            }

                            5 -> {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Section 5: Overall Feedback and Submission
                                    Text(
                                        "Section 5: Overall Feedback Course Feedback",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Course Relevance",
                                        value = courseRelevance,
                                        options = listOf(
                                            "Very Relevant",
                                            "Somewhat Relevant",
                                            "Not Relevant"
                                        ),
                                        onValueChange = { courseRelevance = it },
                                        expanded = isCourseRelevanceExpanded,
                                        onExpandedChange = { isCourseRelevanceExpanded = it },
                                        size = courseRelevanceSize,
                                        onSizeChange = { courseRelevanceSize = it },
                                        isFilled = courseRelevance.isNotBlank()
                                    )
                                    dropDownInput.DropdownInput(
                                        label = "Overall Satisfaction",
                                        value = overallSatisfaction,
                                        options = listOf(
                                            "Very Satisfied",
                                            "Satisfied",
                                            "Not Satisfied"
                                        ),
                                        onValueChange = { overallSatisfaction = it },
                                        expanded = isOverallSatisfactionExpanded,
                                        onExpandedChange = { isOverallSatisfactionExpanded = it },
                                        size = overallSatisfactionSize,
                                        onSizeChange = { overallSatisfactionSize = it },
                                        isFilled = overallSatisfaction.isNotBlank()
                                    )
                                    val suggestion = CommonComposables()
                                    suggestion.SuggestionsInput(
                                        suggestions = suggestions,
                                        onValueChange = { suggestions = it })
                                }
                            }
                        }

                        // Navigation Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    if (currentSection > 1) {
                                        currentSection--
                                    }
                                },
                                enabled = currentSection > 1
                            ) {
                                Text("Previous")
                            }

                            if (currentSection < totalSections) {
                                Button(
                                    onClick = {
                                        if (isCurrentSectionFilled()) {
                                            currentSection++
                                        }
                                    },
                                    enabled = isCurrentSectionFilled()
                                ) {
                                    Text("Next")
                                }
                            } else {
                                // Submit Button
                                val submit = CommonComposables()
                                submit.SubmitButton(
                                    onClick = {
                                        isSubmitting = true
                                        coroutineScope.launch {
                                            val request = CourseEvaluationRequest(
                                                student_id = studentId.toIntOrNull() ?: 0,
                                                token_number = tokenNumber,
                                                course_id = selectedCourse?.id ?: 0,
                                                teaching_modality = teachingModality,
                                                learning_materials = learningMaterials,
                                                lecture_time_start = lectureTimeStart,
                                                lecture_time_end = lectureTimeEnd,
                                                lecturer_punctuality = lecturerPunctuality,
                                                content_understanding = contentUnderstanding,
                                                student_engagement = studentEngagement,
                                                use_of_technology = useOfTechnology,
                                                assessment_feedback = assessmentFeedback,
                                                course_relevance = courseRelevance,
                                                overall_satisfaction = overallSatisfaction,
                                                suggestions = suggestions
                                            )

                                            val call = apiService.createCourseEvaluation(request)
                                            call.enqueue(object :
                                                Callback<Map<String, String>> {
                                                override fun onResponse(
                                                    call: Call<Map<String, String>>,
                                                    response: Response<Map<String, String>>
                                                ) {
                                                    isSubmitting = false
                                                    if (response.isSuccessful) {
                                                        val responseBody = response.body()
                                                        if (responseBody != null) {
                                                            message =
                                                                responseBody["message"] ?: "Success"
                                                        } else {
                                                            message =
                                                                "Successful response but body is null"
                                                        }
                                                        showMessage = true
                                                        // Clear the form
                                                        studentId = ""
                                                        tokenNumber = ""
                                                        selectedCourse = null
                                                        teachingModality = ""
                                                        learningMaterials = ""
                                                        lectureTimeStart = ""
                                                        lectureTimeEnd = ""
                                                        lecturerPunctuality = ""
                                                        contentUnderstanding = ""
                                                        studentEngagement = ""
                                                        useOfTechnology = ""
                                                        assessmentFeedback = ""
                                                        courseRelevance = ""
                                                        overallSatisfaction = ""
                                                        suggestions = ""
                                                        currentSection = 1 // Reset to the first section

                                                    } else {
                                                        val errorBody =
                                                            response.errorBody()?.string()
                                                        if (errorBody != null) {
                                                            val gson = Gson()
                                                            try {
                                                                val errorResponse =
                                                                    gson.fromJson(
                                                                        errorBody,
                                                                        Map::class.java
                                                                    ) as Map<*, *>
                                                                message =
                                                                    errorResponse["error"]?.toString()
                                                                        ?: "Unknown error"
                                                            } catch (e: Exception) {
                                                                message =
                                                                    "Error: ${response.code()} ${response.message()}"
                                                            }
                                                        } else {
                                                            message =
                                                                "Error: ${response.code()} ${response.message()}"
                                                        }
                                                        showMessage = true
                                                        Log.e("API Error", message)
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<Map<String, String>>,
                                                    t: Throwable
                                                ) {
                                                    isSubmitting = false
                                                    message = "Network error: ${t.message}"
                                                    showMessage = true
                                                    Log.e(
                                                        "Network Error",
                                                        t.message ?: "Unknown network error"
                                                    )
                                                }
                                            })
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = isCurrentSectionFilled()
                                )
                            }

                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp)) // Add space before the card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        //elevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Testing Student Details",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Divider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Registration Number",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                    Text(
                                        "REG-8132712",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Divider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Token Number",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                    Text(
                                        "6Bo2IyumoXeq",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Divider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Evaluated Course",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                    Text(
                                        "Supply Chain Management",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Divider()
                            val context = LocalContext.current
                            Text(
                                text = "or visit to github to see testing students sheet (CLICK HERE)",
                                style = MaterialTheme.typography.bodyMedium,
                                // color = MaterialTheme.colors.primary,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        val uri = Uri.parse("https://www.youtube.com/")
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        context.startActivity(intent)
                                    },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Loading Indicator for submission (outside LazyColumn)
            if (isSubmitting) {
                Dialog(onDismissRequest = { /* Prevent dismiss */ }) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Message Dialog (outside LazyColumn)
            val commonComposables = CommonComposables()
            commonComposables.MessageDialog(
                showMessage = showMessage,
                message = message,
                onDismiss = onDismiss
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    // ApiappTheme {
    //    CourseEvaluationScreen()
    // }
}