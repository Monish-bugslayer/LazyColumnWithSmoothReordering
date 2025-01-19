package com.example.lazycolumnwithsmoothreordering.reorderable_column

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lazycolumnwithsmoothreordering.R

data class Product(val id: Int,val name: String)

class ReorderableLazyColumn() {

    var list = mutableStateOf(emptyList<Product>())

    @Composable
    fun ReorderableColumn() {
        val sampleList = mutableListOf<Product>()
        for(i in 1..100) {
            sampleList.add(Product(i, "Products $i"))
        }
        list.value = sampleList
        val listState = rememberLazyListState()
        val reorderableLazyColumnState = rememberReorderableLazyListState(listState) { from, to ->
            list.value = list.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(list.value, key = { _, item -> item.id }) { index, item ->
                ReorderableItem(reorderableLazyColumnState, item.id) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Card(onClick = {}, modifier = Modifier.semantics {
                            customActions = listOf(
                                CustomAccessibilityAction(label = "Move Up", action = {
                                    if (index > 0) {
                                        list.value = list.value.toMutableList().apply {
                                                add(index - 1, removeAt(index))
                                            }
                                        true
                                    } else {
                                        false
                                    }
                                }),
                                CustomAccessibilityAction(label = "Move Down", action = {
                                    if (index < list.value.size - 1) {
                                        list.value = list.value.toMutableList().apply {
                                                add(index + 1, removeAt(index))
                                            }
                                        true
                                    } else {
                                        false
                                    }
                                }),
                            )
                        }, interactionSource = interactionSource
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val modifier =
                                if (index != 0) Modifier
                                    .draggableHandle(
                                        onDragStarted = { /** start haptic **/ },
                                        onDragStopped = { /** stop haptic **/ },
                                        interactionSource = interactionSource
                                    )
                                    .clearAndSetSemantics { } else Modifier
                                    .alpha(0.5f)
                                    .clearAndSetSemantics { }
                            Text(
                                text = item.name,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .width(200.dp).height(50.dp)
                            )
                            Image(
                                modifier = modifier.padding(end = 10.dp),
                                painter = painterResource(R.drawable.baseline_reorder_24),
                                contentDescription = "",
                                alignment = Alignment.CenterEnd
                            )
                        }
                    }
                }
            }
        }
    }
}

