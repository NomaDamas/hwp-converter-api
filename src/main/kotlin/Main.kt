import io.javalin.Javalin
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod
import java.io.File

fun main(args: Array<String>) {
    val app = Javalin.create().start(7000)

    app.post("/upload") { ctx ->
        println("connected")
        val file = ctx.uploadedFile("file") ?: throw IllegalArgumentException("No file uploaded")
        val savedFilepath = "sample_hwp/${file.filename()}"
        val savedFile = File(savedFilepath)
        file.content().copyTo(savedFile.outputStream())

        if (file.filename().endsWith(".hwp")) {
            val hwpFile = readHwp(savedFile.path)
            val hwpText = hwpFile?.let {
                val option = ctx.queryParam("option") ?: "main-only"
                val textExtractMethod = if (option == "all") {
                    TextExtractMethod.InsertControlTextBetweenParagraphText
                } else {
                    TextExtractMethod.OnlyMainParagraph
                }
                extractTextHwp(it, textExtractMethod)
            }

            hwpText?.let {
                removeFile(savedFilepath)
                ctx.contentType("text/plain")
                ctx.result(it)
            } ?: {
                ctx.status(400)
                ctx.result("extract file failed")
            }
        } else if (file.filename().endsWith(".hwpx")) {
//            val hwpxFile = readHwpx(savedFile.path)
//            val hwpxText = hwpxFile?.let {
//                extractTextHwpx(it)
//            }
//
//            hwpxText?.let {
//                removeFile(savedFilepath)
//                ctx.contentType("text/plain")
//                ctx.result(it)
//            } ?: {
//                ctx.status(400)
//                ctx.result("extract file failed")
//            }
            ctx.status(404)
            ctx.result("hwpx file does not support yet because of error at hwpxlib")
        } else {
            ctx.status(404)
            ctx.result("please upload hwp file")
        }
    }
}

private fun removeFile(filepath: String) {
    val file = File(filepath)
    if (file.exists()) {
        file.delete()
    }
}
