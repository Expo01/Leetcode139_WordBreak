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
        if (wordDict.contains(s)) { // suppose original string is 'aaapple' and dict contains 'a, apple' in first
            return true; // call we already said yes 'a' is a dict item, put that in map. so we go and find another 'a'
            // in aapple. finaly substrirng is apple, and that exists, so no sense breaking it down. its in the dict,
            // return true
        }
        if (map.containsKey(s)) { // this handles redundant substings like aaab and aab as above. instead of full recursion
            // thrrough all chars again, just retturn whether that substring can be broken down into different dict words
            return map.get(s);
        }
        for (int i = 1; i <= s.length(); i++) {// tricky part. L starts as just index 0 as substring.
            String left = s.substring(0, i);
            if (wordDict.contains(left) && wordBreak(s.substring(i), wordDict)) { // recusrively calls  using substring and
                // each call will begin another call at index 0. will start to reach base cases where either the dictionary
                // does contain the value, it was already tested and mapped or was not tested and need to map as false.
                // only then do we return to for loop on top of call stack and increment 'i', etc. and pop off recurrsivee
                // calls all the way back to first loop on orginal call, we increment original for loop and do the whole
                // thting over until we loop tthrtough entire original wordBreak call
                map.put(s, true);
                return true;
            }
        }
        map.put(s, false);
        return false;
    }
} // if i actually wanted to write out the caall stacks. 'aab' is a good short example wheree 'ab' will be redundant


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