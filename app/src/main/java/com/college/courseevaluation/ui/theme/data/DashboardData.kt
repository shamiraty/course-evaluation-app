package com.college.courseevaluation.ui.theme.data

import com.google.gson.annotations.SerializedName

data class DashboardData(
    @SerializedName("total_evaluations") val totalEvaluations: Int,
    @SerializedName("total_students") val totalStudents: Int,
    @SerializedName("total_courses") val totalCourses: Int,
    @SerializedName("course_evaluation_counts") val courseEvaluationCounts: List<CourseEvaluationCount>,
    @SerializedName("modality_counts") val modalityCounts: Map<String, Int>,
    @SerializedName("material_counts") val materialCounts: Map<String, Int>,
    @SerializedName("punctuality_counts") val punctualityCounts: Map<String, Int>,
    @SerializedName("understanding_counts") val understandingCounts: Map<String, Int>,
    @SerializedName("engagement_counts") val engagementCounts: Map<String, Int>,
    @SerializedName("satisfaction_counts") val satisfactionCounts: Map<String, Int>,
    @SerializedName("relevance_counts") val relevanceCounts: Map<String, Int>,
    @SerializedName("technology_counts") val technologyCounts: Map<String, Int>,
    @SerializedName("lecture_time_start_counts") val lectureTimeStartCounts: Map<String, Int>,
    @SerializedName("lecture_time_end_counts") val lectureTimeEndCounts: Map<String, Int>,
    @SerializedName("assessment_feedback_counts") val assessmentFeedbackCounts: Map<String, Int>,
    @SerializedName("course_chart_data") val courseChartData: List<CourseChartData>,
    @SerializedName("crosstab_data") val crosstabData: CrosstabData
)

data class CourseEvaluationCount(
    @SerializedName("course_id") val courseId: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("course_name") val courseName: String,
    @SerializedName("department_name") val departmentName: String,
    @SerializedName("program_name") val programName: String
)

data class CourseChartData(
    @SerializedName("course_name") val courseName: String,
    @SerializedName("department_name") val departmentName: String,
    @SerializedName("program_name") val programName: String,
    @SerializedName("count") val count: Int
)

data class CrosstabData(
    val modality: List<CrosstabItem>,
    val material: List<CrosstabItem>,
    val punctuality: List<CrosstabItem>,
    val understanding: List<CrosstabItem>,
    val engagement: List<CrosstabItem>,
    val satisfaction: List<CrosstabItem>,
    val relevance: List<CrosstabItem>,
    val technology: List<CrosstabItem>,
    @SerializedName("lectureTimeStart") val lectureTimeStart: List<CrosstabItem>,
    @SerializedName("lectureTimeEnd") val lectureTimeEnd: List<CrosstabItem>,
    @SerializedName("assessmentFeedback") val assessmentFeedback: List<CrosstabItem>
)

data class CrosstabItem(
    @SerializedName("course_name") val courseName: String,
    @SerializedName("program_name") val programName: String,
    @SerializedName("teaching_modality") val teachingModality: String? = null,
    @SerializedName("learning_materials") val learningMaterials: String? = null,
    @SerializedName("lecturer_punctuality") val lecturerPunctuality: String? = null,
    @SerializedName("content_understanding") val contentUnderstanding: String? = null,
    @SerializedName("student_engagement") val studentEngagement: String? = null,
    @SerializedName("overall_satisfaction") val overallSatisfaction: String? = null,
    @SerializedName("course_relevance") val courseRelevance: String? = null,
    @SerializedName("use_of_technology") val useOfTechnology: String? = null,
    @SerializedName("lecture_time_start") val lectureTimeStart: String? = null,
    @SerializedName("lecture_time_end") val lectureTimeEnd: String? = null,
    @SerializedName("assessment_feedback") val assessmentFeedback: String? = null,
    @SerializedName("frequency") val frequency: Int
)
