package chess.pieces;

public abstract class AbstractChessPiece {
	
	private int column = -1;
	private int row = -1;

	public AbstractChessPiece() {
	}
	
	public abstract char getSymbol();
	
	@Override
	public String toString() {
		return String.valueOf(getSymbol());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || ! AbstractChessPiece.class.isAssignableFrom(obj.getClass()))
			return false;
		return (getSymbol() == ((AbstractChessPiece)obj).getSymbol());
	}
	
	@Override
	public int hashCode() {
		return getSymbol();
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getRow() {
		return row;
	}

	public boolean canAttackPosition(int column, int row) {
		return false;
	}
}
