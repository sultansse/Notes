package com.software1t.notes.ui.fragment_note_list

sealed class SwipeAction {
    object Archive : SwipeAction()
    object Delete : SwipeAction()
    object Custom : SwipeAction()
    // Add more custom swipe actions if needed
}
