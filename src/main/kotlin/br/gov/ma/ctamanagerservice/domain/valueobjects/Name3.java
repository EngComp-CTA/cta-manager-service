package br.gov.ma.ctamanagerservice.domain.valueobjects;


import br.gov.ma.ctamanagerservice.domain.exceptions.InvalidNameException;

public class Name3 implements Comparable<Name3> {
    private String value;

    private int minSize;
    private int maxSize;
    private int minNumberOfWords;
    private int maxNumberOfWords;

    Name3() {

    }

    public Name3(String value) {
        this(value, 5, 2);
    }

    public Name3(String value, int minSize, int minNumberOfWords) {
        this(value, minSize, minNumberOfWords, 255, 6);
    }

    public Name3(String value, int minSize, int minNumberOfWords, int maxSize, int maxNumberOfWords) {
        this.minSize = minSize;
        this.minNumberOfWords = minNumberOfWords;
        this.maxSize = maxSize;
        this.maxNumberOfWords = maxNumberOfWords;

        if (value != null) {
            this.value = value.trim();
        }

        if (isInvalid()) {
            throw new InvalidNameException();
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isInvalid() {
        if (value == null) {
            return true;
        }

        boolean hasMinSize = value.length() >= minSize;
        boolean hasMaxSize = value.length() <= maxSize;
        boolean hasMinNumberOfWords = value.split(" ").length >= minNumberOfWords;
        boolean hasMaxNumberOfWords = value.split(" ").length <= maxNumberOfWords;
        return !hasMinSize || !hasMaxSize || !hasMinNumberOfWords || !hasMaxNumberOfWords;
    }

    @Override
    public int compareTo(Name3 o) {
        return value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
