package com.example.learnfundamental2

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.learnfundamental2.databinding.ActivityLatihanDragAndDropBinding

class LatihanDragAndDropActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanDragAndDropBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dragableView.setOnLongClickListener {
            val text = "Testing"
            val item = ClipData.Item(text)
            val mimeType = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val clipData = ClipData(text, mimeType, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)

            it.startDragAndDrop(clipData, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
            true
        }

        val dragListener = View.OnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> true
                DragEvent.ACTION_DROP -> {
//                    get clip data
                    val text = event.clipData.getItemAt(0).text
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show()

                    view.invalidate()

//                    Remove view from old parent
                    val originView = event.localState as View
                    val originParent = originView.parent as ViewGroup
                    originParent.removeView(originView)

//                    add view to parent on drag drop
                    val newParent = view as LinearLayout
                    newParent.addView(originView)
                    originView.visibility = View.VISIBLE
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    view.invalidate()
                    true
                }
                else -> false
            }
        }

        binding.llTop.setOnDragListener(dragListener)
        binding.llBottom.setOnDragListener(dragListener)

    }
}