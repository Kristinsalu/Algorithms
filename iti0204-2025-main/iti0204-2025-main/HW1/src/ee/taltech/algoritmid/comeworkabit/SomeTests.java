package ee.taltech.algoritmid.comeworkabit;

import java.util.ArrayList;
import java.util.List;

public class SomeTests {

    public static void main(String[] args) throws Exception {
        testOfferTreeEndToEndPublic();
        testCandidateTreeEndToEndPublic();
    }

    private static Candidate createCandidate(String name, double time) {
        return new Candidate() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public double time() {
                return time;
            }
        };
    }

    private static Offer createOffer(String title, double time) {
        return new Offer() {
            @Override
            public String title() {
                return title;
            }

            @Override
            public double time() {
                return time;
            }
        };
    }

    private static Contract registerQueueItem(HW1 solution, QueueItem queueItem) {
        if (queueItem instanceof Candidate) {
            return solution.registerCandidate((Candidate) queueItem);
        } else if (queueItem instanceof Offer) {
            return solution.registerOffer((Offer) queueItem);
        }

        return null;
    }

    private static void fail(String error) throws Exception {
        throw new Exception(error);
    }

    private static void testTreeEndToEnd(String caseId, List<QueueItem> requests, List<Double> responds) throws Exception {
        HW1 solution = new HW1();

        for (int i = 0; i < requests.size(); i++) {
            // System.out.println("i = " + i + " add: " + requests.get(i).time());
            testRequestResponse(caseId, solution, requests.get(i), responds.get(i));
        }
    }

    private static void testRequestResponse(String caseId, HW1 solution, QueueItem contractParty, Double expectedContractPartyTime) throws Exception {

        Contract contract = registerQueueItem(solution, contractParty);

        if (contract == null) {
            if (expectedContractPartyTime != null) {
                // System.out.printf("expected: " + expectedContractPartyTime);
                fail("Contract party wasn't found, but should have: " + caseId);
            }
        } else {
            if (expectedContractPartyTime == null) {
                fail("Contract party was found, but shouldn't have: " + caseId);
            } else {
                QueueItem duoPartner = contractParty instanceof Candidate ? contract.offer() : contract.candidate();

                if (duoPartner.time() != expectedContractPartyTime) {
//                    System.out.printf("expected: " + expectedContractPartyTime + " got: " + duoPartner.time());
                    fail("Contract party with wrong time found: " + caseId);
                }
            }
        }
    }

    private static void testCandidateTreeEndToEndPublic() throws Exception {
        List<QueueItem> requests = new ArrayList<>();
        List<Double> responds = new ArrayList<>();

        requests.add(createCandidate("M", 150));
        responds.add(null);

        requests.add(createCandidate("M",  130));
        responds.add(null);

        requests.add(createCandidate("M",  135));
        responds.add(null);

        requests.add(createCandidate("M",  149));
        responds.add(null);

        requests.add(createCandidate("M",  200));
        responds.add(null);

        requests.add(createCandidate("M",  170));
        responds.add(null);

        requests.add(createCandidate("M",  160));
        responds.add(null);

        requests.add(createCandidate("M",  133));
        responds.add(null);

        requests.add(createCandidate("M",  125));
        responds.add(null);

        requests.add(createCandidate("M",  190));
        responds.add(null);

        requests.add(createCandidate("M",  140));
        responds.add(null);

        requests.add(createCandidate("M",  195));
        responds.add(null);

        requests.add(createCandidate("M",  148));
        responds.add(null);

        requests.add(createCandidate("M",  210));
        responds.add(null);

        requests.add(createCandidate("M",  138));
        responds.add(null);

        requests.add(createCandidate("M",  205));
        responds.add(null);

        requests.add(createCandidate("M",  165));
        responds.add(null);

        requests.add(createCandidate("M",  163));
        responds.add(null);

        requests.add(createCandidate("M",  168));
        responds.add(null);


        requests.add(createOffer("F",  147.99));
        responds.add(148.0);

        requests.add(createOffer("F",  133.001));
        responds.add(133.0);

        requests.add(createOffer("F",  158.7));
        responds.add(160.0);

        requests.add(createOffer("F",  141.2));
        responds.add(140.0);

        requests.add(createOffer("F",  151.3));
        responds.add(150.0);


        requests.add(createCandidate("M",  169));
        responds.add(null);

        requests.add(createCandidate("M",  139));
        responds.add(null);

        requests.add(createCandidate("M",  180));
        responds.add(null);

        requests.add(createCandidate("M",  134));
        responds.add(null);


        requests.add(createOffer("F",  161.1));
        responds.add(163.0);

        requests.add(createOffer("F",  133.7));
        responds.add(134.0);

        requests.add(createOffer("F",  131.007));
        responds.add(130.0);

        requests.add(createOffer("F",  181.5));
        responds.add(180.0);


        testTreeEndToEnd("case 9B", requests, responds);
    }

    private static void testOfferTreeEndToEndPublic() throws Exception {
        List<QueueItem> requests = new ArrayList<>();
        List<Double> responds = new ArrayList<>();

        requests.add(createOffer("F",  110));
        responds.add(null);

        requests.add(createOffer("F",  90));
        responds.add(null);

        requests.add(createOffer("F",  95));
        responds.add(null);

        requests.add(createOffer("F",  109));
        responds.add(null);

        requests.add(createOffer("F",  160));
        responds.add(null);

        requests.add(createOffer("F",  130));
        responds.add(null);

        requests.add(createOffer("F",  120));
        responds.add(null);

        requests.add(createOffer("F",  93));
        responds.add(null);

        requests.add(createOffer("F",  85));
        responds.add(null);

        requests.add(createOffer("F",  150));
        responds.add(null);

        requests.add(createOffer("F",  100));
        responds.add(null);

        requests.add(createOffer("F",  155));
        responds.add(null);

        requests.add(createOffer("F",  108));
        responds.add(null);

        requests.add(createOffer("F",  170));
        responds.add(null);

        requests.add(createOffer("F",  98));
        responds.add(null);

        requests.add(createOffer("F",  165));
        responds.add(null);

        requests.add(createOffer("F",  125));
        responds.add(null);

        requests.add(createOffer("F",  123));
        responds.add(null);

        requests.add(createOffer("F",  128));
        responds.add(null);


        requests.add(createCandidate("M",  109.8));
        responds.add(110.0);

        requests.add(createCandidate("M",  108.2));
        responds.add(108.0);

        requests.add(createCandidate("M",  131.7));
        responds.add(130.0);

        requests.add(createCandidate("M",  110.654));
        responds.add(109.0);

        requests.add(createCandidate("M",  127.007));
        responds.add(128.0);


        requests.add(createOffer("F",  129));
        responds.add(null);

        requests.add(createOffer("F",  99));
        responds.add(null);

        requests.add(createOffer("F",  140));
        responds.add(null);

        requests.add(createOffer("F",  94));
        responds.add(null);


        requests.add(createCandidate("M",  130.7));
        responds.add(129.0);

        requests.add(createCandidate("M",  99.998));
        responds.add(100.0);

        requests.add(createCandidate("M",  101.111));
        responds.add(99.0);

        requests.add(createCandidate("M",  148.7));
        responds.add(150.0);


        testTreeEndToEnd("case 9D", requests, responds);
    }



}
