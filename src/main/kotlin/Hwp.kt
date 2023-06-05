import kr.dogfoot.hwplib.`object`.HWPFile
import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor

fun readHwp(filepath: String): HWPFile? {
    val hwpFile: HWPFile = HWPReader.fromFile(filepath)
    return if (hwpFile.bodyText.sectionList.size > 0) {
        println("$filepath read success!")
        hwpFile
    } else {
        println("$filepath read fail!")
        null
    }
}

fun extractTextHwp(hwpFile: HWPFile): String {
    return TextExtractor.extract(hwpFile, TextExtractMethod.InsertControlTextBetweenParagraphText)
}
