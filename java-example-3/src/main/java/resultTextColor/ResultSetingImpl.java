package resultTextColor;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

public class ResultSetingImpl implements ResultSeting{

	@Override
	public String setResultColor(String result, List<String> words) {
		
		// TODO Auto-generated method stub
		for (String str:words) {
			String replacement="<font color=\"red\">"+str+"</font>";
			result=result.replace(str, replacement);
			result="<html><font size=\"5\">"+result+"</font></html>";
		}
		return result;
	}

}
