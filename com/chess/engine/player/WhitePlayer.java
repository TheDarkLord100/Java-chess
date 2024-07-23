package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public class WhitePlayer extends Player {

    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves,
            Collection<Move> blackStandardLegalMoves) {
                super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }
    
    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()) {
            // White king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Piece kingRook = this.board.getTile(63).getPiece();
                if(kingRook != null && kingRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                    kingRook.getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingRook, kingRook.getPiecePosition(), 61));
                    }
            }
            // White queen side castle
            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
                final Piece queenRook = this.board.getTile(56).getPiece();
                if(queenRook != null && queenRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(57, opponentsLegals).isEmpty() &&
                    queenRook.getPieceType().isRook()) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenRook, queenRook.getPiecePosition(), 59));
                    }
            }
        }

        return kingCastles;
    }
}
