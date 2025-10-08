package com.example.chatapp.wds.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * WDS Dimensions - Spacing tokens for consistent layout spacing
 *
 * Example usage: WdsTheme.dimensions.wdsSpacingDouble
 *
 * Spacing scale:
 * - Quarter (2dp) - Minimal spacing
 * - Half (4dp) - Very tight spacing
 * - HalfPlus (6dp) - Tight spacing
 * - Single (8dp) - Base spacing unit
 * - SinglePlus (12dp) - Comfortable spacing
 * - Double (16dp) - Standard spacing
 * - DoublePlus (20dp) - Generous spacing
 * - Triple (24dp) - Large spacing
 * - TriplePlus (28dp) - Extra large spacing
 * - Quad (32dp) - Very large spacing
 * - Quint (40dp) - Maximum spacing
 */
@Immutable
class WdsDimensions {
    val wdsSizeZero: Dp = BaseDimensions.wdsSizeZero
    val wdsSpacingQuarter: Dp = BaseDimensions.wdsSpacingQuarter
    val wdsSpacingHalf: Dp = BaseDimensions.wdsSpacingHalf
    val wdsSpacingHalfPlus: Dp = BaseDimensions.wdsSpacingHalfPlus
    val wdsSpacingSingle: Dp = BaseDimensions.wdsSpacingSingle
    val wdsSpacingSinglePlus: Dp = BaseDimensions.wdsSpacingSinglePlus
    val wdsSpacingDouble: Dp = BaseDimensions.wdsSpacingDouble
    val wdsSpacingDoublePlus: Dp = BaseDimensions.wdsSpacingDoublePlus
    val wdsSpacingTriple: Dp = BaseDimensions.wdsSpacingTriple
    val wdsSpacingTriplePlus: Dp = BaseDimensions.wdsSpacingTriplePlus
    val wdsSpacingQuad: Dp = BaseDimensions.wdsSpacingQuad
    val wdsSpacingQuint: Dp = BaseDimensions.wdsSpacingQuint

    val wdsBorderWidthNone: Dp = BaseDimensions.wdsBorderWidthNone
    val wdsBorderWidthThin: Dp = BaseDimensions.wdsBorderWidthThin
    val wdsBorderWidthMedium: Dp = BaseDimensions.wdsBorderWidthMedium
    val wdsBorderWidthThick: Dp = BaseDimensions.wdsBorderWidthThick
}

/**
 * CompositionLocal used to pass [WdsDimensions] down the composition hierarchy.
 *
 * This value is set as part of [WdsTheme]. Ensure [WdsTheme] is included in your composition
 * hierarchy to use this (automatically included if using [WdsTheme]).
 *
 * To get the current value of this CompositionLocal, use [WdsTheme.dimensions].
 */
val LocalDimensions: ProvidableCompositionLocal<WdsDimensions> = staticCompositionLocalOf {
    error(
        "CompositionLocal not present for LocalDimensions. This is likely because WdsTheme has not " +
                "been included in your Compose hierarchy."
    )
}

