package dev.baseio.composeplayground.ui.animations.planetarysystem

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Planet(
  var x: Float,
  var y: Float,
  val radius: Float,
  val planetColor: Color,
  private val velocity: Float,
  val orbitRadius: Float,
  val isForward: Boolean = true
) {
  var radian: Float = 0f
  var moon = Moon()
  var startX = x
  var startY = y

  init {
    moon.prepare(this)
  }

  fun update() {
    if (this.velocity > 0) {
      this.updateMoon()
      when {
        isForward -> {
          this.radian += this.velocity
        }
        else -> {
          this.radian -= this.velocity
        }
      }
      this.x = (this.startX + cos(this.radian.toDouble()) * this.orbitRadius).toFloat()
      this.y = (this.startY + sin(this.radian.toDouble()) * this.orbitRadius).toFloat()

    }
  }
}

private fun Planet.updateMoon() {
  moon.radian += moon.velocity
  moon.x =
    (this.x + cos(moon.radian.toDouble()) * (this.radius + 5)).toFloat()
  moon.y =
    (this.y + sin(moon.radian.toDouble()) * (this.radius + 5)).toFloat()

}

class SolarSystem(private val centerOffset: Offset) {
  val planets by lazy {
    val planets = mutableListOf<Planet>()
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 35f,
        velocity = 0f,
        orbitRadius = 0f,
        color = Color(0xfff9d71c), isForward = true
      )
    ); // sun
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 5f,
        color = Color.Gray,
        velocity = 20f,
        orbitRadius = 65f,
        false
      )
    ); // mercury
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 10f,
        color = Color(0xffFFA500),
        velocity = 10f,
        orbitRadius = 90f,
        true
      )
    ); // venus
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 15f,
        color = Color.Blue,
        velocity = 5f,
        orbitRadius = 125f,
        false
      )
    ); // earth
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 20f,
        color = Color.Red,
        velocity = 7f,
        orbitRadius = 175f,
        true
      )
    ); // mars
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 25f,
        color = Color(0xffFFA500),
        velocity = 5f,
        orbitRadius = 225f,
        true
      )
    ); // jupiter
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 20f,
        color = Color(0xfff9d71c),
        velocity = 4f,
        orbitRadius = 275f,
        true
      )
    ); // saturn
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 15f,
        color = Color.Blue,
        velocity = 10f,
        orbitRadius = 325f,
        true
      )
    ); // uranus
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 25f,
        color = Color(0xff800080),
        velocity = 15f,
        orbitRadius = 375f,
        true
      )
    ); // neptune
    planets.add(
      getPlanetForOptions(
        centerOffset.x,
        centerOffset.y,
        radius = 18f,
        color = Color.Gray,
        velocity = 10f,
        orbitRadius = 450f,
        false
      )
    ) // pluto
    planets
  }


  private fun getPlanetForOptions(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color,
    velocity: Float,
    orbitRadius: Float,
    isForward: Boolean
  ): Planet {
    return Planet(
      centerX,
      centerY,
      radius,
      color,
      velocity / 1000,
      orbitRadius, isForward = isForward
    )
  }
}

class Moon {
  var x: Float = 0f
  var y: Float = 0f
  var radian: Float = 0f
  var velocity: Float = 0f

  fun prepare(planet: Planet) {
    this.x = planet.x + planet.orbitRadius + planet.radius
    this.y = planet.y
    this.radian = 0f
    this.velocity = (Random.nextFloat() + 0.1).div(30).toFloat()
  }

}

