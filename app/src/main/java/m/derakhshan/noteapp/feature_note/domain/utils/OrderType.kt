package m.derakhshan.noteapp.feature_note.domain.utils

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
