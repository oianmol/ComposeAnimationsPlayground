package dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.particlesystem

import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

data class Particle(
  var alpha: Float,
  var pos: Offset,
  var scale: Float,
)

abstract class ParticleSystem {
  abstract val viewPortWidth: Float
  abstract val viewPortHeight: Float
  abstract val particleCount: Int

  abstract suspend fun update()
  abstract fun prepare(): List<Particle>
}




fun getRandomVect(
  minX: Float,
  minY: Float,
  maxX: Float,
  maxY: Float,
  random: Random,
): FloatArray {
  val result = FloatArray(2)
  result[0] = (random.nextDouble() * (maxX - minX) + minX).toFloat()
  result[1] = (random.nextDouble() * (maxY - minY) + minY).toFloat()
  return result
}
