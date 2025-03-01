package com.example.statistics.service;

public interface Statistics {
    /**
     * The only method which takes events as input.
     *
     * @param n
     */
    void event(int n);

    /**
     * Minimum of all the events consumed
     *
     * @return
     */
    int min();

    /**
     * Maximum of all the events consumed
     *
     * @return
     */
    int max();

    /**
     * Mean of all the events consumed
     *
     * @return
     */
    float mean();

    /**
     * Variance of all the events consumed
     *
     * @return
     */
    float variance();
}
