package danilovl.parameterbundle.meta

import com.intellij.util.xml.DomElement

interface ParameterDomElement : DomElement {
    fun getValue(): String?
}
