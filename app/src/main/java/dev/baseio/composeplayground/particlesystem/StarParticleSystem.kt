package dev.baseio.composeplayground.particlesystem

import android.graphics.PointF
import androidx.core.util.toRange
import kotlinx.coroutines.delay
import kotlin.random.Random

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
      delay(800)
      it.scale = randomAlpha.nextDouble(1.0, 5.0).toFloat()
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