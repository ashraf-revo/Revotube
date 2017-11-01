package org.revo.Domain;

/**
 * Created by ashraf on 24/03/17.
 */
public enum Bucket {
    Videos("videos-revo"), Ts("ts-revo"), Thumb("thumb-revo");

    private String name;

    Bucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
