package com.animebiru.latihansceneform.composeable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.ar.core.Config
import com.google.ar.core.Plane
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import kotlinx.coroutines.launch

@Composable
fun ARPlaneAnchorExample(
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(false) }
    var planeRenderer by remember { mutableStateOf(true) }
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val childNodes = rememberNodes()
    val coroutineScope = rememberCoroutineScope()

    ARScene(
        modifier = modifier.fillMaxSize(),
        childNodes = childNodes,
        engine = engine,
        modelLoader = modelLoader,
        planeRenderer = planeRenderer,
        onSessionConfiguration = { session, config ->
            config.depthMode =
                when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
            config.instantPlacementMode = Config.InstantPlacementMode.DISABLED
            config.lightEstimationMode =
                Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },
//                        onTapAR = { motionEvent: MotionEvent, hitResult: HitResult ->
//
//                        },
        onSessionUpdated = { _, frame ->
            if (childNodes.isNotEmpty()) return@ARScene

            frame.getUpdatedPlanes()
                .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                ?.let { plane ->
                    isLoading = true
                    childNodes += AnchorNode(
                        engine = engine,
                        anchor = plane.createAnchor(plane.centerPose)
//                                anchor = hitResult.createAnchor()
                    ).apply {
//                                isEditable = true
                        coroutineScope.launch {
                            modelLoader.loadModelInstance("wooden_step_ladder_scan_lowpoly.glb")?.let {
                                addChildNode(
                                    ModelNode(
                                        modelInstance = it,
                                        // Scale to fit in a 0.5 meters cube
                                        scaleToUnits = 0.5f,
                                        // Bottom origin instead of center so the
                                        // model base is on floor
                                        centerOrigin = Position(y = -0.5f)
                                    ).apply {
//                                                rotation = Float3(90f, 180f, 0f)
//                                                isEditable = true
                                    }
                                )
                            }
                            planeRenderer = false
                        }
                    }
                }
        }
    )
}