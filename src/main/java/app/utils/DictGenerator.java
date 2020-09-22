package app.utils;

public class DictGenerator {
    public static char[] dic(){
        char[] result=new char[36];
        for (int i = 0; i < 26; i++) {
            result[i]=(char)('a'+i);
        }
        for (int i = 0; i < 10; i++) {
            result[i+26]=(char)('0'+i);
        }
        return result;
    }

    public static String[] words(){
        String[] words = new String[1296];
        char[] dic = dic();
        for (int i = 0; i < dic.length; i++) {
            for (int j = 0; j < dic.length; j++) {
                words[i*36+j]= String.valueOf(dic[i])+String.valueOf(dic[j]);
            }
        }
        return words;
    }
}
