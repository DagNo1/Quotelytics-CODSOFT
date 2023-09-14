package codsoft.dagno1.quotelytics.data

class Quote (
    val id: Int,
    val content: String,
    val author: String,
    var isRead: Boolean = false,
    var isFavorite: Boolean = false,
    val favoriteOrder: Int = -1
)