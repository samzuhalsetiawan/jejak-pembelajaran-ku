package com.samzuhalsetiawan.sekaide.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.sekaide.util.beatLengthInMilliseconds
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes

data class ScoreEditorState(
    val lengthInMilliseconds: Long = 3.minutes.inWholeMilliseconds,
    val bpm: Int = 160,
    val timeSignature: Pair<Int, Int> = 4 to 4,
    val noteSpeed: Int = 1
) {
    companion object Saver :
        androidx.compose.runtime.saveable.Saver<ScoreEditorState, Map<String, Any>> {
        override fun restore(value: Map<String, Any>): ScoreEditorState? {
            return ScoreEditorState(
                lengthInMilliseconds = value["lengthInMilliseconds"] as Long,
                bpm = value["bpm"] as Int,
                timeSignature = (value["timeSignature"] as IntArray).let { it[0] to it[1] },
                noteSpeed = value["noteSpeed"] as Int
            )
        }

        override fun SaverScope.save(value: ScoreEditorState): Map<String, Any>? {
            return mapOf(
                "lengthInMilliseconds" to value.lengthInMilliseconds,
                "bpm" to value.bpm,
                "timeSignature" to value.timeSignature.let { intArrayOf(it.first, it.second) },
                "noteSpeed" to value.noteSpeed
            )
        }
    }
}

@Composable
fun rememberScoreEditorState(): ScoreEditorState {
    return rememberSaveable(saver = ScoreEditorState.Saver) {
        ScoreEditorState()
    }
}

@Composable
fun ScoreEditor(
    state: ScoreEditorState = rememberScoreEditorState()
) {
//    val scrollState = rememberScrollState()
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(
//                state = scrollState,
//                reverseScrolling = true,
//                flingBehavior = ScrollableDefaults.flingBehavior()
//            )
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height((state.lengthInMilliseconds * state.noteSpeed).toInt().dp)
//        ) {
//            var nextBeat = 0L
//            while (nextBeat < state.lengthInMilliseconds) {
//                HorizontalDivider(
//                    thickness = if (nextBeat % state.timeSignature.first == 0L) 2.dp else 1.dp
//                )
//                nextBeat += state.bpm.beatLengthInMilliseconds
//            }
//        }
//    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(88000.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Blue)
                )
            )
    ) { }
}

@Composable
private fun Dummy(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(2.dp, Color.Black)
            .background(Color(Random.nextLong())),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}