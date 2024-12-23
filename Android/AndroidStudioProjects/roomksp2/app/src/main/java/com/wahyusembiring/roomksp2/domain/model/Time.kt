package com.wahyusembiring.roomksp2.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Time(
   val hour: Int,
   val minute: Int
)