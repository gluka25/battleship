interface Direction {
    fun move(point: Point3, size: Int): Point3
}

private object X : Direction {
    override fun move(point: Point3, size: Int): Point3 {
        return point.copy(x = point.x + if (size > 0) size - 1 else size + 1)
    }
}

private object Y : Direction {
    override fun move(point: Point3, size: Int): Point3 {
        return point.copy(y = point.y + if (size > 0) size - 1 else size + 1)
    }
}

private object Z : Direction {
    override fun move(point: Point3, size: Int): Point3 {
        return point.copy(z = point.z + if (size > 0) size - 1 else size + 1)
    }
}

object DirectionGenerator {
    private val directions: List<Direction> = listOf(X, Y, Z)

    fun generateDirection(): Direction {
        return directions.random()
    }
}
