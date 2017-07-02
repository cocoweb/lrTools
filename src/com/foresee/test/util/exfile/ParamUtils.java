package com.foresee.test.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;


public class ParamUtils {

	public static String parse(String input) {
		input = quoteReplacement(input).replace("\n", "").replace("\r", "");
		input = StringUtils.substringAfter(input, "parse(");
		return StringUtils.substringBeforeLast(input, ")");
	}
	public static String eval(String input, Map<String, Object> objectMap) {
		if (StringUtils.isBlank(input) || !input.contains("##")) {
			return input;
		}
		Pattern pattern = Pattern.compile("parse\\u0028[\\s\\S]+?\\u0029");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			String toReplace = matcher.group();
			input = input.replaceFirst(replaceSpecial(toReplace), Matcher.quoteReplacement((parse(toReplace))));
		}
		pattern = Pattern.compile("##([\\s\\S]*?)##");
		matcher = pattern.matcher(input);
		Interpreter i = new Interpreter();
		try {
			if (objectMap != null) {
				for (Entry<String, Object> entry : objectMap.entrySet()) {
					i.set(entry.getKey(), entry.getValue());
				}
			}
			while (matcher.find()) {
				String toEval = matcher.group(1);
				String value = null;

				Object result = i.eval(toEval);
				if (result == null) {
					return "";
				}
				value = result.toString();

				toEval = replaceSpecial(toEval);
				input = input.replaceFirst("##" + toEval + "##", value);
				System.out.println("Eval result:" + input);
			}
		} catch (EvalError e) {
			System.out.println(input);
		}
		return input;
	}

	private static String quoteReplacement(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\') {
				sb.append('\\');
				sb.append('\\');
			} else if (c == '"') {
				sb.append('\\');
				sb.append('"');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static String parse1(String input) {
		input = input.replace("{", "L_BLANKET");
		input = input.replace("}", "R_BLANKET");
		input = StringUtils.substringAfter(input, "parse(");
		return StringUtils.substringBeforeLast(input, ")");
	}

	private static String restoreParse1(String input) {
		if (input == null) {
			return input;
		}
		input = input.replace("L_BLANKET", "{");
		input = input.replace("R_BLANKET", "}");
		return input;
	}

	public static String eval(String input) {
		return eval(input, null);
	}

	public static String replaceVariable(String input, String paramName, String value) {
		input = restoreHtmlSpecial(input);
		input = input.replaceAll("\\u0024\\u007B" + paramName + "}", value);
		return input.replaceAll("\\u0024\\u0028" + paramName + "\\u0029", value);
	}

	public static String replaceWithDefault(String input) {
		// if (input == null) {
		// return input;
		// }
		// Pattern pattern = Pattern.compile("parse\\u0028[\\s\\S]+?\\u0029");
		// Matcher matcher = pattern.matcher(input);
		// while (matcher.find()) {
		// String toReplace = matcher.group();
		// input = input.replaceFirst(replaceSpecial(toReplace),
		// parse1(toReplace));
		// }
		// input = restoreHtmlSpecial(input);
		// pattern = Pattern.compile("\\u0024\\u007B([\\s\\S]*?)}");
		// matcher = pattern.matcher(input);
		// while (matcher.find()) {
		// String match = matcher.group(1);
		// String defaultValue = StringUtils.substringAfter(match, ",");
		// if (StringUtils.isBlank(defaultValue)) {
		// input = input.replaceFirst("\\u0024\\u007B" + replaceSpecial(match) +
		// "}", "");
		// } else {
		// input = input.replaceFirst("\\u0024\\u007B" + replaceSpecial(match) +
		// "}",
		// Matcher.quoteReplacement((defaultValue)));
		// }
		// }
		input = replaceWithDefault(input, "\\u0024\\u007B", "}");
		input = replaceWithDefault(input, "\\u0024\\u0028", "\\u0029");
		return restoreParse1(input);
	}

	private static String replaceWithDefault(String input, String left, String right) {
		if (input == null) {
			return input;
		}
		Pattern pattern = Pattern.compile("parse\\u0028[\\s\\S]+?\\u0029");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			String toReplace = matcher.group();
			input = input.replaceFirst(replaceSpecial(toReplace), parse1(toReplace));
		}
		input = restoreHtmlSpecial(input);
		pattern = Pattern.compile(left + "([\\s\\S]*?)" + right);
		matcher = pattern.matcher(input);
		while (matcher.find()) {
			String match = matcher.group(1);
			String defaultValue = StringUtils.substringAfter(match, ",");
			if (StringUtils.isBlank(defaultValue)) {
				input = input.replaceFirst(left + replaceSpecial(match) + right, "");
			} else {
				input = input.replaceFirst(left + replaceSpecial(match) + right,
						Matcher.quoteReplacement((defaultValue)));
			}
		}
		return restoreParse1(input);
	}

	public static String replaceVariables(String input, Map<String, String> variableMap, boolean useDefault) {
		input = replaceVariables(input, variableMap, useDefault, "\\u0024\\u007B", "}");
		return replaceVariables(input, variableMap, useDefault, "\\u0024\\u0028", "\\u0029");
	}

	private static String replaceVariables(String input, Map<String, String> variableMap, boolean useDefault,
			String left, String right) {
		if (StringUtils.isBlank(input)) {
			return input;
		}
		input = restoreHtmlSpecial(input);
		Pattern pattern = Pattern.compile(left + "([\\s\\S]*?)" + right);
		Matcher matcher = pattern.matcher(input);
		Map<String, String> map = new HashMap<String, String>();
		while (matcher.find()) {
			String match = matcher.group(1);
			String[] pair = match.split(",");
			String name = pair[0];
			String vObject = variableMap.get(name);
			if (vObject != null) {
				map.put(match, vObject);
			} else if (useDefault) {
				if (pair.length > 1) {
					String defaultValue = StringUtils.substringAfter(match, ",");
					map.put(match, defaultValue);
				}
			}
		}

		for (Entry<String, String> entry : map.entrySet()) {
			String value = Matcher.quoteReplacement(entry.getValue());
			input = input.replaceAll(left + replaceSpecial(entry.getKey()) + right, value);
		}
		return input;
	}

	public static String replaceVariables(String input, Map<String, String> variableMap) {
		return replaceVariables(input, variableMap, true);
	}

	public static String replaceSpecial(String input) {
		input = input.replace("\\", "\\u005C").replace(".", "\\u002E").replace("$", "\\u0024").replace("^", "\\u005E")
				.replace("{", "\\u007B").replace("[", "\\u005B").replace("(", "\\u0028").replace("|", "\\u007C")
				.replace(")", "\\u0029").replace("*", "\\u002A").replace("+", "\\u002B").replace("?", "\\u003F");
		return input;
	}

	public static String restoreHtmlSpecial(String input) {
		return input.replace("&amp;", "&").replace("&quot;", "\"");
	}

	public static void main(String[] args) {
		String s = "wefwf'sizes1' =>wfwef";
		System.out.println(s.replaceAll("'sizes[0-9]+' =>", ""));
	}
}
