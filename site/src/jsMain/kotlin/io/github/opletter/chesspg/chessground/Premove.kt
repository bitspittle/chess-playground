package io.github.opletter.chesspg.chessground

@JsModule("chessground/premove")
@JsNonModule
external object Premove {
    fun premove(pieces: Pieces, key: String, canCastle: Boolean): Array<String>
}