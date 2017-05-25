package dayan.eve.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xsg on 5/19/2017.
 */
public class SearchPromptUtil {

    private static final Integer SEARCH_PROMPT_NUM = 20;

    public static List<String> getPrompt(String allNameString, String key) {
        List<String> prompts = Lists.newArrayList();
        int middle;
        int left;
        int right = 0;
        while (prompts.size() <= SEARCH_PROMPT_NUM) {
            //提示个数上限20个
            middle = allNameString.indexOf(key, right);
            if (middle == -1) {
                break;
            }
            right = allNameString.indexOf("|", middle);
            left = allNameString.lastIndexOf("|", middle);

            prompts.add(allNameString.substring(left + 1, right));
        }
        return prompts;
    }
}
