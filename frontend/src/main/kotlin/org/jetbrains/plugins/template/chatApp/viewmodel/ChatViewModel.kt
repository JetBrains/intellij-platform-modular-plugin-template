package org.jetbrains.plugins.template.chatApp.viewmodel

import com.intellij.openapi.Disposable
import com.intellij.openapi.diagnostic.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.plugins.template.ChatMessage

interface ChatViewModelApi : Disposable {
    val chatMessagesFlow: StateFlow<List<ChatMessage>>

    val isSendingFlow: StateFlow<Boolean>

    fun sendMessage(message: String)
}

class ChatViewModel(
    private val coroutineScope: CoroutineScope,
    private val repository: ChatRepositoryApi
) : ChatViewModelApi {
    companion object {
        private val LOG = Logger.getInstance(ChatViewModel::class.java)
    }

    private val _chatMessagesFlow = MutableStateFlow(emptyList<ChatMessage>())
    override val chatMessagesFlow: StateFlow<List<ChatMessage>> = _chatMessagesFlow.asStateFlow()

    private val _isSendingFlow = MutableStateFlow(false)
    override val isSendingFlow: StateFlow<Boolean> = _isSendingFlow.asStateFlow()

    private var currentSendMessageJob: Job? = null

    init {
        repository
            .messagesFlow
            .onEach { messages -> _chatMessagesFlow.value = messages }
            .launchIn(coroutineScope)
    }

    override fun sendMessage(message: String) {
        val trimmedMessage = message.trim()
        if (trimmedMessage.isEmpty() || currentSendMessageJob?.isActive == true) return

        currentSendMessageJob = coroutineScope.launch {
            _isSendingFlow.value = true

            try {
                repository.sendMessage(trimmedMessage)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                LOG.warn("Failed to send chat message", e)
            } finally {
                _isSendingFlow.value = false
                currentSendMessageJob = null
            }
        }
    }

    override fun dispose() {
        coroutineScope.cancel()
    }
}
