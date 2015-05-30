package chess;

import java.util.HashMap;
import chess.pieces.AbstractChessPiece;
import chess.pieces.EmptyPiece;

/**
 * The positions are not identifies by column and row, but by an offset from 
 * row zero and column zero to width and height.
 * 
 */
public class ChessBoardLayout implements Cloneable {
	
	public static final AbstractChessPiece EMPTY_PIECE = new EmptyPiece();
	public static final int EMPTY_OFFSET = -1;
	private int width;
	private int boardLength;
	private int completionAttempt;
	private int hash;
	
	/**
	 * Maps an offset to a chess piece in the board.
	 * Offset 0 => column=0, row=0; Offset 1 => column=1, row=0
	 */
	private HashMap<Integer, AbstractChessPiece> pieceOffsets;

	public ChessBoardLayout(int width, int height) {

		this.width = width;
		this.boardLength = width * height;
		this.completionAttempt = 0;
		this.hash = 0;
		
		this.pieceOffsets = new HashMap<Integer, AbstractChessPiece>(boardLength);
	}
	
	private ChessBoardLayout(int width, int boardLength, int completionAttempt, HashMap<Integer, AbstractChessPiece> pieceOffsets) {
		this.width = width;
		this.boardLength = boardLength;
		this.completionAttempt = completionAttempt;
		this.hash = 0;
		this.pieceOffsets = (HashMap<Integer, AbstractChessPiece>) pieceOffsets.clone();
	}
	
	public int getBoardLength() {
		return boardLength;
	}
		
	public int getCompletionAttempt() {
		return completionAttempt;
	}

	public void setCompletionAttempt(int completionAttempt) {
		this.completionAttempt = completionAttempt;
	}

	/**
	 * Place a chess piece on the board at position column, row.
	 * Does not verify whether the piece can be attacked can attack others.
	 */
	public void putAbstractChessPieceAtPosition(AbstractChessPiece piece, int column, int row) {
		piece.setColumn(column);
		piece.setRow(row);
		pieceOffsets.put(row * width + column, piece);
	}

	/**
	 */
	public void placeAbstractChessPieceAtPosition(AbstractChessPiece piece, int offset) {
		int column = offset % width;
		int row = offset / width;
		putAbstractChessPieceAtPosition(piece, column, row);
	}

	public void removeAbstractChessPiece(AbstractChessPiece piece) {
		int offset = piece.getColumn() + width * piece.getRow();
		if(pieceOffsets.remove(Integer.valueOf(offset)) == null)
			throw new IllegalArgumentException("Could not find piece in this layout");
	}
	
	public int putPieceInNextAvailablePosition(AbstractChessPiece piece) {
		return putPieceInNextAvailablePosition(piece, 0);
	}

	/**
	 * Place the specified chess piece in the next position where it cannot attack or be attacked by other existing pieces. 
	 */
	public int putPieceInNextAvailablePosition(AbstractChessPiece piece, int startingOffset) {
		
		for(int offset = startingOffset; offset < boardLength; offset++)
			if(pieceOffsets.get(Integer.valueOf(offset)) == null) {
				int column = offset % width;
				int row = offset / width;
				if(canPutPieceAtPosition(piece, column, row)) {
					putAbstractChessPieceAtPosition(piece, column, row);
					return offset;
				}
			}
		return EMPTY_OFFSET;
	}

	private boolean canPutPieceAtPosition(AbstractChessPiece piece, int column, int row) {
		
		for(AbstractChessPiece laidPiece : pieceOffsets.values()) {
			if(laidPiece.canAttackPosition(column, row))
				return false;
			piece.setColumn(column);
			piece.setRow(row);
			if(piece.canAttackPosition(laidPiece.getColumn(), laidPiece.getRow()))
				return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
        int h = hash;
        if (h == 0 && pieceOffsets.size() > 0) {

            for (int i=0; i<boardLength; i++) {
            	AbstractChessPiece abstractChessPiece = pieceOffsets.get(Integer.valueOf(i));
    			if(abstractChessPiece == null)
    				abstractChessPiece = EMPTY_PIECE;
                h = 31 * h + (int) abstractChessPiece.getSymbol();
            }
            hash = h;
        }
        return h;
	}
	
	public String getColumnsText() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<boardLength; i++) {
			AbstractChessPiece AbstractChessPiece = pieceOffsets.get(Integer.valueOf(i));
			if(AbstractChessPiece == null)
				AbstractChessPiece = EMPTY_PIECE;
			sb.append(AbstractChessPiece.getSymbol());
		}
		return sb.toString();
	}
	
	public String getLayoutText() {
		StringBuilder sb = new StringBuilder();

		int height = boardLength / width;
		int offset = 0;
		for(int r = 0; r < height ; r++) {
			for(int c = 0; c < width; c++) {
				AbstractChessPiece chessPiece = pieceOffsets.get(Integer.valueOf(offset++));
				if(chessPiece == null)
					chessPiece = EMPTY_PIECE;
				sb.append(chessPiece.getSymbol() != ' ' ? chessPiece.getSymbol() : '-');
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	public void printBoard() {
		System.out.println(getLayoutText());
	}
	
	@Override
	public ChessBoardLayout clone() {
		ChessBoardLayout clone = new ChessBoardLayout(width, boardLength, completionAttempt, pieceOffsets);
		return clone;
	}
}
