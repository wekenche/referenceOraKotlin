package ora.leadlife.co.jp.form

data class FileUploadForm (
        var filePath:String? = "",
        var fileName:String? = "",
        var fileType:String? = "",
        var fileSize:Long? = 0
)