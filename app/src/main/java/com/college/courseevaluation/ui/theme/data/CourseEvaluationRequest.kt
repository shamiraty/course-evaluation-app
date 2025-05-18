package com.college.courseevaluation.ui.theme.data

// Data class to represent the CourseEvaluation request body
data class CourseEvaluationRequest(
    val student_id: Int,
    val token_number: String,
    val course_id: Int,
    val teaching_modality: String,
    val learning_materials: String,
    val lecture_time_start: String,
    val lecture_time_end: String,
    val lecturer_punctuality: String,
    val content_understanding: String,
    val student_engagement: String,
    val use_of_technology: String,
    val assessment_feedback: String,
    val course_relevance: String,
    val overall_satisfaction: String,
    val suggestions: String?
)
