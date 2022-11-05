package danilovl.parameterbundle.meta

import com.intellij.util.xml.DomElement

interface ParametersDomElement : DomElement {
    fun getParameters(): List<ParameterDomElement?>?
}
