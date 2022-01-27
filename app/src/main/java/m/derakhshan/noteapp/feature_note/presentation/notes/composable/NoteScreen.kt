package m.derakhshan.noteapp.feature_note.presentation.notes.composable


import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import m.derakhshan.noteapp.R
import m.derakhshan.noteapp.core.presentation.MainNavGraph
import m.derakhshan.noteapp.feature_note.presentation.notes.NoteEvents
import m.derakhshan.noteapp.feature_note.presentation.notes.NoteViewModel
import m.derakhshan.noteapp.ui.theme.VeryLightYellow
import m.derakhshan.noteapp.ui.theme.Yellow
import m.derakhshan.noteapp.ui.theme.spacing


@ExperimentalAnimationApi
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()
    val offset by animateDpAsState(
        targetValue = if (state.isAddNoteVisible) 0.dp else (100).dp,
        animationSpec = tween(500)
    )

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(MainNavGraph.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.offset(y = offset)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_note)
                )
            }
        },
        backgroundColor = VeryLightYellow
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_notes),
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall)
                )

                IconButton(onClick = {
                    viewModel.onEvent(NoteEvents.ToggleOrderSection)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Sort,
                        contentDescription = stringResource(id = R.string.sorting)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium),
                    noteOrder = state.noteOrder
                ) {
                    viewModel.onEvent(NoteEvents.ChangeOrder(it))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Yellow)
            )
            //--------------------(hide or show add button)--------------------//
            if (lazyState.isScrollingUp())
                viewModel.onEvent(NoteEvents.ListScrollUp)
            else
                viewModel.onEvent(NoteEvents.ListScrollDown)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyState
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium)
                            .clickable {
                                navController.navigate(
                                    MainNavGraph.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            }
                    ) {
                        viewModel.onEvent(NoteEvents.DeleteNote(note = note))
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note is deleted!",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed)
                                viewModel.onEvent(NoteEvents.RestoreNote)

                        }

                    }
                }
            }

        }

    }


}


@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousItemIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousItemIndex != firstVisibleItemIndex)
                previousItemIndex > firstVisibleItemIndex
            else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousItemIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value

}