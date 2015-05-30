package chess.pieces;


public class EmptyPiece extends AbstractChessPiece {

	public char getSymbol() {
		return ' ';
	}

	public boolean canAttackPosition(int column, int row) {
		return false;
	}

}
