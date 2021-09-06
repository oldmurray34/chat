import org.junit.Assert

fun main() {
    val service = ChatService()
    service.createMessage(1, 1,2, "Привет")
    service.createMessage(2, 1,2, "Как дела?")
    service.deleteMessage(1, 1)
    service.showMessages(1,1,1)
}