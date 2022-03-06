package dev.baseio.composeplayground.particlesystem

import android.graphics.PointF
import androidx.core.util.toRange
import kotlinx.coroutines.delay
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

class StarParticleSystem(
  override val viewPortWidth: Float,
  override val viewPortHeight: Float, override val particleCount: Int
) :
  ParticleSystem() {

  private val random = Random(particleCount)
  private val randomAlpha = Random

  val particles by lazy {
    prepare()
  }

  override suspend fun update() {
    particles.forEach {
      delay(1000)
      it.scale = randomAlpha.nextDouble(1.0, 3.0).toFloat()
      it.alpha = randomAlpha.nextDouble(0.0, 1.0).toFloat()
    }
  }

  override fun prepare(): List<Particle> {
    val particles = mutableListOf<Particle>()
    repeat(particleCount) {
      val position = getRandomVect(0f, 0f, viewPortWidth, viewPortHeight, random)
      val particle =
        Particle(alpha = 1f, pos = PointF(position[0], position[1]), scale = 1f)
      particles.add(particle)
    }
    return particles
  }
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
