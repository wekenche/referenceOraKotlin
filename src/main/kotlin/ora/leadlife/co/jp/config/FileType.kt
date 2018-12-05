package ora.leadlife.co.jp.config

enum class FileType(val value: String) {
    PICTURE("写真"),
    MOVIE("ビデオ"),
    DATA("データ(PDF)"),
    NONE("テキストのみ")
}