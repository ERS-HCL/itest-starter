package com.onefoundation.itests.model;

import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloTestData.
 */
public class HelloTestData {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HelloTestData [input=" + input + ", expected=" + expected + ", intVal=" + intVal + ", boolVal="
				+ boolVal + ", decVal=" + decVal + ", listVal=" + listValString() + "]";
	}

	/**
	 * Gets the int val.
	 *
	 * @return the int val
	 */
	public int getIntVal() {
		return intVal;
	}

	/**
	 * Sets the int val.
	 *
	 * @param intVal the new int val
	 */
	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	/**
	 * Checks if is bool val.
	 *
	 * @return true, if is bool val
	 */
	public boolean isBoolVal() {
		return boolVal;
	}

	/**
	 * Sets the bool val.
	 *
	 * @param boolVal the new bool val
	 */
	public void setBoolVal(boolean boolVal) {
		this.boolVal = boolVal;
	}

	/**
	 * Gets the dec val.
	 *
	 * @return the dec val
	 */
	public BigDecimal getDecVal() {
		return decVal;
	}

	/**
	 * Sets the dec val.
	 *
	 * @param decVal the new dec val
	 */
	public void setDecVal(BigDecimal decVal) {
		this.decVal = decVal;
	}

	/**
	 * Gets the list val.
	 *
	 * @return the list val
	 */
	public List<MoreData> getListVal() {
		return listVal;
	}

	/**
	 * Sets the list val.
	 *
	 * @param listVal the new list val
	 */
	public void setListVal(List<MoreData> listVal) {
		this.listVal = listVal;
	}

	/**
	 * List val string.
	 *
	 * @return the string
	 */
	public String listValString() {
		return (listVal != null)? listVal.toString() : null;
	}

	/** The input. */
	private String input;
	
	/** The expected. */
	private String expected;
	
	/** The int val. */
	private int intVal;
	
	/** The bool val. */
	private boolean boolVal;
	
	/** The dec val. */
	private BigDecimal decVal;
	
	/** The list val. */
	private List<MoreData> listVal;

	/**
	 * Gets the input.
	 *
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Sets the input.
	 *
	 * @param input the new input
	 */
	public void setInput(final String input) {
		this.input = input;
	}

	/**
	 * Gets the expected.
	 *
	 * @return the expected
	 */
	public String getExpected() {
		return expected;
	}

	/**
	 * Sets the expected.
	 *
	 * @param expected the new expected
	 */
	public void setExpected(final String expected) {
		this.expected = expected;
	}

	/**
	 * The Class MoreData.
	 */
	private class MoreData {
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MoreData [id=" + id + ", name=" + name + "]";
		}
		
		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/** The id. */
		private String id;
		
		/** The name. */
		private String name;
	}
}
