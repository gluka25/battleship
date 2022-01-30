import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point3(var x: Int, var y: Int, var z: Int)

fun Point3.connectionPoints(other: Point3): Collection<Point3> {
    return generateDirectionDiff(other) { it.x }.map { copy(x = it) } +
            generateDirectionDiff(other) { it.y }.map { copy(y = it) } +
            generateDirectionDiff(other) { it.z }.map { copy(z = it) }
}

private fun Point3.generateDirectionDiff(other: Point3, coordinateGetter: (Point3) -> Int): List<Int> {
    val first = coordinateGetter(this)
    val second = coordinateGetter(other)
    return if (abs(first - second) > 1) {
        ((min(first, second) + 1) until max(first, second)).toList()
    } else {
        emptyList()
    }
}

fun Point3.generateBlockedPoints3D(): Collection<Point3> {
    return this.generateBlockedPoints2D() +
            copy(z = z + 1).generateBlockedPoints2D() +
            copy(z = z - 1).generateBlockedPoints2D()
}

private fun Point3.generateBlockedPoints2D(): Collection<Point3> {
    return setOf(
        this,
        copy(x = x - 1),
        copy(x = x + 1),
        copy(y = y - 1),
        copy(y = y + 1),
        copy(x = x - 1, y = y - 1),
        copy(x = x + 1, y = y + 1),
        copy(x = x - 1, y = y + 1),
        copy(x = x + 1, y = y - 1),
    )
}
