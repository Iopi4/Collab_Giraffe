package com.example.giraffenew.home.data

// Data class для хранения всех параметров
data class SegmentP1Data(
    val hmiP1Color: Int,
    val segmentLengths: List<Int>,
    val pointPositionsX: List<Int>,
    val pointPositionsY: List<Int>,
    val segmentVisibilities: List<Int>
)

data class SegmentP2Data(
    val hmiP2Color: Int,
    val segmentLengths: List<Int>,
    val pointPositionsX: List<Int>,
    val pointPositionsY: List<Int>,
    val segmentVisibilities: List<Int>
)