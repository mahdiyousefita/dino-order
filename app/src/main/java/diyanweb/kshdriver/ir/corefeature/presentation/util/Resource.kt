package diyanweb.kshdriver.ir.corefeature.presentation.util

import diyanweb.kshdriver.ir.corefeature.domain.model.StringResourceContent

/**
 * A sealed class representing the different states of a resource.
 *
 * @param R The type of the encapsulated data.
 */
sealed class Resource<out R> {
    /**
     * Represents a successful state of a resource with the encapsulated [data].
     *
     * @param T The type of the encapsulated data.
     * @property data The encapsulated data.
     */
    class Success<out T>(val data: T) : Resource<T>()

    /**
     * Represents an error state of a resource with an [message] and optional [data].
     *
     * @param T The type of the encapsulated data.
     * @property message The error message.
     * @property data The encapsulated data associated with the error state.
     */
    class Error<out T>(
        val message: StringResourceContent,
        val data: T? = null
    ) : Resource<T>()

    /**
     * Represents a loading state of a resource with an optional [data].
     *
     * @param T The type of the encapsulated data.
     * @property data The encapsulated data associated with the loading state.
     */
    class Loading<out T>(val data: T? = null) : Resource<T>()
}