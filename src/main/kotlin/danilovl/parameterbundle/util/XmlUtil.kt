package danilovl.parameterbundle.util

import com.intellij.psi.xml.XmlTag

class XmlUtil {
    companion object {
        fun recursiveXml(xmlTag: XmlTag, actualKey: String? = null): HashMap<String, String> {
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
                    parameters = parameters.plus(recursiveXml(subTag, key)) as HashMap<String, String>
                }

                return parameters
            }

            if (actualKey !== null) {
                key = "$actualKey.$key"
            }

            parameters[key] = value

            return parameters
        }
    }
}