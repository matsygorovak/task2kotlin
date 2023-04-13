import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Test {
    @Test
    fun testMain1() {
        main("-d -l 1 -o test src/file1.txt".split(" ").toTypedArray())
        assertEquals("aaaaa", File("test1").readText())
        assertEquals("bbbbb", File("test2").readText())
        assertEquals("ccccc", File("test3").readText())
    }

    @Test
    fun testMain2() {
        main("-c 6 -o otvet src/file1.txt".split(" ").toTypedArray())
        assertEquals("aaaaa\n", File("otvetaa").readText())
        assertEquals("bbbbb\n", File("otvetab").readText())
        assertEquals("ccccc", File("otvetac").readText())
    }

    @Test
    fun testMain3() {
        main("-d -n 2 src/file2.txt".split(" ").toTypedArray())
        assertEquals("qwertyui\noplkjh", File("x1").readText())
        assertEquals("gf\ndsazxcvb\nnm", File("x2").readText())
    }

    @Test
    fun testError1() {
        assertFailsWith(
            exceptionClass = IOException::class,
            message = "Одновременно указано несколько флагов управления размером")
        {Split().run(true, 1,3,4, "test","src/file1.txt")}
    }

    @Test
    fun testError2() {
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            message = "Имя файла указано неверно")
        {Split().run(false, 4,null,null, "x","error")}
    }

    @Test
    fun testSplitChars() {
        val split = Split()
        assertEquals(
            expected = listOf("aaaa", "a\nbb", "bbb\n", "cccc", "c"),
            actual = split.chars(4, "src/file1.txt")
        )
    }

    @Test
    fun testSplitLines() {
        val split = Split()
        assertEquals(
            expected = listOf(
                "qwertyui\noplkjhgf", "dsazxcvb\nnm"
            ), actual = split.lines(2, "src/file2.txt")
        )
    }

    @Test
    fun testSplitCount() {
        val split = Split()
        assertEquals(
            expected = listOf(
                "qwertyui\no",
                "plkjhgf\nds", "azxcvb\nnm"
            ), actual = split.count(3, "src/file2.txt")
        )
    }

    @Test
    fun testNameOfOutFiles() {
        val split = Split()
        assertEquals(
            expected = "x", actual = split.nameOfOutFiles("src/file2.txt", "")
        )
    }
}
