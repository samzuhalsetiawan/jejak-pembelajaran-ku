package com.animebiru.latihansceneform.composeable

import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.ar.core.AugmentedImage
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.addAugmentedImage
import io.github.sceneview.ar.arcore.getUpdatedAugmentedImages
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberScene


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

    ARScene(
        modifier = modifier.fillMaxSize(),
        engine = engine,
        activity = LocalContext.current as? ComponentActivity,
        lifecycle = LocalLifecycleOwner.current.lifecycle,
        childNodes = childNodes,
        modelLoader = modelLoader,
        scene = scene,
        onSessionConfiguration = { session: Session, config: Config ->
            config.addAugmentedImage(
                session, "tes1",
                context.assets.open("profile.jpg").use(BitmapFactory::decodeStream)
            )
        },
        onSessionUpdated = { session: Session, frame: Frame ->
            frame.getUpdatedAugmentedImages().forEach { augmentedImage: AugmentedImage? ->
                if (augmentedImage != null && augmentedImageNodes.none { it.name == augmentedImage.name }) {
                    val augmentedImageNode = AugmentedImageNode(engine, augmentedImage).apply {
                        when (augmentedImage.name) {
                            "tes1" -> {
                                addChildNode(
                                    ModelNode(
                                        modelLoader.createModelInstance("soccer_ball.glb"),
                                        scaleToUnits = 0.1f
                                    )
                                )
                            }
                        }
                    }
                    childNodes += augmentedImageNode
                    augmentedImageNodes += augmentedImageNode
                }
            }
        }
    )
}