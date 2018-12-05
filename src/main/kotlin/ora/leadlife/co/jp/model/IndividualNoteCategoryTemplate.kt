package ora.leadlife.co.jp.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="individual_note_category_templates")
data class IndividualNoteCategoryTemplate(
        @Id
        @GeneratedValue
        var id:Long = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        @Column(name = "code" , length = 254)
        var code: String = "",

        @Column(name = "name" , length = 254)
        var name: String = "",

        @Column(name = "url" , length = 254)
        var url: String = "",

        @Column(name = "banner_url" , length = 254)
        var bannerUrl: String = "",

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "individualNoteCategoryTemplate")
        var templateList: List<IndividualNoteCategory>? = null

        ) {
    override fun hashCode(): Int {
        return id.hashCode()
    }
}