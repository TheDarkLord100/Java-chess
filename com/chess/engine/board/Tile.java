package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import java.util.*;

public abstract class Tile{
    protected final int tileCoordinates;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinates, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinates, piece) : EMPTY_TILES.get(tileCoordinates);
    }

    private Tile(final int tileCoordinates){
        this.tileCoordinates = tileCoordinates;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public int getTileCoordinates(){
        return this.tileCoordinates;
    }

    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinates){
            super(coordinates);
        }

        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }

        @Override
        public String toString(){
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;
        private OccupiedTile(int coordinates, final Piece pieceOnTile){
            super(coordinates);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }

        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
}