package com.gharieb.whereandwhentogo.data

data class CompletionRequest(
	val max_tokens: Int,
	val temperature: Float = 0f,
	val model: String,
	val prompt: String,
)

