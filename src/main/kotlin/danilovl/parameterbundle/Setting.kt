package danilovl.parameterbundle

class Setting {
    companion object {
        const val PARAMETER_SERVICE_INTERFACE_NAMESPACE = "\\Danilovl\\ParameterBundle\\Interfaces\\ParameterServiceInterface"
        const val DEV_DEBUG_CONTAINER_XML_PATH = "/var/cache/dev/App_KernelDevDebugContainer.xml"
        const val CONFIG_DIR = "/config"
        const val YAML_EXTENSION = "yaml"
        const val PARAMETERS_KEY = "parameters"
        const val BUNDLE_CONFIGURATION_KEY = "danilovl_parameter.delimiter"
        const val DEFAULT_PARAMETERS_KEY_DELIMITER = "."
        var PARAMETERS_KEY_DELIMITER = DEFAULT_PARAMETERS_KEY_DELIMITER

        val TWIG_FUNCTION_NAMES = arrayOf(
            "parameter_get",
            "parameter_get_string",
            "parameter_get_int",
            "parameter_get_float",
            "parameter_get_boolean",
            "parameter_get_array",
            "parameter_has"
        )

        fun setDelimiter(delimiter: String) {
            this.PARAMETERS_KEY_DELIMITER = delimiter
        }
    }
}
