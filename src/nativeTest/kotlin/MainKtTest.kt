import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals


@Test
fun `process a saturday`() {
    assertEquals("1\t0", process(LocalDate(2020, 9, 12)))
}

@Test
fun `process a sunday`() {
    assertEquals("1\t0", process(LocalDate(2020, 9, 13)))
}

@Test
fun `process a monday`() {
    assertEquals("0\t0", process(LocalDate(2020, 9, 14)))
}

@Test
fun `process new year day`() {
    assertEquals("0\t1", process(LocalDate(2020, 1, 1)))
}

@Test
fun `process easter`() {
    assertEquals("0\t1", process(LocalDate(2020, 4, 13)))
}

@Test
fun `process may the 1st`() {
    assertEquals("0\t1", process(LocalDate(2020, 5, 1)))
}

@Test
fun `process may the 8th`() {
    assertEquals("0\t1", process(LocalDate(2020, 5, 8)))
}

@Test
fun `process ascension day`() {
    assertEquals("0\t1", process(LocalDate(2020, 5, 21)))
}

@Test
fun `process pentecost day`() {
    assertEquals("0\t1", process(LocalDate(2020, 6, 1)))
}

@Test
fun `process july the 14th`() {
    assertEquals("0\t1", process(LocalDate(2020, 7, 14)))
}

@Test
fun `process assumption day`() {
    assertEquals("1\t1", process(LocalDate(2020, 8, 15)))
}

@Test
fun `process toussaint`() {
    assertEquals("1\t1", process(LocalDate(2020, 11, 1)))
}

@Test
fun `process november the 11th`() {
    assertEquals("0\t1", process(LocalDate(2020, 11, 11)))
}

@Test
fun `process Xmas day`() {
    assertEquals("0\t1", process(LocalDate(2020, 12, 25)))
}

@Test
fun dayOfYear() {
    assertEquals(1, LocalDate.parse("2020-01-01").dayOfYear)
}

@Test
fun getEasterDayOfYear2020() {
    assertEquals(LocalDate(2020, 4, 12).dayOfYear, getEasterDayOfYear(2020))
}

@Test
fun getEasterDayOfYear2019() {
    assertEquals(LocalDate(2019, 4, 21).dayOfYear, getEasterDayOfYear(2019))
}

@Test
fun getEasterDayOfYear2018() {
    assertEquals(LocalDate(2018, 4, 1).dayOfYear, getEasterDayOfYear(2018))
}

@Test
fun getEasterDayOfYear2016() {
    assertEquals(LocalDate(2016, 3, 27).dayOfYear, getEasterDayOfYear(2016))
}
