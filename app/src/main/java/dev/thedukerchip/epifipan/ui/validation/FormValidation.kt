package dev.thedukerchip.epifipan.ui.validation


typealias Predicate = (value: String) -> Boolean

typealias ValueResolver = () -> String

class ValidationRule(val errorMessage: String, val ruleChecker: Predicate)

class FieldValidator(private val valueResolver: ValueResolver) {
    private val validationRules = mutableListOf<ValidationRule>()

    fun validate(): String? {
        validationRules.forEach { rule ->
            if (!rule.ruleChecker(valueResolver())) {
                return rule.errorMessage
            }
        }

        return null
    }

    fun addRule(errorMessage: String, predicate: Predicate): FieldValidator {
        validationRules.add(ValidationRule(errorMessage, predicate))
        return this
    }
}

class FormResolver(private val validators: List<FieldValidator>) {
    fun validate(): String? {
        validators.forEach { validator ->
            val error = validator.validate()
            if (error != null) {
                return error
            }
        }

        return null
    }
}
