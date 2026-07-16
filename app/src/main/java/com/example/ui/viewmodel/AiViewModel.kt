package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.api.Content
import com.example.data.api.GenerateContentRequest
import com.example.data.api.Part
import com.example.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val isLoading: Boolean = false
)

class AiViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val chatHistory = mutableListOf<Content>()

    private val systemInstruction = Content(
        parts = listOf(
            Part("You are the Christantus Medical Consult AI Assistant. " +
                 "You can search products, explain medicines, answer pharmacy FAQs, help customers navigate the website, and provide general health information. " +
                 "IMPORTANT RULE: You must always include a disclaimer in your response that you are for informational purposes only and do not replace professional medical advice, diagnosis, or treatment. " +
                 "NEVER diagnose illnesses or prescribe prescription medication. " +
                 "For all final medical determinations, complex health issues, or professional assistance, you must firmly but politely direct the user to contact our support team at Christantusmedicalconsult@outlook.com or consult a qualified healthcare professional.")
        ),
        role = "system"
    )

    init {
        // Initial welcome message
        _messages.value = listOf(
            ChatMessage(
                text = "Hello! I'm the Christantus AI Assistant. How can I help you today with your pharmacy or healthcare needs?\n\n*This assistant is for informational purposes only and does not replace professional medical advice, diagnosis, or treatment.*",
                isUser = false
            )
        )
    }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return

        // Add user message to UI
        val currentMessages = _messages.value.toMutableList()
        currentMessages.add(ChatMessage(text = userText, isUser = true))
        // Add loading message
        currentMessages.add(ChatMessage(text = "", isUser = false, isLoading = true))
        _messages.value = currentMessages

        // Add to history
        chatHistory.add(Content(parts = listOf(Part(userText)), role = "user"))

        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.GEMINI_API_KEY
                val request = GenerateContentRequest(
                    contents = chatHistory.toList(),
                    systemInstruction = systemInstruction
                )
                val response = RetrofitClient.service.generateContent(apiKey, request)
                
                val aiText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "I'm sorry, I couldn't process that request."

                // Add to history
                chatHistory.add(Content(parts = listOf(Part(aiText)), role = "model"))

                // Update UI
                val updatedMessages = _messages.value.toMutableList()
                // Remove loading message
                updatedMessages.removeAt(updatedMessages.lastIndex)
                updatedMessages.add(ChatMessage(text = aiText, isUser = false, isLoading = false))
                _messages.value = updatedMessages

            } catch (e: Exception) {
                // Update UI with error
                val updatedMessages = _messages.value.toMutableList()
                // Remove loading message
                updatedMessages.removeAt(updatedMessages.lastIndex)
                updatedMessages.add(ChatMessage(text = "An error occurred. Please try again. (${e.message})", isUser = false, isLoading = false))
                _messages.value = updatedMessages
            }
        }
    }
}
