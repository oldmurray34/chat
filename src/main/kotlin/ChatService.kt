class ChatNotFoundException(message: String): RuntimeException(message)
class MessageNotFoundException(message: String): RuntimeException(message)

class ChatService {
    val chats = mutableListOf<Chat>()
    private var chatId: Int = 1
    private var messageId: Int = 1

    fun createMessage(id: Int, senderInt: Int, recipientId: Int, content: String) : Boolean {
        var newMessage = Message(id, senderInt, recipientId, content)
        val chatWithMessageId = chats.filter {
            (it.firstUserId == newMessage.senderId || it.firstUserId == newMessage.recipientId) &&
            (it.secondUserId == newMessage.senderId || it.secondUserId == newMessage.recipientId)
        }
        return if (chatWithMessageId.isEmpty()) {
            newMessage = newMessage.copy(id = messageId)
            chats += Chat(chatId, mutableListOf(newMessage), newMessage.senderId, newMessage.recipientId)
            chatId++
            messageId++
            true
        } else {
            newMessage = newMessage.copy(id = messageId)
            val updatedMessages = (chatWithMessageId[0].messages + newMessage) as MutableList
            chats[chats.indexOfFirst { it.id == chatWithMessageId[0].id }] = chatWithMessageId[0].copy(messages = updatedMessages)
            messageId++
            true
        }
    }

    fun showMessages(chatId: Int, messageId: Int, count: Int) : Boolean {
        val chatWithId = chats.filter { it.id == chatId }
        if (chatWithId.isEmpty()) throw ChatNotFoundException("No chat found with id $chatId")
        val newMessages = (chatWithId[0].messages.filter { it.id >= messageId })
        if (newMessages.isEmpty()) throw MessageNotFoundException("No message found with id $messageId")
        val newMessagesCounted: List<Message> = if (count > newMessages.size || count <= 0) {
            newMessages.take(newMessages.size)
        } else {
            newMessages.take(count)
        }
        newMessagesCounted.forEach { it.isRead = true }
        println(newMessagesCounted)
        return true
    }

    fun getChats(userId: Int) : Boolean {
        val filteredChats = chats
            .filter { it.firstUserId == userId || it.secondUserId == userId }
        return if (filteredChats.isEmpty()) {
            println("Нет сообщений")
            false
        } else {
            println("$filteredChats")
            true
        }
    }

    fun getUnreadChatsCount(userId: Int) : Int {
        val filteredChats = chats
            .filter { it.firstUserId == userId }
            .filter { !it.noUnread }
        return if (filteredChats.isEmpty()) {
            throw ChatNotFoundException("No chats found with user $userId")
        } else {
            filteredChats.size
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int) : Boolean {
        val correctChat = chats.filter {chat: Chat -> chat.id == chatId }
        if (correctChat.isEmpty()) throw ChatNotFoundException("No chat with id $chatId")
        val correctMessage = correctChat[0].messages.filter { it.id == messageId }
        if (correctMessage.isEmpty()) throw MessageNotFoundException("No message with id $messageId")
        val newMessages = correctChat[0].messages
        newMessages.remove(correctMessage[0])
        return if (newMessages.isEmpty()) {
            chats.remove(correctChat[0])
            true
        } else {
            chats[chats.indexOfFirst { it.id == chatId }] = correctChat[0].copy(messages = newMessages)
            true
        }
    }

    fun editMessage(chatId: Int, messageId: Int, message: Message) : Boolean {
        val correctChat = chats.filter {chat: Chat -> chat.id == chatId }
        if (correctChat.isEmpty()) throw ChatNotFoundException("No chat with id $chatId")
        val correctMessage = correctChat[0].messages.filter { it.id == messageId }
        if (correctMessage.isEmpty()) throw MessageNotFoundException("No message with id $messageId")
        val newMessages = correctChat[0].messages
        newMessages[newMessages.indexOfFirst { it.id == messageId }] = message.copy()
        chats[chats.indexOfFirst { it.id == chatId }] = correctChat[0].copy(messages = newMessages)
        return true
    }

    fun deleteChat(chatId: Int) : Boolean {
        if (chats.any { it.id == chatId }) {
            chats.removeAt(chats.indexOfFirst { it.id == chatId })
            return true
        } else {
            throw ChatNotFoundException("Chat with id $chatId not found")
        }
    }
}