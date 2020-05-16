package sandbox.neural.parasite.auxiliary

enum class Constant(val value: Double) {
    Fi(0.7), // Коэффициент сцепления шин с дорогой (бетон)
    F0(0.015), // Коэффициент сопротивления качению (бетон)
    Af(5.5e-4), // Коэффициент влияния скорости на сопротивления качению колеса
    G(9.8)
}
