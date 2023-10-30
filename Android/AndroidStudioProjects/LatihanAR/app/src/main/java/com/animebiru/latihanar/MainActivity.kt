package com.animebiru.latihanar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.sceneform.math.Matrix
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.position
import io.github.sceneview.ar.arcore.rotation
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.model.model
import io.github.sceneview.node.Node
import io.github.sceneview.node.RenderableNode
import io.github.sceneview.renderable.Renderable
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    private lateinit var arSceneView: ArSceneView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arSceneView = findViewById(R.id.sceneView)

        arSceneView.arSession?.planeFindingEnabled = true
        arSceneView.planeRenderer.isVisible = true
        arSceneView.planeRenderer.isShadowReceiver = false

        val modelNode = ArModelNode(arSceneView.engine, PlacementMode.PLANE_HORIZONTAL, instantAnchor = true)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                val model = modelNode.loadModelGlb(this@MainActivity, "models/soccer_ball.glb", scaleToUnits = 0.5f) {
                    Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: Error Failed to Load")
                }
                arSceneView.planeRenderer.isVisible = false
                Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: Model Loaded ${model.hashCode()}")
            }
        }

        modelNode.anchor()
        arSceneView.addChild(modelNode)
//        arSceneView.onTapAr = ::onTapAr
    }

    private fun onTapAr(hitResult: HitResult, motionEvent: MotionEvent) {
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: TAPPED ${arSceneView.children.size}")
        val plane = hitResult.trackable as Plane
        val anchor = plane.createAnchor(hitResult.hitPose)

        val modelNode = createArModelNode("models/soccer_ball.glb")

        modelNode.let {
            it.anchor = anchor
            Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: Anchored")
            arSceneView.addChild(it)
        }
    }

    private fun createArModelNode(modelPath: String, placementMode: PlacementMode? = null): ArModelNode {
        val modelNode = if (placementMode == null) ArModelNode(arSceneView.engine) else ArModelNode(arSceneView.engine, placementMode)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                val model = modelNode.loadModelGlb(this@MainActivity, modelPath) {
                    Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: Error Failed to Load")
                }
                Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onTapAr: Model Loaded ${model.hashCode()}")
            }
        }
        return modelNode
    }
}