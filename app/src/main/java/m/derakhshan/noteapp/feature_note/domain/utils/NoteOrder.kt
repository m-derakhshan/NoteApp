package m.derakhshan.noteapp.feature_note.domain.utils


sealed class NoteOrder(open val orderType: OrderType) {

    data class OrderByColor(override val orderType: OrderType) : NoteOrder(orderType)
    data class OrderByDate(override val orderType: OrderType) : NoteOrder(orderType)
    data class OrderByTitle(override val orderType: OrderType) : NoteOrder(orderType)

    fun copyOrder(orderType: OrderType):NoteOrder{
        return when(this){
            is OrderByColor -> OrderByColor(orderType)
            is OrderByDate -> OrderByDate(orderType)
            is OrderByTitle -> OrderByTitle(orderType)
        }
    }
}