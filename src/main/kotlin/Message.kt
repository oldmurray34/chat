data class Message (
    val id: Int,
    val senderId : Int,
    val recipientId: Int,
    val content: String,
    var isRead: Boolean = false
        ) {
}