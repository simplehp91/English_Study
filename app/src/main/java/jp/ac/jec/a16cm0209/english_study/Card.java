package jp.ac.jec.a16cm0209.english_study;

/**
 * Created by nguyenhiep on 1/25/2017 AD.
 */

public class Card {
    public String eng;
    public String ja;
    public byte[] img;

    public Card(String eng, String ja, byte[] img) {
        this.eng = eng;
        this.img = img;
        this.ja = ja;
    }
}
