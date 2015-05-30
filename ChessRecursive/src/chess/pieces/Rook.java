package chess.pieces;



public class Rook extends AbstractChessPiece {

	public char getSymbol() {
		return 'R';
	}

	public boolean canAttackPosition(int column, int row) {
		if(getRow() == row || getColumn() == column)
			return true;
		return false;
	}

}
