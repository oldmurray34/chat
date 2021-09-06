import org.junit.Test

import org.junit.Assert.*

class ChatServiceTestNew {

    @Test
    fun createMessageNoChat() {
        val service = ChatService()
        assertTrue(service.createMessage(1, 1,2, "Привет"))
    }

    @Test
    fun createMessageExistingChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        assertTrue(service.createMessage(2, 2,1, "Привет, как дела?"))
    }

    @Test
    fun showMessages() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        assertTrue(service.showMessages(1,1,1))
    }

    @Test (expected = ChatNotFoundException::class)
    fun showMessagesFailedNoChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        assertTrue(service.showMessages(2,1,1))
    }

    @Test (expected = MessageNotFoundException::class)
    fun showMessagesFailedNoMessage() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        assertTrue(service.showMessages(1,2,1))
    }

    @Test
    fun getChats() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,3, "Привет")
        assertTrue(service.getChats(1))
    }

    @Test
    fun getChatsNoChats() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,3, "Привет")
        assertFalse(service.getChats(4))
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,3, "Привет")
        val expected = 2
        assertEquals(expected, service.getUnreadChatsCount(1))
    }

    @Test (expected = ChatNotFoundException::class)
    fun getUnreadChatsCountNoChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,3, "Привет")
        val expected = 2
        assertEquals(expected, service.getUnreadChatsCount(4))
    }

    @Test
    fun deleteMessage() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        assertTrue(service.deleteMessage(1, 1))
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteMessageFailedNoChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        assertTrue(service.deleteMessage(2, 1))
    }

    @Test (expected = MessageNotFoundException::class)
    fun deleteMessageFailedNoMessage() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        assertTrue(service.deleteMessage(1, 10))
    }

    @Test
    fun editMessage() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        val updatedMessage = Message(1, 1, 2, "Еееееее")
        assertTrue(service.editMessage(1, 1, updatedMessage))
    }

    @Test (expected = ChatNotFoundException::class)
    fun editMessageFailedNoChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        val updatedMessage = Message(1, 1, 2, "Еееееее")
        assertTrue(service.editMessage(3, 1, updatedMessage))
    }

    @Test (expected = MessageNotFoundException::class)
    fun editMessageFailedNoMessage() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        val updatedMessage = Message(1, 1, 2, "Еееееее")
        assertTrue(service.editMessage(1, 4, updatedMessage))
    }

    @Test
    fun deleteChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        assertTrue(service.deleteChat(1))
    }

    @Test (expected = ChatNotFoundException::class)
    fun deleteChatFailedNoChat() {
        val service = ChatService()
        service.createMessage(1, 1,2, "Привет")
        service.createMessage(2, 1,2, "Как дела?")
        assertTrue(service.deleteChat(4))
    }
}