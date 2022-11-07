package spocktest

data class PC(
    val vendor: String,
    val clockRate: Int,
    val ram: Int,
    val os: String
) {
    fun isLinux() = this.os == "Linux"
}

class Shop {
    fun buyPc(
        vendor: String = "Sunny",
        clockRate: Int = 2333,
        ram: Int = 4096,
        os: String = "Linux"
    ) = PC(vendor, clockRate, ram, os)
}
