package com.example.student.util

import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns
import java.util.regex.Pattern

object ValidationUtils {

    // Common patterns
    private val NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]+")
    private val STUDENT_ID_PATTERN = Pattern.compile("^[A-Za-z0-9-]+")
    private val PHONE_PATTERN = Pattern.compile("^[0-9+()\\s-]+")
    
    // Input filters
    val NAME_FILTER = InputFilter { source, start, end, dest, dstart, dend ->
        if (source.isBlank()) return@InputFilter ""
        
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]
            if (Character.isLetter(c) || c == ' ' || c == '-' || c == '\'') {
                builder.append(c)
            }
        }
        builder.toString()
    }
    
    val STUDENT_ID_FILTER = InputFilter { source, start, end, dest, dstart, dend ->
        if (source.isBlank()) return@InputFilter ""
        
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]
            if (Character.isLetterOrDigit(c) || c == '-') {
                builder.append(c)
            }
        }
        builder.toString()
    }
    
    val PHONE_FILTER = InputFilter { source, start, end, dest, dstart, dend ->
        if (source.isBlank()) return@InputFilter ""
        
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]
            if (Character.isDigit(c) || "+()- ".contains(c)) {
                builder.append(c)
            }
        }
        builder.toString()
    }
    
    // Validation functions
    fun isValidName(name: String?): Boolean {
        if (name.isNullOrBlank()) return false
        return NAME_PATTERN.matcher(name).matches() && name.length in 2..50
    }
    
    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPhone(phone: String?): Boolean {
        if (phone.isNullOrBlank()) return false
        return PHONE_PATTERN.matcher(phone).matches() && phone.length in 7..20
    }
    
    fun isValidStudentId(studentId: String?): Boolean {
        if (studentId.isNullOrBlank()) return false
        return STUDENT_ID_PATTERN.matcher(studentId).matches() && studentId.length in 3..20
    }
    
    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrBlank()) return false
        return password.length >= 6
    }
    
    fun isPasswordMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
    
    // Formatting functions
    fun formatPhoneNumber(phone: String): String {
        if (phone.isBlank()) return ""
        
        val digits = phone.replace("[^0-9]".toRegex(), "")
        return when (digits.length) {
            in 0..3 -> digits
            in 4..6 -> "${digits.substring(0, 3)}-${digits.substring(3)}"
            else -> "${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6, minOf(10, digits.length))}"
        }
    }
    
    // Input length filters
    fun getMaxLengthFilter(maxLength: Int): InputFilter {
        return InputFilter.LengthFilter(maxLength)
    }
    
    fun getAlphabeticFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            if (source.isBlank()) return@InputFilter ""
            source.filter { it.isLetter() || it == ' ' }
        }
    }
    
    fun getNumericFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            if (source.isBlank()) return@InputFilter ""
            source.filter { it.isDigit() }
        }
    }
    
    // Input validation with error messages
    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )
    
    fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult(false, "Name is required")
            !NAME_PATTERN.matcher(name).matches() -> ValidationResult(false, "Invalid name format")
            name.length < 2 -> ValidationResult(false, "Name is too short")
            name.length > 50 -> ValidationResult(false, "Name is too long")
            else -> ValidationResult(true)
        }
    }
    
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, "Invalid email format")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isBlank() -> ValidationResult(false, "Phone number is required")
            !PHONE_PATTERN.matcher(phone).matches() -> ValidationResult(false, "Invalid phone number format")
            phone.length < 7 -> ValidationResult(false, "Phone number is too short")
            phone.length > 20 -> ValidationResult(false, "Phone number is too long")
            else -> ValidationResult(true)
        }
    }
    
    fun validateStudentId(studentId: String): ValidationResult {
        return when {
            studentId.isBlank() -> ValidationResult(false, "Student ID is required")
            !STUDENT_ID_PATTERN.matcher(studentId).matches() -> ValidationResult(false, "Invalid student ID format")
            studentId.length < 3 -> ValidationResult(false, "Student ID is too short")
            studentId.length > 20 -> ValidationResult(false, "Student ID is too long")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "Password is required")
            password.length < 6 -> ValidationResult(false, "Password must be at least 6 characters")
            else -> ValidationResult(true)
        }
    }
}
