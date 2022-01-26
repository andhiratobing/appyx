package com.bumble.appyx.v2.app.node.child

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.coroutineScope
import com.bumble.appyx.v2.app.ui.atomic_tangerine
import com.bumble.appyx.v2.app.ui.manatee
import com.bumble.appyx.v2.app.ui.silver_sand
import com.bumble.appyx.v2.app.ui.sizzling_red
import com.bumble.appyx.v2.core.integration.NodeHost
import com.bumble.appyx.v2.core.integrationpoint.IntegrationPointStub
import com.bumble.appyx.v2.core.modality.BuildContext
import com.bumble.appyx.v2.core.modality.BuildContext.Companion.root
import com.bumble.appyx.v2.core.node.Node
import com.bumble.appyx.v2.core.state.SavedStateMap
import com.bumble.appyx.v2.sandbox.ui.md_amber_500
import com.bumble.appyx.v2.sandbox.ui.md_blue_500
import com.bumble.appyx.v2.sandbox.ui.md_blue_grey_500
import com.bumble.appyx.v2.sandbox.ui.md_cyan_500
import com.bumble.appyx.v2.sandbox.ui.md_grey_500
import com.bumble.appyx.v2.sandbox.ui.md_indigo_500
import com.bumble.appyx.v2.sandbox.ui.md_light_blue_500
import com.bumble.appyx.v2.sandbox.ui.md_light_green_500
import com.bumble.appyx.v2.sandbox.ui.md_lime_500
import com.bumble.appyx.v2.sandbox.ui.md_pink_500
import com.bumble.appyx.v2.sandbox.ui.md_teal_500
import kotlin.random.Random
import kotlinx.coroutines.delay

class GenericChildNode(
    buildContext: BuildContext,
    counterStartValue: Int
) : Node(
    buildContext = buildContext
) {

    companion object {
        private const val KEY_ID = "Id"
        private const val KEY_COUNTER = "Counter"
        private const val KEY_COLOR_INDEX = "ColorIndex"
    }

    private val colors = listOf(
        manatee,
        sizzling_red,
        atomic_tangerine,
        silver_sand,
        md_pink_500,
        md_indigo_500,
        md_blue_500,
        md_light_blue_500,
        md_cyan_500,
        md_teal_500,
        md_light_green_500,
        md_lime_500,
        md_amber_500,
        md_grey_500,
        md_blue_grey_500
    )

    private val id = Random.nextInt(10000)
    private var counter by mutableStateOf(counterStartValue)
    private var colorIndex by mutableStateOf(
        buildContext.savedStateMap?.get(KEY_COLOR_INDEX) as? Int ?:
        Random.nextInt(colors.size)
    )

    init {
        lifecycle.coroutineScope.launchWhenCreated {
            while (true) {
                counter++
                delay(1000)
            }
        }
    }

    override fun onSaveInstanceState(scope: SaverScope): SavedStateMap =
        super.onSaveInstanceState(scope) + mapOf(
            KEY_ID to id,
            KEY_COUNTER to counter,
            KEY_COLOR_INDEX to colorIndex
        )

    @Composable
    override fun View(modifier: Modifier) {
        val color by derivedStateOf { colors[colorIndex] }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = color,
                    shape = RoundedCornerShape(6.dp)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { colorIndex = Random.nextInt(colors.size) },
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            ) {
                Text(
                    text = "Child ($id)",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "$counter",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 40.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun GenericChildNodePreview() {
    Box(Modifier.size(200.dp)) {
        NodeHost(integrationPoint = IntegrationPointStub()) {
            GenericChildNode(root(null), 100)
        }
    }
}

