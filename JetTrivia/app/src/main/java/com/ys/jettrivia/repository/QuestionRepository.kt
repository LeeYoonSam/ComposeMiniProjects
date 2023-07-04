package com.ys.jettrivia.repository

import com.ys.jettrivia.data.DataOrException
import com.ys.jettrivia.model.QuestionItem
import com.ys.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api: QuestionApi
) {
    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
}