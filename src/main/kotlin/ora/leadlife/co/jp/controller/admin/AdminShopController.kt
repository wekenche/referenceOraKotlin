package ora.leadlife.co.jp.controller.admin

import ora.leadlife.co.jp.form.ShopForm
import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.PaymentService
import ora.leadlife.co.jp.service.ShopService
import ora.leadlife.co.jp.service.UserService
import ora.leadlife.co.jp.util.PageWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/admin/shop")
class AdminShopController {
    @Autowired
    lateinit var shopService: ShopService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var paymentService: PaymentService

    @GetMapping
    fun index(model: Model, pageable: Pageable, shopForm: ShopForm): String {
        val page = PageWrapper(shopService.getAll(pageable), "/admin/shop")
        val shopList : ArrayList<ShopForm> = ArrayList()
        page.content.forEach {
            val code = if(it.parent == null) "" else { it.parent!!.code }
            val codeId = if(it.parent == null) null else { it.parent!!.id!! }

            shopList.add(ShopForm(
                    shopId = it.id!!,
                    shopName = it.name,
                    shopCode = it.code!!,
                    shopParent = code,
                    shopBackPercent = it.backPercent,
                    shopBackParentPercent = it.backParentPercent,
                    shopParentId = codeId,
                    unpaid = paymentService.unpaid(it),
                    created = it.createdAt,
                    updated = it.updatedAt))
        }
        model.addAttribute("shops", shopList)
        model.addAttribute("shopsDropDown", shopService.findAll())
        model.addAttribute("page", page)
        model.addAttribute("navPage", "shop")

        return "admin/shop/index"
    }

    @GetMapping("/search")
    fun search(model: Model, pageable: Pageable,
               @RequestParam(value = "name" , required = false) name : String?,
               @RequestParam(value = "code" , required = false) code : String?,
               @RequestParam(value = "back_percent" , required = false) back_percent : String?,
               @RequestParam(value = "back_parent_percent" , required = false) back_parent_percent : String?): String {
        val page = PageWrapper(shopService.findByParams(pageable,name,code,back_percent,back_parent_percent), "/admin/shop")
        val shopList : ArrayList<ShopForm> = ArrayList()
        page.content.forEach {
            val coded = if(it.parent == null) "" else { it.parent!!.code }
            val codeId = if(it.parent == null) null else { it.parent!!.id!! }
            shopList.add(ShopForm(shopId = it.id!!, shopName = it.name, shopCode = it.code!!, shopParent = coded,
                    shopBackPercent = it.backPercent, shopBackParentPercent = it.backParentPercent, shopParentId = codeId,
                    created = it.createdAt, updated = it.updatedAt))
        }
        model.addAttribute("shops", shopList)
        model.addAttribute("page", page)
        model.addAttribute("shopForm", ShopForm())
        model.addAttribute("shopsDropDown", shopService.findAll())
        model.addAttribute("search_name", name)
        model.addAttribute("search_code", code)
        model.addAttribute("search_back_percent", back_percent)
        model.addAttribute("search_back_parent_percent", back_parent_percent)

        return "admin/shop/index"
    }

    @PostMapping("/edit")
    fun edit(model: Model, shopForm: ShopForm, @AuthenticationPrincipal user: User): String {
        val parent = shopService.findById(shopForm.shopParentId!!)
        val shop = Shop(id = shopForm.shopId, name = shopForm.shopName, code = shopForm.shopCode, parentId = shopForm.shopParentId, parent = parent,
                backPercent = shopForm.shopBackPercent, backParentPercent = shopForm.shopBackParentPercent)
        shopService.save(shop)
        return "redirect:/admin/shop/"
    }

    @PostMapping("add")
    fun add(model: Model, shopForm: ShopForm): String {
        val parent : Shop? = if(!shopForm.shopParentId!!.equals("0"))
            {shopService.findById(shopForm.shopParentId!!)}
            else {null}
        shopService.save(
            Shop(
                name = shopForm.shopName,
                code = shopForm.shopCode,
                parent = parent,
                backPercent = shopForm.shopBackPercent,
                backParentPercent = shopForm.shopBackParentPercent
            )
        )

        return "redirect:/admin/shop/"
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    fun delete(@PathVariable id: String): MutableMap<String, String>? {
        val shop = shopService.findById(id.toLong())!!
        userService.findByShop(shop).forEach {
            userService.updateUserShop(null,it.id!!)
        }
        shopService.findByParent(shop).forEach {
            shopService.updateParentShop(null,it.id!!)
        }
        shopService.delete(id.toLong())
        return Collections.singletonMap("result", "ok")
    }

    /**
     * 一時的に支払情報を作る
     */
    @GetMapping("/payment/{id}")
    fun payment(@PathVariable id: String): String {
        paymentService.makeShopPayHistoryById(id.toLong())
        return "redirect:/admin/shop"
    }

    @GetMapping("/pay/{id}")
    fun pay(@PathVariable id: String): String {
        paymentService.shopPay(id.toLong())
        return "redirect:/admin/shop"
    }
}