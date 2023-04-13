import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.IOException

class Parser {

    @Option(name = "-d", usage = "the output files should be named “ofile1, ofile2, ofile3...“")
    private var nameOfFiles: Boolean = false

    @Option(name = "-l", usage = "size of output files in lines")
    private var sizeInLines: Int = 100

    @Option(name = "-c", usage = "size of output files in chars")
    private var sizeInChars: Int? = null

    @Option(name = "-n", usage = "count of output files")
    private var countOfFiles: Int? = null

    @Option(name = "-o", usage = "sets the base name of the output file")
    private var nameOfOutFiles: String? = null

    @Argument(metaVar = "InputFile", usage = "Input File")
    private var inputFile: String? = null

    fun parseArgs(args: Array<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(args.toList())
            val split = Split()
            split.run(nameOfFiles, sizeInLines, sizeInChars, countOfFiles, nameOfOutFiles, inputFile)
        } catch (e: CmdLineException) {
            System.err.println(e.message)
            parser.printUsage(System.err)
        } catch (e: IllegalArgumentException) {
            System.err.println(e.message)
        } catch (e: IOException) {
            System.err.println(e.message)
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }
}

