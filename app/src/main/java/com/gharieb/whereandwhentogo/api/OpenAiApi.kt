package com.gharieb.whereandwhentogo.api

import com.gharieb.whereandwhentogo.constants.Key.OPENAI_API_KEY
import com.gharieb.whereandwhentogo.data.CompletionRequest
import com.gharieb.whereandwhentogo.data.CompletionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApi {

    @Headers("Authorization: Bearer $OPENAI_API_KEY")
    @POST("v1/completions")
    suspend fun getCompletion(
        @Body completionRequest: CompletionRequest
    ): Response<CompletionResponse>
}