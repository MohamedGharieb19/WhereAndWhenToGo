package com.gharieb.whereandwhentogo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.whereandwhentogo.data.CompletionRequest
import com.gharieb.whereandwhentogo.data.CompletionResponse
import com.gharieb.whereandwhentogo.data.Message
import com.gharieb.whereandwhentogo.module.AppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException


class AnswerViewModel: ViewModel() {

    private val _messageList = MutableLiveData<Message>()
    val messageList: LiveData<Message> get() = _messageList

    private fun addToTextView(message: String){
        _messageList.postValue(Message(message))
    }

    fun callApi(question: String){
        val completionRequest = CompletionRequest(
            model = "text-davinci-003",
            prompt = question,
            max_tokens = 4000
        )
        viewModelScope.launch {
            try {
                val response = AppModule.provideApi().getCompletion(completionRequest)
                handleApiResponse(response)
            }catch (e: SocketTimeoutException){
                addToTextView("Timeout $e")
            }
        }
    }

    private suspend fun handleApiResponse(response: Response<CompletionResponse>) {
        withContext(Dispatchers.Main){
            if (response.isSuccessful){
                response.body()?.let { completionResponse ->
                    val result = completionResponse.choices.firstOrNull()?.text
                    addToTextView(result.toString())
                }
            }
        }
    }









}