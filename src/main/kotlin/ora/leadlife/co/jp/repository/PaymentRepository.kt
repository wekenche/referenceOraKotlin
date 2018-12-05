package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Payment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PaymentRepository : CrudRepository<Payment, Long> {
    fun findFirstByAccountAndMstatusEqualsOrderByIdDesc(account: Account, mstatus: String = "success"): Payment?
    fun findFirstByAccountEqualsOrderByIdDesc(account: Account): Payment?
    fun findFirstByAppleTransactionId(appleTransactionId: Long): Payment?

    @Query(value = """
select *
from payments
where id in (
  select max(payments.id)
  from payments
    inner join accounts a on payments.account_id = a.id
  where apple_transaction_id is not null
    and a.is_premium=true
  group by account_id)
        """
            , nativeQuery = true)
    fun findLastOfApple(): List<Payment>
}