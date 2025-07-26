package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val label: String,
)