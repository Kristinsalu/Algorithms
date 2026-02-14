package ee.taltech.algoritmid.comeworkabit;

import java.util.ArrayList;
import java.util.List;

public class HW1 implements ComeWorkaBit {
    private final BinarySearchTree<Double, Candidate> candidates = new BinarySearchTree<>();
    private final BinarySearchTree<Double, Offer> offers = new BinarySearchTree<>();

    @Override
    public Contract registerCandidate(Candidate candidate) throws IllegalArgumentException{
        validate(candidate);

        double candidateMin = (candidate.time() - 2) / 1.1;
        double candidateMax = (candidate.time() + 2) / 0.9;
        double referenceTime = candidate.time();

        Offer bestBelow = offers.findBestSmaller(referenceTime);
        Offer bestAbove = offers.findBestLarger(referenceTime);

        List<Offer> potentialOffers = new ArrayList<>();

        if (isMatch(bestBelow, candidateMin, candidateMax)) {
            potentialOffers.add(bestBelow);
        }
        if (isMatch(bestAbove, candidateMin, candidateMax)) {
            if (bestAbove != bestBelow) {
                potentialOffers.add(bestAbove);
            }
        }

        Offer bestOffer = findBestMatch(potentialOffers, referenceTime);

        if (bestOffer == null) {
            candidates.insert(candidate.time(), candidate);
            return null;
        }
        unregisterOffer(bestOffer);

        return new Contract() {
            @Override
            public Candidate candidate() {return candidate;}
            @Override
            public Offer offer() { return bestOffer;}
        };
    }

    @Override
    public void unregisterCandidate(Candidate candidate) throws IllegalArgumentException {
        validate(candidate);
        candidates.delete(candidate.time(), candidate);
    }

    @Override
    public Contract registerOffer(Offer offer) throws IllegalArgumentException {
        validate(offer);

        double offerMin = offer.time() * 0.9 - 2;
        double offerMax = offer.time() * 1.1 + 2;
        double referenceTime = offer.time();

        Candidate bestBelow = candidates.findBestSmaller(referenceTime);
        Candidate bestAbove = candidates.findBestLarger(referenceTime);

        List<Candidate> potentialCandidates = new ArrayList<>();

        if (isMatch(bestBelow, offerMin, offerMax)) {
            potentialCandidates.add(bestBelow);
        }
        if (isMatch(bestAbove, offerMin, offerMax)) {
            if (bestAbove != bestBelow) {
                potentialCandidates.add(bestAbove);
            }
        }

        Candidate bestCandidate = findBestMatch(potentialCandidates, referenceTime);

        if (bestCandidate == null) {
            offers.insert(offer.time(), offer);
            return null;
        }

        unregisterCandidate(bestCandidate);

        return new Contract() {
            @Override
            public Candidate candidate() {
                return bestCandidate;
            }

            @Override
            public Offer offer() {
                return offer;
            }
        };
    }

    @Override
    public void unregisterOffer (Offer offer) throws IllegalArgumentException {
        validate(offer);
        offers.delete(offer.time(), offer);
    }

    @Override
    public Recommendation recommendationForCandidate (Candidate candidate) throws IllegalArgumentException {
        validate(candidate);

        List<Offer> allOffers = offers.inOrderTraversal();
        int shorter = 0, longer = 0;

        for (Offer o : allOffers) {
            if (o.time() < candidate.time()) shorter++;
            else if (o.time() > candidate.time()) longer++;
        }

        if (shorter == longer) return Recommendation.NO_RECOMMENDATION;
        return shorter > longer ? Recommendation.WORK_LESS : Recommendation.WORK_MORE;
    }

    @Override
    public List<QueueItem> queue () {
        List<Offer> offersSorted = offers.inOrderTraversal();
        List<Candidate> candidatesSorted = candidates.inOrderTraversal();
        List<QueueItem> mergedQueue = new ArrayList<>();

        int offerIndex = 0;
        int candidateIndex = 0;
        while (offerIndex < offersSorted.size() && candidateIndex < candidatesSorted.size()) {
            Offer currentOffer = offersSorted.get(offerIndex);
            Candidate currentCandidate = candidatesSorted.get(candidateIndex);

            if (currentOffer.time() <= currentCandidate.time()) {
                mergedQueue.add(currentOffer);
                offerIndex++;
            } else {
                mergedQueue.add(currentCandidate);
                candidateIndex++;
            }
        }

        while (offerIndex < offersSorted.size()) {
            mergedQueue.add(offersSorted.get(offerIndex));
            offerIndex++;
        }
        while (candidateIndex < candidatesSorted.size()) {
            mergedQueue.add(candidatesSorted.get(candidateIndex));
            candidateIndex++;
        }
        return mergedQueue;
    }

    private void validateCommon(QueueItem item) throws IllegalArgumentException {
        if (item == null || item.time() <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validate(Candidate candidate) throws IllegalArgumentException {
        validateCommon(candidate);
        if (candidate.name() == null || candidate.name().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validate(Offer offer) throws IllegalArgumentException {
        validateCommon(offer);
        if (offer.title() == null || offer.title().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private <T extends QueueItem> T findBestMatch(List<T> items, double referenceTime)  {
        T bestItem = null;
        double minDifference = Double.MAX_VALUE;

        for (T item : items) {
            double diff = Math.abs(item.time() - referenceTime);

            if (diff < minDifference) {
                minDifference = diff;
                bestItem = item;
            } else if (diff == minDifference) {
                if (bestItem == null || item.time() < bestItem.time()) {
                    bestItem = item;
                }
            }
        }
        return bestItem;
    }

    private boolean isMatch(QueueItem item, double minTime, double maxTime) {
        if (item == null) {
            return false;
        }
        double itemTime = item.time();
        return itemTime >= minTime && itemTime <= maxTime;
    }
}