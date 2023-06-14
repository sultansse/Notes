package com.software1t.notes.ui.fragment_settings.swipe_helper

sealed class SwipeAction {
    object Archive : SwipeAction()
    object Delete : SwipeAction()
    object Custom : SwipeAction()
    // Add more custom swipe actions if needed
}
