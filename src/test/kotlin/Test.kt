import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class Test {

    @Test
    fun testMain() {
        main("-d -o test src/file1.txt".split(" ").toTypedArray())
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

    @Test
    fun testRun() {
        val split = Split()
        try {
            split.run(
                true, 100, 9, null, "-", "src/file1.txt"
            )
        } catch(e: java.lang.Exception) {
            assertTrue(e is IllegalAccessException)
        }
        assertEquals(File("src/file1.txt1").readText(), "aaaaa\r\nbbb")
        assertEquals(File("src/file1.txt2").readText(), "bb\r\nccccc")
    }
}
