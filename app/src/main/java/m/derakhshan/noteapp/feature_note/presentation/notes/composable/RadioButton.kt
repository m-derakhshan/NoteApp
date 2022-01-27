package m.derakhshan.noteapp.feature_note.presentation.notes.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import m.derakhshan.noteapp.ui.theme.spacing


@Composable
fun DefaultRadioButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onCheck: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onCheck,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}