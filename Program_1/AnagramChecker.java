/*
 * Jordan Leach
 * 1/24/17
 */
public class AnagramChecker {

	/*
	 * char2int was provided
	 */
	private static int char2int(char c){
		return (int)c-(int)'a';
	}

	
	
	//assume input strings are LOWER CASE
	//fine to add any additional helper functions (do in this file)
	//your solution must run in O(n+m) time (50% of points)
	public static boolean areAnagrams(String a, String b){
		/*
	         * The code below is mine 
	         */
		String word1 = a.replaceAll("\\s", "");
		String word2 = b.replaceAll("\\s", "");
		
		boolean correct = true;
		
		if(word1.length() != word2.length())
		{
			correct = false;
		}
		else
		{
			char[] word1Array = word1.toCharArray();
			
			for (char c : word1Array)
			{
				int index = word2.indexOf(c);
				
				if (index != -1)
				{
					word2 = word2.substring(0, index) + 
							word2.substring(index + 1, word2.length());
				}
				else 
				{
					correct = false;
					break;
				}
			}
		}
		return correct;
		
	}


}


