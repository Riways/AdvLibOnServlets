package util.file_parser;

import java.util.Objects;

public class WordAndCounter implements Comparable<WordAndCounter> {
    private String word;
    private int counter;
    private boolean isWordInOxford3000;

    public WordAndCounter(String word, int counter) {
        this.word = word;
        this.counter = counter;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    
     public boolean isWordInOxford3000() {
		return isWordInOxford3000;
	}

	public void setWordInOxford3000(boolean isWordInOxford3000) {
		this.isWordInOxford3000 = isWordInOxford3000;
	}

	//Sorted from biggest value to lowest
    @Override
    public int compareTo(WordAndCounter o) {
        return o.counter - counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordAndCounter that = (WordAndCounter) o;
        return counter == that.counter && word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, counter);
    }

    @Override
    public String toString() {
        return "WordAndCounter{" +
                "word='" + word + '\'' +
                ", counter=" + counter +
                '}';
    }
}
