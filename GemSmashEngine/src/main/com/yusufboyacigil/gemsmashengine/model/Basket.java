package com.yusufboyacigil.gemsmashengine.model;

/**
 * Represents basket where gems collected in.
 * @author yboyacigil
 */
public class Basket {

	private int gem;
	private int numGems;
	
	public void setGem(int gem) {
		if (this.gem != 0 && this.numGems > 0) {
			throw new IllegalStateException("Gem already set for this basket (" + gem + ")");
		}
		this.gem = gem;
	}
	
	public int gem() {
		return this.gem;
	}
	
	public int numGems() {
		return this.numGems;
	}
	
	public boolean put(int gem, int c) {
		if (this.gem != gem) {
			return false;
		}
		numGems += c;
		return true;
	}
	
	public int take(int c) {
		if (numGems == 0) {
			return 0;
		}
		if (c > numGems) {
			int actualNumGems = numGems;
			numGems = 0;
			return actualNumGems;
		}
		numGems -= c;
		return c;
	}
	
	public boolean isEmpty() {
		return numGems == 0;
	}
	
	public boolean cannotPut(int gem) {
		return this.gem != gem;
	}

	@Override
	public String toString() {
		return "[g:" + gem + ", #:" + numGems + "]";
	}

}
