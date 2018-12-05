package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.HospitalPetsForm
import ora.leadlife.co.jp.form.InsurancePetForm
import ora.leadlife.co.jp.form.PetsForm
import ora.leadlife.co.jp.form.SalonForPetsForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.ServicePets
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/pets")
class PetController {
    @Autowired
    lateinit var servicePets: ServicePets

    @Autowired lateinit var accountService: AccountService

    @GetMapping
    fun pets(): String {
        return "pets/index"
    }

    @GetMapping(path = arrayOf("/input"))
    fun input(model: Model , @AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "pets/input"
    }

    fun setPage(account:Account,model: Model, view : Int) {
        val petsForm = PetsForm()
        if(servicePets.findPetsByAccount(account).isNotEmpty()) {
            var list : ArrayList<PetsForm> = ArrayList()
            servicePets.findPetsByAccount(account).forEach {
                var p = PetsForm(name = it.name, isGender = it.gender, feed = it.feed, isExistsPedigreeForm = it.existsPedigreeForm, pedigreeFormNo = it.pedigreeFormNo)
                list.add(p)
            }
            petsForm.petsWrapper = list
        }
        if(servicePets.findHospiByAccount(account).isNotEmpty()) {
            var list : ArrayList<HospitalPetsForm> = ArrayList()
            servicePets.findHospiByAccount(account).forEach {
                var h = HospitalPetsForm(diseaseName = it.diseaseName, hospital = it.hospital, contactAddress = it.contactAddress)
                list.add(h)
            }
            petsForm.petsHospitalWrapper = list
        }
        if(servicePets.findInsuranceByAccount(account).isNotEmpty()) {
            var list : ArrayList<InsurancePetForm> = ArrayList()
            servicePets.findInsuranceByAccount(account).forEach {
                val i = InsurancePetForm(company = it.company, tel = it.tel, billingMethod = it.billingMethod, remarks = it.memo)
                list.add(i)
            }
            petsForm.petsInsuranceWrapper = list
        }
        if(servicePets.findSalonByAccount(account).isNotEmpty()) {
            val list : ArrayList<SalonForPetsForm> = ArrayList()
            servicePets.findSalonByAccount(account).forEach {
                var s = SalonForPetsForm(name = it.name, contactAddress = it.contactAddress)
                list.add(s)
            }
            petsForm.petsSalonWrapper = list
        }
        model.addAttribute("petsForm", petsForm)
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/save"))
    fun save(petsForm: PetsForm, @AuthenticationPrincipal user: User): String {
        servicePets.save(petsForm,user.lastUsedAccount!!)
        return "redirect:/pets/input"
    }
}