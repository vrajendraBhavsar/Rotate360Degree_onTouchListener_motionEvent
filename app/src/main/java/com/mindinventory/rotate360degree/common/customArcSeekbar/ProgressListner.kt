package com.mindinventory.rotate360degree.common.customArcSeekbar

interface ProgressListener {
    operator fun invoke(progress: Int)
}