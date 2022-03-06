package dev.baseio.composeplayground.particlesystem

import android.graphics.PointF
import kotlin.random.Random

data class Particle(
  var alpha: Float,
  var pos: PointF,
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
