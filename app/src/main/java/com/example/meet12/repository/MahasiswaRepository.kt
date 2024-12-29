package com.example.meet12.repository

import com.example.meet12.model.Mahasiswa
import com.example.meet12.service.MahasiswaService

interface MahasiswaRepository {
    suspend fun getMahasiswa(): List<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun  deleteMahasiswa(nim: String)

    suspend fun  getMahasiswabyNim(nim: String): Mahasiswa
}

class MahasiswaKontakRepository(
    private val kontakApiService: MahasiswaService
):MahasiswaRepository{
    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        kontakApiService.insertMahasiswa(mahasiswa)

    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        kontakApiService.updateMahasiswa(nim, mahasiswa)
    }

    override suspend fun deleteMahasiswa(nim: String) {
        try{
            val response = kontakApiService.deleteMahasiswa(nim)
            if(!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP status code: " +
                        "${response.hashCode()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMahasiswa(): List<Mahasiswa> =
        kontakApiService.getAllMahasiswa()

    override suspend fun getMahasiswabyNim(nim: String): Mahasiswa {
        return kontakApiService.getMahasiswabyNim(nim)
    }

}