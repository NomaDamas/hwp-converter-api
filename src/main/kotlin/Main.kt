import io.javalin.Javalin
import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor
import java.io.File

fun main(args: Array<String>) {
    val app = Javalin.create().start(7000)

    app.post("/upload") { ctx ->
        val file = ctx.uploadedFile("file") ?: throw IllegalArgumentException("No file uploaded")
        val savedFilepath = "sample_hwp/${file.filename()}"
        val savedFile = File(savedFilepath)
        file.content().copyTo(savedFile.outputStream())

        val hwpFile = readHwp(savedFile.path)
        val hwpText = hwpFile?.let {
            extractTextHwp(it)
        }

        hwpText?.let {
            removeFile(savedFilepath)
            ctx.contentType("text/plain")
            ctx.result(it)
        } ?: {
            ctx.status(400)
            ctx.result("extract file failed")
        }
    }
}

private fun readHwp(filepath: String): HWPFile? {
    val hwpFile: HWPFile = HWPReader.fromFile(filepath)
    return if (hwpFile.bodyText.sectionList.size > 0) {
        println("$filepath read success!")
        hwpFile
    } else {
        println("$filepath read fail!")
        null
    }
}

private fun extractTextHwp(hwpFile: HWPFile): String {
    return TextExtractor.extract(hwpFile, TextExtractMethod.InsertControlTextBetweenParagraphText)
}

private fun removeFile(filepath: String) {
    val file = File(filepath)
    if (file.exists()) {
        file.delete()
    }
}
