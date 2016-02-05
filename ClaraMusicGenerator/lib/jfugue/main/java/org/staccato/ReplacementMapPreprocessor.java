/*
 * JFugue, an Application Programming Interface (API) for Music Programming
 * http://www.jfugue.org
 *
 * Copyright (C) 2003-2014 David Koelle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.staccato;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacementMapPreprocessor implements Preprocessor 
{
	private static ReplacementMapPreprocessor instance;
	
	private ReplacementMapPreprocessor() { } 
	
	public static ReplacementMapPreprocessor getInstance() {
		if (instance == null) {
			instance = new ReplacementMapPreprocessor();
		}
		return instance;
	}

	private static java.util.regex.Pattern replacementPatternWithBrackets = Pattern.compile("<\\S+>");
	private static java.util.regex.Pattern replacementPatternWithoutBrackets = Pattern.compile("\\S+");
	private Map<String, String> map;
	private boolean requiresAngleBrackets = true;
	private int iterations = 1;
	
	public void setRequireAngleBrackets(boolean require) {
		this.requiresAngleBrackets = require;
	}
	
	public boolean requiresAngleBrackets() {
		return this.requiresAngleBrackets;
	}
	
	public void setReplacementMap(Map<String, String> map) {
		this.map = map;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	public int getIterations() {
		return this.iterations;
	}
	
	private java.util.regex.Pattern getReplacementPattern() {
		return requiresAngleBrackets() ? replacementPatternWithBrackets : replacementPatternWithoutBrackets;
	}
	
	@Override
	public String preprocess(String s, StaccatoParserContext context) {
		String iteratingString = s;
		for (int i=0; i < iterations; i++) {
			StringBuilder buddy = new StringBuilder();
			int posPrev = 0;
		
			Matcher m = getReplacementPattern().matcher(iteratingString);
			while (m.find()) {
				String foundKey = requiresAngleBrackets() ? m.group().substring(1, m.group().length()-1) : m.group();
				buddy.append(iteratingString.substring(posPrev, m.start()));
				String replacementValue = map.get(foundKey);
				if (replacementValue != null) {
					buddy.append(map.get(foundKey));
				} else {
					buddy.append(foundKey); // If the key doesn't have a value, just put the key back - it might be intended for another parser or purpose
				}
				posPrev = m.end();
			}
	
			buddy.append(iteratingString.substring(posPrev, iteratingString.length()));
			iteratingString = buddy.toString();
		}
		return iteratingString;
	}
}
