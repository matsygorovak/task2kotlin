import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Test {
    @Test
    fun testMain1() {
        main("-d -l 1 -o test testFiles/file1.txt".split(" ").toTypedArray())
        assertEquals("aaaaa", File("test1").readText())
        assertEquals("bbbbb", File("test2").readText())
        assertEquals("ccccc", File("test3").readText())
    }

    @Test
    fun testMain2() {
        main("-c 6 -o otvet testFiles/file1.txt".split(" ").toTypedArray())
        assertEquals("aaaaa\n", File("otvetaa").readText())
        assertEquals("bbbbb\n", File("otvetab").readText())
        assertEquals("ccccc", File("otvetac").readText())
    }

    @Test
    fun testMain3() {
        main("-d -n 2 -o r testFiles/file2.txt".split(" ").toTypedArray())
        assertEquals("qwertyui\noplkjh", File("r1").readText())
        assertEquals("gf\ndsazxcvb\nnm", File("r2").readText())
    }

    @Test
    fun testSplit1() {
        Split(
            false, 100, null,
            2, "out", "testFiles/file3.txt"
        ).run()
        assertEquals("rr\n", File("outaa").readText())
        assertEquals("rr", File("outab").readText())
    }

    @Test
    fun testSplit2() {
        Split(
            true, 1, null,
            null, "r", "testFiles/file1.txt"
        ).run()
        assertEquals("aaaaa", File("r1").readText())
        assertEquals("bbbbb", File("r2").readText())
        assertEquals("ccccc", File("r3").readText())
    }

    @Test
    fun testError1() {
        assertFailsWith(
            exceptionClass = IOException::class,
            message = "Одновременно указано несколько флагов управления размером")
        {Split(true, 1,3,4, "test","testFiles/file1.txt").run()}
    }

    @Test
    fun testError2() {
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            message = "Имя файла указано неверно")
        {Split(false, 4,null,null, "x","error").run()}
    }
}
