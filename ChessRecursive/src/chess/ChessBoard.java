package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chess.pieces.AbstractChessPiece;

public class ChessBoard {
	
	private int width;
	private int height;
	private AbstractChessPiece[] pieces;

	public ChessBoard(int width, int height, AbstractChessPiece[] pieces) {
		this.width = width;
		this.height = height;
		this.pieces = pieces;
	}

	/**
	 * Find all unique configurations of the set of chess pieces on this chess board
	 */
	public Results solve() {

		Results results = new Results();
		
		// get a list of all the unique permutations based on the chess pieces available
		Set<List<AbstractChessPiece>> permutations = new HashSet<List<AbstractChessPiece>>();
		buildChessPiecePermutations(permutations, new ArrayList<AbstractChessPiece>(), new ArrayList<AbstractChessPiece>(Arrays.asList(pieces)));
		
		for(List<AbstractChessPiece> permutation : permutations) {
			
				ChessBoardLayout chessBoardLayout = new ChessBoardLayout(width, height);			
				putPieceOnBoard(results, chessBoardLayout, permutation, 0, 0);
		}

		return results;
	}

	/**
	 * Tries to place a piece on all possible positions of the chess board
	 */
	private boolean putPieceOnBoard(Results results, ChessBoardLayout chessBoardLayout, List<AbstractChessPiece> piecesToPlace, int pieceIndex, int startOffset) {
		if(pieceIndex == piecesToPlace.size()) {
			// no more chess pieces to place. store the result
			results.addLayout(chessBoardLayout);
			return true;
		}
		else {
			
			AbstractChessPiece chessPiece = piecesToPlace.get(pieceIndex);
			int offset = startOffset;
			while(offset < chessBoardLayout.getBoardLength()) {
				
				int placedOffset = chessBoardLayout.putPieceInNextAvailablePosition(chessPiece, offset);
				if( placedOffset == ChessBoardLayout.EMPTY_OFFSET ) 
					break;
				else {
					// try possible combinations using the remaining pieces, in the offsets ahead of this one.
					putPieceOnBoard(results, chessBoardLayout.clone(), piecesToPlace, pieceIndex + 1, placedOffset + 1);
					chessBoardLayout.removeAbstractChessPiece(chessPiece);
					offset = placedOffset + 1;
				}
			}
			return false;
		}
	}
	
	/**
	 * Find permutations of a list of chess pieces using a recursive method.
	 */
	private void buildChessPiecePermutations(Set<List<AbstractChessPiece>> permutations, List<AbstractChessPiece> collect, List<AbstractChessPiece> distrib) {
		int n = distrib.size();
		if(n == 0)
			permutations.add(new ArrayList<AbstractChessPiece>(collect));
		else {
			for(int i = 0; i < n; i++) {
				List<AbstractChessPiece> nestedCollect = new ArrayList<AbstractChessPiece>(collect);
				List<AbstractChessPiece> nestedDistrib = new ArrayList<AbstractChessPiece>(distrib);
				nestedCollect.add(nestedDistrib.remove(i));
				buildChessPiecePermutations(permutations, nestedCollect, nestedDistrib);
			}
		}
	}
}