import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

    }
}

class Solution {
    Map<String, Boolean> map = new HashMap<>(); // this is for optimization to eliminate redundant checks on the right side
    // of the passed string.  suppose we have the main string of 'aaaaab' and we have dictionary items [a,aa,aaa,]
    // we check a as first dictionary item in first method call in call stack and subsequently call others so its like
    // a ; aaaab -> aa ; aaab -> aaa ; aab -> etc..... but on first recursive call (second call on stack) we take the
    // right portion  from a ; aaaab and call with 'aaaab' as the new string whic leads to calls such as
    // a ; aaab -> aa ; aab, etc... where we can see that 'aaab' and 'aab' were called multiple times.
    // Map serves to store those right substrings to improve time efficiency

    public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.contains(s)) {
            return true;
        }
        if (map.containsKey(s)) {
            return map.get(s);
        }
        for (int i = 1; i <= s.length(); i++) {
            String left = s.substring(0, i);
            if (wordDict.contains(left) && wordBreak(s.substring(i), wordDict)) {
                map.put(s, true);
                return true;
            }
        }
        map.put(s, false);
        return false;
    }
} // TBH this is still hard and i only 90% get it. going to come back when reviewing for last 10%


// ATTEMPT
//class Solution {
//    public boolean wordBreak(String s, List<String> wordDict) {
//
//        HashMap<String, Boolean> dictionaryMap = new HashMap<>(); // empty map
//        for (String str : wordDict) { // wordDict mapped to reduce lookup time
//            dictionaryMap.put(str, true);
//        }
//
//        int index = 0; // index to track  current char in String
//        StringBuilder sb = new StringBuilder(); // append until value found in map
//
//        return testSubstrings(s, dictionaryMap, index, sb); // call helper method
//
//    }
//
//    private boolean testSubstrings(String OGstring, HashMap<String, Boolean> dMap, int currentIndex, StringBuilder currBuild) {
//        currBuild.append(OGstring.indexOf(currentIndex));
//
//        // idea of cycling through to find next word, then testing remainder until another word found, etc.
//        while (currentIndex < OGstring.length()){
//            if (dMap.containsKey(currBuild.toString())) {
//                currentIndex++;
//                return testSubstrings(OGstring, dMap, currentIndex, currBuild);
//            } else {
//                currentIndex++;
//            }
//        }
//
//       return false;
//    }
//}

// problem: leetcode. suppose we iterate through leetcode, find 'leet',
//  and reduce s to 'code' but then we find that dictionary contains
// 'cod' so we input that and get left with 'e'. point is, how do we know which combo to use when we affect the back half.

// recursion? find 'leet' -> true -> find cod -> true -> e = false
// pop back to 'd', iterate to 'e' code = true, s ended return true

//