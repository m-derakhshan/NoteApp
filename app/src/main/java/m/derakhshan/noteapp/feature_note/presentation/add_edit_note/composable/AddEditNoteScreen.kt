package m.derakhshan.noteapp.feature_note.presentation.add_edit_note.composable

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.derakhshan.noteapp.core.presentation.composable.BackSwipeGesture
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.presentation.add_edit_note.AddEditNoteEvent
import m.derakhshan.noteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import m.derakhshan.noteapp.feature_note.presentation.add_edit_note.SnackBarEvent
import m.derakhshan.noteapp.ui.theme.Black
import m.derakhshan.noteapp.ui.theme.LightBlack
import m.derakhshan.noteapp.ui.theme.spacing

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    navController: NavController,
    noteColor: Int?
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var offset by remember {
        mutableStateOf(0f)
    }

    val backgroundAnimation = remember {
        Animatable(
            Color(if (noteColor != null && noteColor != -1) noteColor else state.color)
        )
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.snackBar.collectLatest { snackBar: SnackBarEvent ->
            if (snackBar is SnackBarEvent.ShowSnackBar)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = snackBar.message
                )

        }
    })

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.Save) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = "Save")
            }
        },
        modifier = Modifier.draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState(onDelta = { delta ->
                offset += (delta * 0.2f)
            }),
            onDragStopped = {
                if (offset > 90)
                    navController.navigateUp()
                offset = 0f
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAnimation.value)
        ) {

            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Navigate Back")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small)
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.Center
            ) {
                Note.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color = color)
                            .border(
                                3.dp, color = if (state.color == color.toArgb())
                                    Color(
                                        ColorUtils.blendARGB(
                                            LightBlack.toArgb(), color.toArgb(), 0.7f
                                        )
                                    )
                                else
                                    Color.Transparent, shape = CircleShape
                            )
                            .clip(CircleShape)
                            .clickable {
                                scope.launch {
                                    backgroundAnimation.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(1000)
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(color.toArgb()))
                            }
                    )
                    Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))

                }
            }


            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
            TransparentHintTextField(
                text = state.title.text,
                hint = state.title.hint,
                onValueChanged = { viewModel.onEvent(AddEditNoteEvent.ChangeTitle(it)) },
                onFocusChangeListener = { viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it)) },
                isHintVisible = state.title.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )

            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
            TransparentHintTextField(
                text = state.content.text,
                hint = state.content.hint,
                onValueChanged = { viewModel.onEvent(AddEditNoteEvent.ChangeContent(it)) },
                onFocusChangeListener = { viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it)) },
                isHintVisible = state.content.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(MaterialTheme.spacing.small)
            )
        }

        BackSwipeGesture(
            offset = offset, arcColor = Color(
                ColorUtils.blendARGB(
                    state.color, Black.toArgb(), 0.3f
                )
            )
        )
    }

}