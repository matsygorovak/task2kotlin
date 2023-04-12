import java.io.File

class Split() {
    fun run(
        nameOfFiles: Boolean,
        sizeInLines: Int?,
        sizeInChars: Int?,
        countOfFiles: Int?,
        nameOfOutFiles: String?,
        inputFile: String?
    ) {
        if (inputFile == null) {
            throw IllegalArgumentException()
        }
        val oFile = nameOfOutFiles(inputFile, nameOfOutFiles)
        val list = if (sizeInChars != null) {
            chars(sizeInChars!!, inputFile)
        } else if (countOfFiles != null) {
            count(countOfFiles!!, inputFile)
        } else {
            lines(sizeInLines!!, inputFile)
        }
        if (nameOfFiles) {
            for (i in 1..list.size + 1) {
                var file = File(oFile + "$i")
                file.createNewFile()
                file.writeText(list[i - 1])
            }
        } else {
            var s1 = ""
            var s2 = ""
            for (i in 0..list.size) {
                s1 = ('a' + i / 26).toString()
                s2 = ('a' + i % 26).toString()
                var file = File(oFile + "$s1$s2")
                file.createNewFile()
                file.writeText(list[i])
            }
        }
    }

    //    флаг -o
    fun nameOfOutFiles(inputFile: String?, outputFile: String?): String {
        if (outputFile!!.isEmpty()) return "x"
        if (outputFile == "-") return inputFile!!
        return outputFile!!
    }

    //    делим по линиям (флаг -l)
    fun lines(sizeInLines: Int, inputFile: String?): ArrayList<String> {
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
    fun chars(sizeInChars: Int, inputFile: String?): List<String> {
        val file = Regex("\r\n").replace(File(inputFile!!).readText(), "\n")
        return file.chunked(sizeInChars).toList()
    }

    //    делим по кол-ву файлов
    fun count(countOfFiles: Int, inputFile: String?): List<String> {
        val file = Regex("\r\n").replace(File(inputFile!!).readText(), "\n")
        var co = file.length / countOfFiles
        if (file.length % countOfFiles != 0) {
            co += 1
        }
        return chars(co, inputFile)
    }
}