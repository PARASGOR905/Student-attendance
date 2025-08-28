package com.example.student.data.repository

import com.example.student.data.dao.StudentDao
import com.example.student.data.entity.Student
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val studentDao: StudentDao
) : BaseRepository<Student, Long> {

    override suspend fun insert(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    override suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    override suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun deleteById(id: Long) {
        studentDao.deleteStudentById(id)
    }

    override suspend fun getById(id: Long): Student? {
        return studentDao.getStudentById(id)
    }

    suspend fun getByStudentId(studentId: String): Student? {
        return studentDao.getStudentByStudentId(studentId)
    }

    override fun getAll(): Flow<List<Student>> {
        return studentDao.getAllStudents()
    }

    fun searchStudents(query: String): Flow<List<Student>> {
        return studentDao.searchStudents(query)
    }

    suspend fun getStudentCount(): Int {
        return studentDao.getStudentCount()
    }

    suspend fun studentExists(studentId: String): Boolean {
        return studentDao.getStudentByStudentId(studentId) != null
    }
}
