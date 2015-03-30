/**
 * 
 */
package org.exparity.stub.testutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stewart Bissett
 */
public class CollectionOfGenerics {

	private List<GenericType<String>> values = new ArrayList<>();

	public List<GenericType<String>> getValues() {
		return values;
	}

	public void setValues(List<GenericType<String>> values) {
		this.values = values;
	}
}
