package chess;

import chess.pieces.AbstractChessPiece;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;


public class Main {
	public static void main(String[] args) {
		ChessBoard chessBoard = new ChessBoard(7, 7, new AbstractChessPiece[] {new King(), new King(), new Queen(), new Queen(), new Bishop(), new Bishop(), new Knight()});
	
		long start = System.currentTimeMillis();
		Results results = chessBoard.solve();
		long end = System.currentTimeMillis();
		
		System.out.println("Found " + results.getNumAttempts() + " combinations in " + (end - start) + " ms");
		System.out.println("First "+(results.getNumAttempts() > 10 ? 10 : results.getNumAttempts())+" boards:");
		System.out.println();
		results.printLayouts(System.out);
	}

}
