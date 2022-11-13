package danilovl.parameterbundle.util

class StringUtil {
    companion object {
        fun removeQuotationMark(text: String): String {
            return text.replace("\"", "").replace("'", "")
        }
    }
}
