package com.example.student.util

object Constants {
    // Database
    const val DATABASE_NAME = "student_database"
    const val STUDENTS_TABLE = "students"
    const val ATTENDANCE_TABLE = "attendance_records"

    // Shared Preferences
    const val PREF_NAME = "student_prefs"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_USER_ROLE = "user_role"

    // Roles
    const val ROLE_ADMIN = "admin"
    const val ROLE_TEACHER = "teacher"
    const val ROLE_STUDENT = "student"

    // Request Codes
    const val REQUEST_CODE_ADD_STUDENT = 1001
    const val REQUEST_CODE_EDIT_STUDENT = 1002
    const val REQUEST_CODE_TAKE_PHOTO = 1003
    const val REQUEST_CODE_PICK_IMAGE = 1004

    // Result Codes
    const val RESULT_ADDED = 1
    const val RESULT_UPDATED = 2
    const val RESULT_DELETED = 3
    const val RESULT_CANCELLED = 0

    // Intent Extras
    const val EXTRA_STUDENT_ID = "extra_student_id"
    const val EXTRA_STUDENT = "extra_student"
    const val EXTRA_ATTENDANCE_DATE = "extra_attendance_date"
    const val EXTRA_IS_EDIT = "extra_is_edit"

    // Date Formats
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val TIME_FORMAT = "HH:mm"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DISPLAY_DATE_FORMAT = "EEEE, MMMM d, yyyy"

    // Animation Durations
    const val ANIM_DURATION_SHORT = 200L
    const val ANIM_DURATION_MEDIUM = 300L
    const val ANIM_DURATION_LONG = 500L

    // Validation
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_NAME_LENGTH = 50
    const val MAX_EMAIL_LENGTH = 100
    const val MAX_PHONE_LENGTH = 20
    const val MAX_STUDENT_ID_LENGTH = 20

    // Pagination
    const val ITEMS_PER_PAGE = 20

    // File Provider
    const val FILE_PROVIDER_AUTHORITY = "com.example.student.fileprovider"
    
    // Notification Channels
    const val CHANNEL_ID_REMINDERS = "reminders_channel"
    const val CHANNEL_NAME_REMINDERS = "Reminders"
    
    // Notification IDs
    const val NOTIFICATION_ID_ATTENDANCE_REMINDER = 1001
    
    // WorkManager Tags
    const val WORK_TAG_SYNC = "sync_work"
    const val WORK_TAG_BACKUP = "backup_work"
    
    // Export/Import
    const val EXPORT_FILE_NAME = "student_attendance_export"
    const val EXPORT_FILE_EXTENSION = ".json"
    const val MIME_TYPE_JSON = "application/json"
    
    // Deep Links
    const val DEEP_LINK_BASE = "https://student.example.com"
    const val DEEP_LINK_STUDENT = "$DEEP_LINK_BASE/student"
    const val DEEP_LINK_ATTENDANCE = "$DEEP_LINK_BASE/attendance"
}
