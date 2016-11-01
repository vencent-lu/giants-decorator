package com.giants.decorator.html;

import java.io.Serializable;

public interface Theme extends Serializable {
	
	String getName();
	
	String getPath();
	
	boolean equals(Object obj);

	
}
