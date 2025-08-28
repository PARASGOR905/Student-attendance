package com.example.student.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.student.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val appName = context.applicationInfo.loadLabel(context.packageManager).toString()

    /**
     * Create a file in the app's external files directory
     */
    fun createFileInAppDir(
        fileName: String,
        subDir: String? = null,
        mimeType: String = "application/octet-stream"
    ): File {
        val dir = if (subDir != null) {
            File(context.getExternalFilesDir(null), subDir).apply { mkdirs() }
        } else {
            context.getExternalFilesDir(null)!!
        }
        
        return File(dir, fileName).apply {
            if (exists()) delete()
            createNewFile()
        }
    }

    /**
     * Create an image file in the public Pictures directory
     */
    fun createImageFile(prefix: String = "IMG_"): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${prefix}${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    /**
     * Get a content URI for a file using FileProvider
     */
    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            file
        )
    }

    /**
     * Save content to a file in the app's private storage
     */
    fun saveToInternalStorage(fileName: String, content: String): File {
        return File(context.filesDir, fileName).apply {
            writeText(content)
        }
    }

    /**
     * Read content from a file in the app's private storage
     */
    fun readFromInternalStorage(fileName: String): String? {
        return try {
            File(context.filesDir, fileName).readText()
        } catch (e: IOException) {
            null
        }
    }

    /**
     * Delete a file from the app's private storage
     */
    fun deleteFileFromInternalStorage(fileName: String): Boolean {
        return try {
            File(context.filesDir, fileName).delete()
        } catch (e: IOException) {
            false
        }
    }

    /**
     * Save content to the public Downloads directory
     */
    fun saveToDownloads(fileName: String, mimeType: String, content: String): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let { contentUri ->
                resolver.openOutputStream(contentUri)?.use { outputStream ->
                    outputStream.write(content.toByteArray())
                }
            }
            uri
        } else {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            file.writeText(content)
            Uri.fromFile(file)
        }
    }

    /**
     * Get the app's cache directory
     */
    fun getCacheDir(subDir: String? = null): File {
        return if (subDir != null) {
            File(context.cacheDir, subDir).apply { mkdirs() }
        } else {
            context.cacheDir
        }
    }

    /**
     * Clear the app's cache
     */
    fun clearCache() {
        context.cacheDir.deleteRecursively()
    }

    /**
     * Get the size of the app's cache
     */
    fun getCacheSize(): Long {
        return getDirSize(context.cacheDir)
    }

    /**
     * Get the size of a directory in bytes
     */
    private fun getDirSize(dir: File): Long {
        var size = 0L
        dir.walkTopDown()
            .filter { it.isFile }
            .forEach { size += it.length() }
        return size
    }

    /**
     * Format file size in a human-readable format
     */
    fun formatFileSize(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var size = bytes.toDouble()
        var unitIndex = 0
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        return "%.2f %s".format(size, units[unitIndex])
    }
}
