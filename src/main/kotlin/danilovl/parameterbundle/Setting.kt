package danilovl.parameterbundle

class Setting {
    companion object {
        const val PARAMETER_SERVICE_INTERFACE_NAMESPACE = "\\Danilovl\\ParameterBundle\\Interfaces\\ParameterServiceInterface"
        const val DEV_DEBUG_CONTAINER_XML_PATH = "/var/cache/dev/App_KernelDevDebugContainer.xml"

        val TWIG_FUNCTION_NAMES = arrayOf(
            "parameter_get",
            "parameter_get_string",
            "parameter_get_int",
            "parameter_get_float",
            "parameter_get_boolean",
            "parameter_get_array",
            "parameter_has"
        )
    }
}
