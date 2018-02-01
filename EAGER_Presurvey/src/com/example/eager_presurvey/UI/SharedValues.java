package com.example.eager_presurvey.UI;

public class SharedValues {
	public static boolean imageClass[]={true, true, true, true, true};
	public static void reset(){
		for (int i=0; i<imageClass.length; i++)
			imageClass[i]=true;
	}
}
