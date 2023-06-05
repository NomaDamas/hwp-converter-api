import kr.dogfoot.hwpxlib.`object`.HWPXFile
import kr.dogfoot.hwpxlib.reader.HWPXReader
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractMethod
import kr.dogfoot.hwpxlib.tool.textextractor.TextExtractor

fun readHwpx(filepath: String): HWPXFile? {
    return HWPXReader.fromFilepath(filepath)
}

fun extractTextHwpx(hwpxFile: HWPXFile): String {
    return TextExtractor.extract(
        hwpxFile, TextExtractMethod.InsertControlTextBetweenParagraphText,
        null, false, null
    )
}
