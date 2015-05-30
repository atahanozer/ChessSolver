package chess.pieces;


public class King extends AbstractChessPiece {


	public char getSymbol() {
		return 'K';
	}

	public boolean canAttackPosition(int column, int row) {
		if(Math.abs(getRow() - row) == 1 && Math.abs(getColumn() - column) == 1)
			return true;
		if( (getRow() == row && Math.abs(getColumn() - column) == 1) || (getColumn() == column && Math.abs(getRow() - row) == 1) )
			return true;
		return false;
	}

}
