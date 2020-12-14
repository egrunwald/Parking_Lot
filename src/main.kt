package parking
import java.util.*
val scanner = Scanner(System.`in`)
var spaces = emptyArray<Spots>()
var lotCreated = false
class Spots (var occupied: Boolean = false, var regNum: String, var color: String)
class ParkingLot {
    companion object {
        fun create(spots: Int): Int {
            spaces = emptyArray()
            val start = Spots(false, "", "")
            for (i in 0 until spots) {
                spaces += start
            }
            println("Created a parking lot with $spots spots.")
            lotCreated = true
            return spots
        }
        fun park(reg: String, color: String) {
            val newCar = Spots(true, reg, color.toLowerCase())
            var full = true
            for (i in spaces.indices) {
                if (!spaces[i].occupied) {
                    spaces[i] = newCar
                    full = false
                    println("$color car parked in spot ${i + 1}.")
                    break
                }
            }
            if (full) println("Sorry, the parking lot is full.")
        }
        fun leave(spot: Int) {
            if (spot in spaces.indices) {
                if (spaces[spot].occupied) {
                    println("Spot ${spot + 1} is free.")
                    spaces[spot].occupied = false
                } else println("There is no car in spot ${spot + 1}.")
            } else println("not a valid spot.")
        }
        fun status(numOfSpots: Int) {
            var check = 0
            for (i in spaces.indices) {
                if (spaces[i].occupied) {
                    println("${i + 1} ${spaces[i].regNum} ${spaces[i].color}")
                } else check++
            }
            if (check == numOfSpots) {
                println("Parking lot is empty.")
            }
        }
        fun sort(outputs: String, by: String, numOfSpots: Int, searchBy: String) {
            var check = 0
            var cars = emptyArray<String>()
            for (i in spaces.indices) {
                if (spaces[i].occupied) {
                    when (by) {
                        "color" -> if (outputs == "reg") {
                            if (spaces[i].color == searchBy.toLowerCase()) {
                                cars += spaces[i].regNum
                            } else check++
                        } else {
                            if (spaces[i].color == searchBy.toLowerCase()) {
                                cars += (i + 1).toString()
                            } else check++
                        }
                        else -> if (spaces[i].regNum == searchBy) {
                            cars += (i + 1).toString()
                        } else check++
                    }
                } else check++
            }
            if (check == numOfSpots) {
                if (by == "color") {
                    println("No cars with color $searchBy were found.")
                } else println("No cars with registration number $searchBy were found.")
            } else println(cars.joinToString(", ").trim())
        }
        fun spotReg(spot: Int) {
            if (spaces[spot - 1].occupied) {
                println("$spot ${spaces[spot - 1].regNum}")
            } else println("No car parked in spot ${spot}.")
        }
        fun noLot() {
            println("Sorry, a parking lot has not been created.")
        }
    }
}

fun main() {
    var numOfSpots = 0
    do {
        print("Enter command: ")
        val input = scanner.nextLine().split(" ")
        val action = input[0]
        try {
            when (action.toLowerCase()) {
                "create" -> numOfSpots = ParkingLot.create(input[1].toInt())
                "park" -> if (lotCreated) {
                    ParkingLot.park(input[1], input[2])
                } else ParkingLot.noLot()
                "leave" -> if (lotCreated) {
                    ParkingLot.leave(input[1].toInt() - 1)
                } else ParkingLot.noLot()
                "status" -> if (lotCreated) {
                    ParkingLot.status(numOfSpots)
                } else ParkingLot.noLot()
                "reg_by_color" -> if (lotCreated) {
                    ParkingLot.sort("reg", "color", numOfSpots, input[1])
                } else ParkingLot.noLot()
                "reg_by_spot" -> if (lotCreated) {
                    ParkingLot.spotReg(input[1].toInt())
                } else ParkingLot.noLot()
                "spot_by_color" -> if (lotCreated) {
                    ParkingLot.sort("spot", "color", numOfSpots, input[1])
                } else ParkingLot.noLot()
                "spot_by_reg" -> if (lotCreated) {
                    ParkingLot.sort("spot", "registration", numOfSpots, input[1])
                } else ParkingLot.noLot()
                "exit" -> break
                else -> println("I do not understand.")
            }
        } catch (e: NumberFormatException) {
            println("Value must be a integer.")
        }
    } while (action != "exit")
}