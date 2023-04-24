package com.example.atleticolineupapp.util.drop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import com.example.atleticolineupapp.util.drag.DragData
import com.example.atleticolineupapp.util.drag.LocalDragTargetInfo

@Composable
fun DropContainer(
    modifier: Modifier,
    onDrag: (inBounds: Boolean, isDragging: Boolean) -> Unit,
    content: @Composable (BoxScope.(data: DragData?) -> Unit)
) {
    val dragState = LocalDragTargetInfo.current
    val dragPosition = dragState.dragPosition
    val dragOffset = dragState.dragOffset
    var inBounds by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned {
            it.boundsInWindow().let { rect ->
                inBounds = rect.contains(dragPosition + dragOffset)
            }
        }
    ) {
        val dragData = if (inBounds) dragState.dragData else null
        onDrag(inBounds, dragState.isDragging)
        content(dragData)
    }
}

@Composable
fun DropPaneContent(
    modifier: Modifier,
    dragImage: Painter?
) {
    if (dragImage != null) {
        Image(
            painter = dragImage,
            contentDescription = "",
            modifier = modifier.clip(shape = ShapeDefaults.Large)
        )
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = "",
                modifier = modifier.align(Alignment.Center),
                tint = Color.Red)
        }
        Icon(Icons.Filled.Person, contentDescription = "", tint = Color.Red)
    }
}