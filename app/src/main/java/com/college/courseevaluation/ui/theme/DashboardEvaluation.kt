package com.college.courseevaluation.ui.theme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.college.courseevaluation.ui.theme.data.CourseChartData
import com.college.courseevaluation.ui.theme.data.CrosstabData
import com.college.courseevaluation.ui.theme.data.CrosstabItem
import com.college.courseevaluation.ui.theme.data.DashboardData
import com.college.courseevaluation.ui.theme.model.DashboardViewModel

@Composable
fun DashboardEvaluationScreen(dashboardViewModel: DashboardViewModel = viewModel()) {
    val dashboardData by dashboardViewModel.dashboardData
    val errorMessage by dashboardViewModel.errorMessage
    val isLoading by dashboardViewModel.isLoading

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }
    } else if (dashboardData != null) {
        DashboardContent(data = dashboardData!!)
    }
}

@Composable
fun DashboardContent(data: DashboardData) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text("Semister Evaluation Analytics", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            // Card with Badges
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Summary", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Evaluations:", style = MaterialTheme.typography.bodyMedium)
                        Badge { Text(data.totalEvaluations.toString(), style = MaterialTheme.typography.bodyMedium) }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Students:", style = MaterialTheme.typography.bodyMedium)
                        Badge { Text(data.totalStudents.toString(), style = MaterialTheme.typography.bodyMedium) }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Courses:", style = MaterialTheme.typography.bodyMedium)
                        Badge { Text(data.totalCourses.toString(), style = MaterialTheme.typography.bodyMedium) }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space before the charts
        }

        // Course Evaluation Chart (Vertical) with Legend and Grid
        if (data.courseChartData.isNotEmpty()) {
            item {
                VerticalBasicBarChartSection(title = "Course Evaluation Summary", courseData = data.courseChartData)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Aspect Bar Charts (Vertical) with Legends and Grids
        item {
            VerticalAspectBarChart(title = "Teaching Modality", data = data.modalityCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Learning Materials", data = data.materialCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Lecturer Punctuality", data = data.punctualityCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Content Understanding", data = data.understandingCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Student Engagement", data = data.engagementCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Overall Satisfaction", data = data.satisfactionCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Course Relevance", data = data.relevanceCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Use of Technology", data = data.technologyCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Lecture Time Start", data = data.lectureTimeStartCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Lecture Time End", data = data.lectureTimeEndCounts)
            Spacer(modifier = Modifier.height(8.dp))
            VerticalAspectBarChart(title = "Assessment Feedback", data = data.assessmentFeedbackCounts)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Crosstab Tables
        item {
            CrosstabTableSection(title = "Evaluation Breakdown", crosstabData = data.crosstabData)
        }
    }
}

@Composable
fun VerticalBasicBarChartSection(title: String, courseData: List<CourseChartData>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            if (courseData.isNotEmpty()) {
                val textMeasurer = rememberTextMeasurer()
                val labelTextStyle = TextStyle(fontSize = 12.sp)
                val yAxisLabelTextStyle = TextStyle(fontSize = 10.sp)
                val barColor = MaterialTheme.colorScheme.primary
                val yAxisTextColor = Color.Red
                val density = LocalDensity.current
                val maxValue = courseData.maxOf { it.count }.toFloat()
                val labels = courseData.map { it.courseName }
                val values = courseData.map { it.count }
                // Define a list of colors for the bars.  Make sure it is long enough.
                val barColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary,
                    Color.Green,
                    Color.Yellow,
                    Color.Cyan,
                    Color.Magenta,
                    Color.LightGray,
                    Color.DarkGray,
                    Color.Blue
                ).take(labels.size) // Ensure we don't go out of bounds.

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val barWidthPx = 40.dp.toPx()
                    val spacingPx = 16.dp.toPx()  // Increased spacing for better separation
                    val totalWidth = size.width
                    val totalHeight = size.height
                    val totalBars = labels.size
                    val availableWidth = totalWidth - (totalBars + 1) * spacingPx
                    val barStartX = (totalWidth - (totalBars * barWidthPx + (totalBars - 1) * spacingPx)) / 2

                    // Draw grid lines
                    val gridColor = Color.LightGray.copy(alpha = 0.7f) // Make grid lines semi-transparent
                    val gridIntervalY = (maxValue / 5).toFloat() // Adjust as needed for your data range
                    for (i in 0..5) { // Draw 6 horizontal grid lines
                        val y = totalHeight - (i * gridIntervalY / maxValue) * (totalHeight - 40.dp.toPx()) - 20.dp.toPx()
                        drawLine(
                            start = Offset(x = 0f, y = y),
                            end = Offset(x = totalWidth, y = y),
                            color = gridColor,
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    drawLine( // Draw X-axis
                        start = Offset(x = 0f, y = totalHeight - 20.dp.toPx()),
                        end = Offset(x = totalWidth, y = totalHeight - 20.dp.toPx()),
                        color = Color.Gray,
                        strokeWidth = 3.dp.toPx()
                    )
                    drawLine(  // Draw Y-axis
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = totalHeight - 20.dp.toPx()),
                        color = Color.Gray,
                        strokeWidth = 3.dp.toPx()
                    )

                    labels.forEachIndexed { index, label ->
                        val value = values[index].toFloat()
                        val barHeight = (value / maxValue) * (totalHeight - 40.dp.toPx())
                        val xPos = barStartX + index * (barWidthPx + spacingPx)
                        val yPos = totalHeight - barHeight - 20.dp.toPx()
                        val color = barColors[index] // Get the color for this bar

                        drawRect(
                            color = color, // Use the color
                            topLeft = Offset(x = xPos, y = yPos),
                            size = androidx.compose.ui.geometry.Size(width = barWidthPx, height = barHeight)
                        )
                    }

                    // Y-axis labels (simplified)
                    val steps = 3
                    for (i in 0..steps) {
                        val yValue = (maxValue * i / steps).toInt()
                        val yPos = totalHeight - (yValue / maxValue) * (totalHeight - 40.dp.toPx()) - 20.dp.toPx()
                        val textLayoutResult = textMeasurer.measure(text = yValue.toString(), style = TextStyle(fontSize = 10.sp, color = Color.Gray))
                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(x = 0f, y = yPos - textLayoutResult.size.height / 2)
                        )
                    }
                }

                // Legend for Course Names and Colors
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Courses:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    labels.forEachIndexed { index, label ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Canvas(modifier = Modifier.size(16.dp)) {
                                drawCircle(color = barColors[index]) // Use the color
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(label, style = labelTextStyle)
                        }
                    }
                }
            } else {
                Text("No course evaluation data available for the chart.")
            }
        }
    }
}

@Composable
fun VerticalAspectBarChart(title: String, data: Map<String, Int>) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            if (data.isNotEmpty()) {
                val textMeasurer = rememberTextMeasurer()
                val labelTextStyle = TextStyle(fontSize = 12.sp)
                val barColor = MaterialTheme.colorScheme.primary
                val textColor = Color.Gray
                val density = LocalDensity.current
                val maxValue = data.values.maxOrNull()?.toFloat() ?: 1f
                val labels = data.keys.toList()
                val values = data.values.toList()

                // Define a list of colors for the bars.  Make sure it is long enough.
                val barColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    Color.Green,
                    Color.Yellow,
                    Color.Cyan,
                    Color.Magenta,
                    Color.LightGray,
                    Color.DarkGray,
                    Color.Blue
                ).take(labels.size) // Ensure we don't go out of bounds.

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val barWidthPx = 40.dp.toPx()
                    val spacingPx = 16.dp.toPx() // Increased spacing
                    val totalWidth = size.width
                    val totalHeight = size.height
                    val totalBars = labels.size
                    val availableWidth = totalWidth - (totalBars + 1) * spacingPx
                    val barStartX = (totalWidth - (totalBars * barWidthPx + (totalBars - 1) * spacingPx)) / 2

                    // Draw grid lines
                    val gridColor = Color.LightGray.copy(alpha = 0.7f)
                    val gridIntervalY = (maxValue / 5).toFloat()
                    for (i in 0..5) {
                        val y = totalHeight - (i * gridIntervalY / maxValue) * (totalHeight - 40.dp.toPx()) - 20.dp.toPx()
                        drawLine(
                            start = Offset(x = 0f, y = y),
                            end = Offset(x = totalWidth, y = y),
                            color = gridColor,
                            strokeWidth =1.dp.toPx()
                        )
                    }
                    drawLine( // Draw X-axis
                        start = Offset(x = 0f, y = totalHeight - 20.dp.toPx()),
                        end = Offset(x = totalWidth, y = totalHeight - 20.dp.toPx()),
                        color = Color.LightGray,
                        strokeWidth = 1.dp.toPx()
                    )
                    drawLine(  // Draw Y-axis
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = totalHeight - 20.dp.toPx()),
                        color = Color.LightGray,
                        strokeWidth = 1.dp.toPx()
                    )

                    labels.forEachIndexed { index, label ->
                        val value = values[index].toFloat()
                        val barHeight = (value / maxValue) * (totalHeight - 40.dp.toPx())
                        val xPos = barStartX + index * (barWidthPx + spacingPx)
                        val yPos = totalHeight - barHeight - 20.dp.toPx()
                        val color = barColors[index]  // Get color for the bar

                        drawRect(
                            color = color, // Use the color
                            topLeft = Offset(x = xPos, y = yPos),
                            size = androidx.compose.ui.geometry.Size(width = barWidthPx, height = barHeight)
                        )
                    }

                    // Y-axis labels (simplified)
                    val steps = 3
                    for (i in 0..steps) {
                        val yValue = (maxValue * i / steps).toInt()
                        val yPos = totalHeight - (yValue / maxValue) * (totalHeight - 40.dp.toPx()) - 20.dp.toPx()
                        val textLayoutResult = textMeasurer.measure(text = yValue.toString(), style = TextStyle(fontSize = 10.sp, color = Color.Gray))
                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(x = 0f, y = yPos - textLayoutResult.size.height / 2)
                        )
                    }
                }

                // Legend for Aspect Labels and Colors
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("Key:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    labels.forEachIndexed { index, label ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Canvas(modifier = Modifier.size(16.dp)) {
                                drawCircle(color = barColors[index]) // Use the color.
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(label, style = labelTextStyle)
                        }
                    }
                }
            } else {
                Text("No data available for this chart.")
            }
        }
    }
}


@Composable
fun CrosstabTableSection(title: String, crosstabData: CrosstabData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        PaginatedCrosstabTableSection(title = "Teaching Modality Breakdown", crosstabItems = crosstabData.modality)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Learning Materials Breakdown", crosstabItems = crosstabData.material)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Lecturer Punctuality Breakdown", crosstabItems = crosstabData.punctuality)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Content Understanding Breakdown", crosstabItems = crosstabData.understanding)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Student Engagement Breakdown", crosstabItems = crosstabData.engagement)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Overall Satisfaction Breakdown", crosstabItems = crosstabData.satisfaction)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Course Relevance Breakdown", crosstabItems = crosstabData.relevance)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Use of Technology Breakdown", crosstabItems = crosstabData.technology)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Lecture Time Start Breakdown", crosstabItems = crosstabData.lectureTimeStart)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Lecture Time End Breakdown", crosstabItems = crosstabData.lectureTimeEnd)
        Spacer(modifier = Modifier.height(8.dp))
        PaginatedCrosstabTableSection(title = "Assessment Feedback Breakdown", crosstabItems = crosstabData.assessmentFeedback)
    }
}

@Composable
fun PaginatedCrosstabTableSection(title: String, crosstabItems: List<CrosstabItem>) {
    val itemsPerPage = 3
    var currentPage by remember { mutableStateOf(0) }
    val pageCount = (crosstabItems.size + itemsPerPage - 1) / itemsPerPage
    val currentItems = crosstabItems.drop(currentPage * itemsPerPage).take(itemsPerPage)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (currentItems.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    val firstItem = currentItems.firstOrNull()
                    val headers = listOfNotNull(
                        "Course",
                        "Program",
                        firstItem?.teachingModality?.let { "Modality" },
                        firstItem?.learningMaterials?.let { "Materials" },
                        firstItem?.lecturerPunctuality?.let { "Punctuality" },
                        firstItem?.contentUnderstanding?.let { "Understanding" },
                        firstItem?.studentEngagement?.let { "Engagement" },
                        firstItem?.overallSatisfaction?.let { "Satisfaction" },
                        firstItem?.courseRelevance?.let { "Relevance" },
                        firstItem?.useOfTechnology?.let { "Technology" },
                        firstItem?.lectureTimeStart?.let { "Start Time" },
                        firstItem?.lectureTimeEnd?.let { "End Time" },
                        firstItem?.assessmentFeedback?.let { "Feedback" },
                        "Frequency"
                    )

                    // Table Header with Primary Color Background
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(4.dp)
                    ) {
                        headers.forEach { header ->
                            Text(
                                text = header,
                                fontWeight = FontWeight.Bold,
                                color = Color.White, // Ensure text is readable on primary color
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Table Rows for the current page
                    currentItems.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = item.courseName, modifier = Modifier.weight(1f).padding(4.dp))
                            Text(text = item.programName, modifier = Modifier.weight(1f).padding(4.dp))
                            item.teachingModality?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.learningMaterials?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.lecturerPunctuality?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.contentUnderstanding?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.studentEngagement?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.overallSatisfaction?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.courseRelevance?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.useOfTechnology?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.lectureTimeStart?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.lectureTimeEnd?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            item.assessmentFeedback?.let { Text(text = it, modifier = Modifier.weight(1f).padding(4.dp)) }
                            Text(text = item.frequency.toString(), modifier = Modifier.weight(1f).padding(4.dp))
                        }
                    }

                    // Pagination Controls
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { if (currentPage > 0) currentPage-- },
                            enabled = currentPage > 0
                        ) {
                            Icon(Icons.Filled.ArrowLeft, contentDescription = "Previous Page")
                        }
                        Text("Page ${currentPage + 1} of $pageCount")
                        IconButton(
                            onClick = { if (currentPage < pageCount - 1) currentPage++ },
                            enabled = currentPage < pageCount - 1
                        ) {
                            Icon(Icons.Filled.ArrowRight, contentDescription = "Next Page")
                        }
                    }
                }
            } else {
                Text("No data available for this table.")
            }
        }
    }
}

