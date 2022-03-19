package dev.baseio.composeplayground.ui.animations.pulltorefresh.particlesystem

import android.graphics.PointF
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class StarParticleSystem(
  override val viewPortWidth: Float,
  override val viewPortHeight: Float, override val particleCount: Int
) :
  ParticleSystem() {

  private val random = Random(particleCount)

  val particles by lazy {
    prepare()
  }

  override suspend fun update() {
    particles.forEach {
      delay(50)
      it.scale = Random.nextDouble(1.0, 3.0).toFloat()
      it.alpha = Random.nextDouble(0.0, 1.0).toFloat()
    }
  }

  override fun prepare(): List<Particle> {
    val particles = mutableListOf<Particle>()
    repeat(particleCount) {
      val position = getRandomVect(0f, 0f, viewPortWidth, viewPortHeight, random)
      val particle =
        Particle(
          alpha = 1f,
          pos = PointF(position[0], position[1]),
          scale = Random.nextDouble(1.0, 3.0).toFloat()
        )
      particles.add(particle)
    }
    return particles
  }
}