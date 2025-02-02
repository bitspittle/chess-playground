package io.github.opletter.chesspg.chessbackend.delegate

import io.github.opletter.chesspg.chessbackend.Board
import io.github.opletter.chesspg.chessbackend.Move
import io.github.opletter.chesspg.chessbackend.Piece
import io.github.opletter.chesspg.chessbackend.Square
import kotlin.math.abs

internal class EnPassantDelegate(
    enPassantSquare: Square?,
) {

    var enPassantSquare: Square? = enPassantSquare
        private set

    fun isEnPassantSquare(square: Square): Boolean {
        return square == enPassantSquare
    }

    fun update(
        board: Board,
        move: Move,
        color: Piece.Color,
    ) {
        enPassantSquare = when (move) {
            is Move.CastleMove -> null
            is Move.PromotionMove -> null
            is Move.GeneralMove -> {
                if (move.piece == Piece.Type.Pawn && abs(move.fromRank!! - move.toRank) == 2) {
                    val leftPiece = runCatching {
                        Square(
                            file = move.toFile - 1, rank = move.toRank
                        )
                    }.getOrNull()?.let { board.get(it) }
                    val rightPiece = runCatching {
                        Square(
                            file = move.toFile + 1, rank = move.toRank
                        )
                    }.getOrNull()?.let { board.get(it) }

                    val enPassantAvailable =
                        (leftPiece != null && leftPiece.type == Piece.Type.Pawn && leftPiece.color == color.invert()) || (rightPiece != null && rightPiece.type == Piece.Type.Pawn && rightPiece.color == color.invert())

                    if (enPassantAvailable) {
                        Square(file = move.fromFile!!, rank = (move.fromRank + move.toRank) / 2)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
        }
    }
}