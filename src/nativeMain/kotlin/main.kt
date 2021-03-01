import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import platform.posix.fflush
import platform.posix.fprintf
import kotlin.system.exitProcess

fun main() = readLine()
    .let(::parseInput)
    .let(::process)
    .let(::println)

fun parseInput(s: String?) = s
    .let { it ?: exit(1, "an date should be passed as stdin") }
    .let {
        Regex("^(\\d{4})(\\d{2})(\\d{2})$")
            .matchEntire(it)
            ?.let { match ->
                LocalDate(
                    match.groups[1]!!.value.toInt(10),
                    match.groups[2]!!.value.toInt(10),
                    match.groups[3]!!.value.toInt(10)
                )
            }
            ?: try {
                LocalDate.parse(it)
            } catch (e: IllegalArgumentException) {
                null
            }
            ?: exit(2, "illegal date format, should be formatted as YYYYMMDD or YYYY-MM-DD")
    }

fun process(date: LocalDate) = "${date}\t${isWeekEnd(date).toInt()}\t${isPublicHolidays(date).toInt()}"

fun Boolean.toInt() = if (this) 1 else 0

val STDERR = platform.posix.fdopen(2, "w")
fun exit(code: Int, msg: String): Nothing {
    fprintf(STDERR, "$msg\n")
    fflush(STDERR)
    exitProcess(code)
}


private fun isWeekEnd(date: LocalDate) =
    date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY


private fun isPublicHolidays(date: LocalDate) = isPublicHolidaysFixed(date) ||
        getEasterDayOfYear(date.year).let { easterDayOfYear ->
            date.dayOfYear.let { it == easterDayOfYear || it == easterDayOfYear + 1 || it == easterDayOfYear + 39 || it == easterDayOfYear + 49 || it == easterDayOfYear + 50 }
        }

private fun isPublicHolidaysFixed(date: LocalDate) =
    (date.month == Month.JANUARY && date.dayOfMonth == 1) ||
            (date.month == Month.MAY && date.dayOfMonth == 1) ||
            (date.month == Month.MAY && date.dayOfMonth == 8) ||
            (date.month == Month.JULY && date.dayOfMonth == 14) ||
            (date.month == Month.AUGUST && date.dayOfMonth == 15) ||
            (date.month == Month.NOVEMBER && date.dayOfMonth == 1) ||
            (date.month == Month.NOVEMBER && date.dayOfMonth == 11) ||
            (date.month == Month.DECEMBER && date.dayOfMonth == 25)

/**
 * impl of https://en.wikipedia.org/wiki/Computus#Anonymous_Gregorian_algorithm
 */
fun getEasterDayOfYear(year: Int): Int {
    val a = year % 19
    val b = year / 100
    val c = year % 100
    val d = b / 4
    val e = b % 4
    val f = (b + 8) / 25
    val g = (b - f + 1) / 3
    val h = (19 * a + b - d - g + 15) % 30
    val i = c / 4
    val k = c % 4
    val l = (32 + 2 * e + 2 * i - h - k) % 7
    val m = (a + 11 * h + 22 * l) / 451
    val n = h + l - 7 * m + 114
    val month = n / 31
    val dayOfMonth = (n % 31) + 1
    return LocalDate(year, month, dayOfMonth).dayOfYear
}
