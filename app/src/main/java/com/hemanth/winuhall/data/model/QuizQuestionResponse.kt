package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class QuizQuestionResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("quiz")
    var quiz: Quiz
) {
    data class Data(
        @SerializedName("body")
        var body: String,
        @SerializedName("chapter")
        var chapter: Chapter,
        @SerializedName("created_at")
        var createdAt: String,
        @SerializedName("difficulty")
        var difficulty: Int,
        @SerializedName("_id")
        var id: String,
        @SerializedName("isMultiple")
        var isMultiple: Boolean,
        @SerializedName("isPassage")
        var isPassage: Boolean,
        @SerializedName("negativeMarks")
        var negativeMarks: Int,
        @SerializedName("options")
        var options: List<Option>,
        @SerializedName("orgId")
        var orgId: String,
        @SerializedName("partialMarks")
        var partialMarks: Int,
        @SerializedName("positiveMarks")
        var positiveMarks: Int,
        @SerializedName("questionType")
        var questionType: String,
        @SerializedName("solution")
        var solution: String,
        @SerializedName("subject")
        var subject: Subject,
        @SerializedName("subtopics")
        var subtopics: List<Subtopic>
    ) {
        data class Chapter(
            @SerializedName("_id")
            var id: String,
            @SerializedName("name")
            var name: String
        )

        data class Option(
            @SerializedName("correct")
            var correct: Boolean,
            @SerializedName("_id")
            var id: String,
            @SerializedName("value")
            var value: String,
            var isSelected: Boolean=false
        )

        data class Subject(
            @SerializedName("_id")
            var id: String,
            @SerializedName("name")
            var name: String
        )

        data class Subtopic(
            @SerializedName("_id")
            var id: String,
            @SerializedName("name")
            var name: String
        )
    }

    data class Quiz(
        @SerializedName("course")
        var course: String,
        @SerializedName("courseId")
        var courseId: String,
        @SerializedName("duration")
        var duration: Int,
        @SerializedName("instructions")
        var instructions: List<String>,
        @SerializedName("isSuffle")
        var isSuffle: Boolean,
        @SerializedName("name")
        var name: String,
        @SerializedName("orgId")
        var orgId: String,
        @SerializedName("public")
        var `public`: Boolean,
        @SerializedName("published")
        var published: Boolean,
        @SerializedName("quizType")
        var quizType: Int,
        var isSelected: Boolean
    )
}