package com.example.student.ui.state

import androidx.compose.runtime.Stable

/**
 * A generic class that holds a value with its loading status.
 */
@Stable
sealed class UiState<out T> {
    object Idle : UiState<Nothing>() {
        override fun toString() = "Idle"
    }
    
    object Loading : UiState<Nothing>() {
        override fun toString() = "Loading"
    }
    
    data class Success<out T>(val data: T) : UiState<T>() {
        override fun toString() = "Success"
    }
    
    data class Error(val message: String? = null, val throwable: Throwable? = null) : UiState<Nothing>() {
        override fun toString() = "Error: $message"
    }
    
    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success<*>
    val isError: Boolean get() = this is Error
    
    fun dataOrNull(): T? = (this as? Success)?.data
    
    fun errorMessageOrNull(): String? = (this as? Error)?.message
    
    fun throwableOrNull(): Throwable? = (this as? Error)?.throwable
    
    companion object {
        fun <T> success(data: T): UiState<T> = Success(data)
        fun error(message: String? = null, throwable: Throwable? = null): UiState<Nothing> = 
            Error(message, throwable)
    }
}

/**
 * A wrapper class that holds paginated data with its loading and error states.
 */
@Stable
data class PagedData<T>(
    val items: List<T> = emptyList(),
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val totalItems: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val endReached: Boolean = false
) {
    val isFirstPage: Boolean get() = currentPage == 0
    val hasMore: Boolean get() = currentPage < totalPages - 1
    val isEmpty: Boolean get() = items.isEmpty() && !isLoading && !isRefreshing
    
    fun appendItems(newItems: List<T>): PagedData<T> {
        return copy(
            items = items + newItems,
            currentPage = currentPage + 1,
            isLoading = false,
            error = null
        )
    }
    
    fun refresh(newItems: List<T>): PagedData<T> {
        return copy(
            items = newItems,
            currentPage = 0,
            isLoading = false,
            isRefreshing = false,
            error = null,
            endReached = false
        )
    }
    
    fun setLoading(loading: Boolean): PagedData<T> {
        return copy(isLoading = loading, error = null)
    }
    
    fun setRefreshing(refreshing: Boolean): PagedData<T> {
        return copy(isRefreshing = refreshing, error = null)
    }
    
    fun setError(message: String?): PagedData<T> {
        return copy(error = message, isLoading = false, isRefreshing = false)
    }
    
    fun setEndReached(reached: Boolean): PagedData<T> {
        return copy(endReached = reached, isLoading = false)
    }
    
    companion object {
        fun <T> initial(): PagedData<T> = PagedData()
    }
}

/**
 * A wrapper for handling form state and validation.
 */
@Stable
class FormState<T>(
    initialData: T,
    private val validate: (T) -> List<Pair<String, String>> = { emptyList() }
) {
    private var _data: T = initialData
    var data: T
        get() = _data
        set(value) {
            _data = value
            _errors = validate(value)
        }
    
    private var _errors: List<Pair<String, String>> = validate(initialData)
    val errors: List<Pair<String, String>>
        get() = _errors
    
    val hasErrors: Boolean
        get() = _errors.isNotEmpty()
    
    fun getErrorForField(field: String): String? {
        return _errors.find { it.first == field }?.second
    }
    
    fun validate(): Boolean {
        _errors = validate(_data)
        return _errors.isEmpty()
    }
    
    fun update(updateFn: T.() -> T) {
        _data = _data.updateFn()
        _errors = validate(_data)
    }
    
    fun setFieldValue(field: String, value: Any) {
        try {
            val fieldToUpdate = _data!!::class.java.getDeclaredField(field)
            fieldToUpdate.isAccessible = true
            fieldToUpdate.set(_data, value)
            _errors = validate(_data)
        } catch (e: Exception) {
            // Handle error
        }
    }
}
