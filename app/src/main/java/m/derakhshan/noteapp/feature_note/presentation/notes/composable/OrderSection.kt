package m.derakhshan.noteapp.feature_note.presentation.notes.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import m.derakhshan.noteapp.R
import m.derakhshan.noteapp.feature_note.domain.utils.NoteOrder
import m.derakhshan.noteapp.feature_note.domain.utils.OrderType
import m.derakhshan.noteapp.feature_note.presentation.notes.composable.DefaultRadioButton
import m.derakhshan.noteapp.ui.theme.spacing

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.OrderByDate(OrderType.Ascending),
    onOrderChanged: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = noteOrder is NoteOrder.OrderByTitle
            ) {
                onOrderChanged(NoteOrder.OrderByTitle(noteOrder.orderType))
            }

            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))

            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                selected = noteOrder is NoteOrder.OrderByDate
            ) {
                onOrderChanged(NoteOrder.OrderByDate(noteOrder.orderType))
            }

            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))

            DefaultRadioButton(
                text = stringResource(id = R.string.color),
                selected = noteOrder is NoteOrder.OrderByColor
            ) {
                onOrderChanged(NoteOrder.OrderByColor(noteOrder.orderType))
            }
        }

        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

        Row(modifier = Modifier.fillMaxWidth()) {

            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending
            ) {
                onOrderChanged(noteOrder.copyOrder(OrderType.Ascending))
            }

            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))

            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending
            ) {
                onOrderChanged(noteOrder.copyOrder(OrderType.Descending))
            }
        }
    }
}