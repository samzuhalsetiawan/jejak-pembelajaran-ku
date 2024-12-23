package com.samzuhalsetiawan.sekaide.util

import kotlin.time.Duration.Companion.minutes

val Int.beatLengthInMilliseconds: Long
    get() = 1.minutes.inWholeMilliseconds / this