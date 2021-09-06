data class Chat (
    val id: Int,
    var messages: MutableList<Message>,
    val firstUserId: Int,
    val secondUserId: Int,
    val noUnread: Boolean = (messages.filter { !it.isRead }).isEmpty()
        ) {
}