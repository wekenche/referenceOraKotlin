package ora.leadlife.co.jp.schedule

import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.PaidRegistrationService
import ora.leadlife.co.jp.util.DateUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentTasks {
    val logger: Logger = LoggerFactory.getLogger(PaymentTasks::class.java)

    @Autowired
    lateinit var paidRegistrationService: PaidRegistrationService

    @Autowired
    lateinit var accountService: AccountService

    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Tokyo") // 毎月1日の0時に実行
    fun rePaymentProcess() {
        logger.info("rePaymentProcess Start")

        // 課金定期処理
        val lastDayOfLastMonthCal = Calendar.getInstance() // 先月の月末日
        lastDayOfLastMonthCal.add(Calendar.MONTH, -1)
        lastDayOfLastMonthCal.set(Calendar.DATE, lastDayOfLastMonthCal.getActualMaximum(Calendar.DATE))
        for (account in accountService.findTargetRePayAccounts(lastDayOfLastMonthCal)) {
            paidRegistrationService.rePay(account)
        }

        // フリーアカウントの情報を削除
        var startDate = Calendar.getInstance() // 先々月の1日
        startDate.set(Calendar.DATE, 1)
        startDate.add(Calendar.MONTH, -2)
        startDate = DateUtil.clearTime(startDate)
        var endDate = Calendar.getInstance() // 先々月の(月末日 - 1)
        endDate.set(Calendar.DATE, 1)
        endDate.add(Calendar.MONTH, -2)
        endDate.set(Calendar.DATE, endDate.getActualMaximum(Calendar.DATE) - 1)
        endDate = DateUtil.clearTime(endDate)
        for (account in accountService.findTargetDeleteAccounts(startDate.time, endDate.time)) {
            accountService.makeFree(account)
        }

        logger.info("rePaymentProcess End")
    }
}
