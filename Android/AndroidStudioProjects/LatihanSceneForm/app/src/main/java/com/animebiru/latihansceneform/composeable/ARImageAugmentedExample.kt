package com.animebiru.latihansceneform.composeable

import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.addAugmentedImage
import io.github.sceneview.ar.arcore.getUpdatedAugmentedImages
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberScene
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun OnSessionConfiguration(session: Session, config: Config) {

}

@Composable
fun ARImageAugmentedExample(
    modifier: Modifier = Modifier
) {
    val engine = rememberEngine()
    val scene = rememberScene(engine = engine)
    val modelLoader = rememberModelLoader(engine = engine)
    val childNodes = rememberNodes()
    val context = LocalContext.current
    val augmentedImageNodes = mutableListOf<AugmentedImageNode>()
    val coroutineScope = rememberCoroutineScope()

    ARScene(
        modifier = modifier.fillMaxSize(),
        engine = engine,
        activity = LocalContext.current as? ComponentActivity,
        lifecycle = LocalLifecycleOwner.current.lifecycle,
        childNodes = childNodes,
        modelLoader = modelLoader,
        scene = scene,
        onSessionConfiguration = { session: Session, config: Config ->
            val imageDatabase = context.assets.open("myimages2.imgdb").use {
                AugmentedImageDatabase.deserialize(session, it)
            }
            config.augmentedImageDatabase = imageDatabase
//            config.addAugmentedImage(
//                session, "tes1",
//                context.assets.open("profile.jpg").use(BitmapFactory::decodeStream)
//            )
            session.configure(config)
        },
        onSessionUpdated = { session: Session, frame: Frame ->

//            if (frameSkipCounter % 120 != 0) return@ARScene

            val updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)

            for (img in updatedAugmentedImages) {
                if (img.trackingState == TrackingState.TRACKING) {
                    // Use getTrackingMethod() to determine whether the image is currently
                    // being tracked by the camera.
                    when (img.trackingMethod) {
                        AugmentedImage.TrackingMethod.LAST_KNOWN_POSE -> {
                            // The planar target is currently being tracked based on its last known pose.
                        }
                        AugmentedImage.TrackingMethod.FULL_TRACKING -> {
                            // The planar target is being tracked using the current camera image.
                            session.config.instantPlacementMode = Config.InstantPlacementMode.DISABLED
                        }
                        AugmentedImage.TrackingMethod.NOT_TRACKING -> {
                            // The planar target isn't been tracked.
                        }
                    }

                    // You can also check which image this is based on AugmentedImage.getName().
                    when (img.name) {
                        "router2" -> {
                            if (augmentedImageNodes.none { it.name == img.name }) {
                                coroutineScope.launch {
                                    val augmentedImageNode = AugmentedImageNode(engine, img)
                                    val model = ModelNode(
                                        modelLoader.createModelInstance("soccer_ball.glb"),
                                        scaleToUnits = 0.1f
                                    )
                                    augmentedImageNode.addChildNode(model)
                                    childNodes += augmentedImageNode
                                    augmentedImageNodes += augmentedImageNode
                                }
                            }
                        }
                    }
                }
            }

//            frame.getUpdatedAugmentedImages().forEach { augmentedImage: AugmentedImage? ->
//                if (augmentedImage != null && augmentedImageNodes.none { it.name == augmentedImage.name }) {
//                    val augmentedImageNode = AugmentedImageNode(engine, augmentedImage).apply {
//                        when (augmentedImage.name) {
//                            "tes1" -> {
//                                addChildNode(
//                                    ModelNode(
//                                        modelLoader.createModelInstance("soccer_ball.glb"),
//                                        scaleToUnits = 0.1f
//                                    )
//                                )
//                            }
//                        }
//                    }
//                    childNodes += augmentedImageNode
//                    augmentedImageNodes += augmentedImageNode
//                }
//            }
        }
    )
}