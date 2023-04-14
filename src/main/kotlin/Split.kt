import java.io.File
import java.io.IOException

class Split(
    private val nameOfFiles: Boolean,
    private val sizeInLines: Int?,
    private val sizeInChars: Int?,
    private val countOfFiles: Int?,
    private val nameOfOutFiles: String?,
    private val inputFile: String?
) {
    fun run() {
        if (!File(inputFile!!).exists()) {
            throw IllegalArgumentException("Имя файла указано неверно")
        }
        if ((sizeInLines != null && sizeInChars != null && sizeInLines != 100)
            || (sizeInLines != null && countOfFiles != null && sizeInLines != 100)
            || (sizeInChars != null && countOfFiles != null)
        ) {
            throw IOException("Одновременно указано несколько флагов управления размером")
        }

        val oFile = nameOfOutFiles(inputFile, nameOfOutFiles)
        val list = if (sizeInChars != null) {
            chars(sizeInChars, inputFile)
        } else if (countOfFiles != null) {
            count(countOfFiles, inputFile)
        } else {
            lines(sizeInLines!!, inputFile)
        }
        if (nameOfFiles) {
            for (i in 1..list.size) {
                val file = File(oFile + "$i")
                file.createNewFile()
                file.writeText(list[i - 1])
            }
        } else {
            var s1 = ""
            var s2 = ""
            for (i in list.indices) {
                s1 = ('a' + i / 26).toString()
                s2 = ('a' + i % 26).toString()
                val file = File(oFile + "$s1$s2")
                file.createNewFile()
                file.writeText(list[i])
            }
        }
    }

    //    флаг -o
    private fun nameOfOutFiles(inputFile: String?, outputFile: String?): String {
        if (outputFile!!.isEmpty()) return "x"
        if (outputFile == "-") return inputFile!!
        return outputFile
    }

    //    делим по линиям (флаг -l)
    private fun lines(sizeInLines: Int, inputFile: String?): ArrayList<String> {
        var count = 0
        var string = ""
        val list = arrayListOf<String>()
        val file = File(inputFile!!).readLines()
        for (i in file) {
            count++
            string += i + "\n"
            if (count == sizeInLines || count == file.size) {
                string = string.dropLast(1)
                list.add(string)
                count = 0
                string = ""
            }
        }
        return list
    }

    //    делим по символам (флаг -c)
    private fun chars(sizeInChars: Int, inputFile: String?): List<String> {
        val file = Regex("\r\n").replace(File(inputFile!!).readText(), "\n")
        return file.chunked(sizeInChars).toList()
    }

    //    делим по кол-ву файлов
    private fun count(countOfFiles: Int, inputFile: String?): List<String> {
        val file = Regex("\r\n").replace(File(inputFile!!).readText(), "\n")
        var co = file.length / countOfFiles
        if (file.length % countOfFiles != 0) {
            co += 1
        }
        return chars(co, inputFile)
    }
}