package danilovl.parameterbundle.meta

import com.intellij.util.xml.DomElement

interface RootDomElement : DomElement {
    fun getParameters(): ParametersDomElement?
}
