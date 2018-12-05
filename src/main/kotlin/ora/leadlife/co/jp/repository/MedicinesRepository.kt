package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Medicines
import org.springframework.data.repository.CrudRepository

interface MedicinesRepository: CrudRepository<Medicines,Long> {
    fun findByAccount(account : Account): List<Medicines>
    fun deleteByAccount(account : Account)
}