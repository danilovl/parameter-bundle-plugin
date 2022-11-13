package danilovl.parameterbundle.util

import com.intellij.psi.xml.XmlTag

class XmlUtil {
    companion object {
        fun parseXmlTag(xmlTag: XmlTag, actualKey: String? = null): HashMap<String, String> {
            var parameters = HashMap<String, String>()
            var key = xmlTag.getAttribute("key")?.value
            val value = xmlTag.value.text

            if (key === null) {
                if (actualKey !== null && !value.isEmpty()) {
                    parameters[actualKey] = "[]"
                }

                return parameters
            }

            if (value.isEmpty()) {
                return parameters
            }

            val type = xmlTag.getAttribute("type")?.value
            if (type.equals("collection")) {
                if (actualKey !== null) {
                    key = "$actualKey.$key"
                }

                val subTags = xmlTag.subTags
                subTags.forEach { subTag: XmlTag ->
                    parameters = parameters.plus(parseXmlTag(subTag, key)) as HashMap<String, String>
                }

                return parameters
            }

            if (actualKey !== null) {
                key = "$actualKey.$key"
            }

            parameters[key] = value

            return parameters
        }

        fun parseXmlTagWithOnlyValue(xmlTag: XmlTag, actualKey: String? = null): HashMap<String, XmlTag> {
            var parameters = HashMap<String, XmlTag>()
            var key = xmlTag.getAttribute("key")?.value
            val value = xmlTag.value.text

            if (key === null || value.isEmpty()) {
                return parameters
            }

            val type = xmlTag.getAttribute("type")?.value
            if (type.equals("collection")) {
                if (actualKey !== null) {
                    key = "$actualKey.$key"
                }

                val subTags = xmlTag.subTags
                val firstSubTag = subTags.first()

                val firstSubTagKey = firstSubTag.getAttribute("key")?.value
                if (firstSubTagKey === null) {
                    parameters[key] = xmlTag

                    return parameters
                }

                subTags.forEach { subTag: XmlTag ->
                    parameters = parameters.plus(parseXmlTagWithOnlyValue(subTag, key)) as HashMap<String, XmlTag>
                }

                return parameters
            }

            if (actualKey !== null) {
                key = "$actualKey.$key"
            }

            parameters[key] = xmlTag

            return parameters
        }
    }
}
