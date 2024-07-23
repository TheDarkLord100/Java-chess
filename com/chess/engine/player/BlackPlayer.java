package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public class BlackPlayer extends Player{

    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves,
            Collection<Move> blackStandardLegalMoves) {
                super(board, blackStandardLegalMoves, whiteStandardLegalMoves);   
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
    
    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()) {
            // Black king side castle
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Piece kingRook = this.board.getTile(7).getPiece();
                if(kingRook != null && kingRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
                    kingRook.getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingRook, kingRook.getPiecePosition(), 5));
                    }
            }
            // Black queen side castle
            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
                final Piece queenRook = this.board.getTile(0).getPiece();
                if(queenRook != null && queenRook.isFirstMove() &&
                    Player.calculateAttacksOnTile(1, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty() &&
                    queenRook.getPieceType().isRook()) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenRook, queenRook.getPiecePosition(), 3));
                    }
            }
        }

        return kingCastles;
    }
}
