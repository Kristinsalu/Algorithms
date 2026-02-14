package ee.taltech.algoritmid.comeworkabit;

import java.util.List;

public interface ComeWorkaBit {

    enum Recommendation {
        NO_RECOMMENDATION,
        WORK_LESS,
        WORK_MORE
    }

    Contract registerCandidate(Candidate candidate) throws IllegalArgumentException;

    void unregisterCandidate(Candidate candidate) throws IllegalArgumentException;

    Contract registerOffer(Offer offer) throws IllegalArgumentException;

    void unregisterOffer(Offer offer) throws IllegalArgumentException;

    Recommendation recommendationForCandidate(Candidate candidate) throws IllegalArgumentException;

    List<QueueItem> queue();

}