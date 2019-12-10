//
// Title:           friend graph algorithms
// Files:           Requires: N/A
// Course:          CS 400 Fall 19 2019
//
// Author:          Roy Sun
// Email:           rsun65@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

/**
 * JUnit test class to test class Graph that implements GraphADT interface.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkTest {
    private SocialNetwork network;

    @Rule
    public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

    @Before
    public void setUp() throws Exception {
        // you may use any or all of the provided json files for JUnit testing
        this.network = new SocialNetwork("social-network-md.json");
    }

    @After
    public void tearDown() throws Exception {
        this.network = null;
    }

    @Test
    public final void test00_socialButterflyValid() {
        try {
            assertEquals("test00: failed - expected: \"Addison\" returned: \"" + network.socialButterfly() +  "\"",
                    true, network.socialButterfly().equals("Addison"));
        } catch (Exception e) {
            fail("test00: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test01_socialButterflyEmptyNetwork() {
        try {
            this.network = new SocialNetwork("social-network-empty.json");
            assertEquals("test00: failed - expected: \"\" returned: \"" + network.socialButterfly() +  "\"",
                    true, network.socialButterfly().equals(""));
        } catch (Exception e) {
            fail("test01: failed - unexpected exception occurred");
        }
    }

    // TODO: write your tests here - don't forget edge cases!
    @Test
    public final void test02_avaregeFriendsPerPerson() {
        try {
            assertEquals("Average # of friends for social-network-md.json is incorrect",
                    true, network.averageFriendsPerPerson() == 3);
        } catch (Exception e) {
            fail("test02: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test02_mutualFriendsEmpty() {
        try {
            assertEquals("Mutual friends between Mel and Jess should be empty",
                    true, network.mutualFriends("Mel", "Jess").size() == 0);
        } catch (Exception e) {
            fail("test03: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test03_mutualFriends() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Edward");
            output.add("Addison");
            output.add("Jess");
            assertEquals("Mutual friends between Bailey and Alex should be (Edward, Addison, Jess)",
                    output, network.mutualFriends("Bailey", "Alex"));
        } catch (Exception e) {
            fail("test03: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test04_influencer() {
        try {
            assertEquals("Influencer should be Alex with 6 connections",
                    "Alex", network.influencer());
        } catch (Exception e) {
            fail("test04: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test05_haveSeenMeme_1() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Daniel");
            assertEquals("Only Daniel should have seen meme",
                    output, network.haveSeenMeme("Daniel", 1));
        } catch (Exception e) {
            fail("test05: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test06_haveSeenMeme_2() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Daniel");
            output.add("Riley");
            output.add("Jess");
            assertEquals("Daniel, Riley and Jess should have seen meme",
                    true, network.haveSeenMeme("Daniel", 3).equals(output));
        } catch (Exception e) {
            fail("test06: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test07_haveSeenMeme_3() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Daniel");
            output.add("Riley");
            output.add("Jess");
            output.add("Mel");
            output.add("Bailey");
            output.add("Edward");
            output.add("Addison");
            output.add("Alex");
            assertEquals("Everyone should have seen meme",
                    true, network.haveSeenMeme("Daniel", 15).equals(output));
        } catch (Exception e) {
            fail("test07: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test08_youMayKnow1() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Jess");
            assertEquals("Daniel may know Jess",
                    output, network.youMayKnow("Daniel"));
        } catch (Exception e) {
            fail("test08: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test09_youMayKnow2() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Mel");
            output.add("Riley");
            assertEquals("Alex may know Mel and Riley",
                    output, network.youMayKnow("Alex"));
        } catch (Exception e) {
            fail("test09: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test10_isFriendGroup1() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Edward");
            output.add("Bailey");
            output.add("Alex");
            output.add("Addison");
            assertEquals("Alex, Bailey, Edward, and Addison should be a friend group",
                    true, network.isFriendGroup(output));
        } catch (Exception e) {
            fail("test10: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test11_isFriendGroup2() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Riley");
            output.add("Jess");
            output.add("Daniel");
            assertEquals("Riley, Jess and Daniel should not be a friend group",
                    false, network.isFriendGroup(output));
        } catch (Exception e) {
            fail("test11: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test12_isFriendGroup3() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Addison");
            output.add("Mel");
            assertEquals("Mel and Addison should be a friend group",
                    true, network.isFriendGroup(output));
        } catch (Exception e) {
            fail("test12: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test13_glue1() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Daniel");
            output.add("Riley");
            output.add("Jess");
            output.add("Mel");
            output.add("Bailey");
            output.add("Edward");
            output.add("Addison");
            output.add("Alex");
            assertEquals("Addison should be the glue",
                    "Addison", network.glue(output));
        } catch (Exception e) {
            fail("test13: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test14_glue2() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Bailey");
            output.add("Riley");
            assertEquals("Graph should already be disconnected",
                    "", network.glue(output));
        } catch (Exception e) {
            fail("test14: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test15_glue3() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Alex");
            output.add("Bailey");
            output.add("Edward");
            assertEquals("There should be no glue",
                    "", network.glue(output));
        } catch (Exception e) {
            fail("test15: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test16_glue4() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Alex");
            output.add("Jess");
            output.add("Riley");
            output.add("Daniel");
            assertEquals("Jess should be glue",
                    "Jess", network.glue(output));
        } catch (Exception e) {
            fail("test16: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test17_glue5() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Alex");
            output.add("Daniel");
            assertEquals("Two people, so no glue",
                    "", network.glue(output));
        } catch (Exception e) {
            fail("test17: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test18_sixDegrees1() {
        try {
            assertEquals("Graph is within 6 degrees",
                    true, network.sixDegreesOfSeparation());
        } catch (Exception e) {
            fail("test16: failed - unexpected exception occurred");
        }
    }


    @Test
    public final void test19_ladder1() {
        try {
            List<String> output = new ArrayList<String>();
            output.add("Daniel");
            output.add("Riley");
            output.add("Jess");
            output.add("Bailey");
            assertEquals("Daniel - Riley - Jess - Bailey",
                    output, network.socialLadder("Daniel", "Bailey"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("test19: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test20_ladder2() {
        try {
            List<String> output = new ArrayList<String>();
            output.add("Edward");
            output.add("Alex");
            output.add("Jess");
            assertEquals("Edward - Alex - Jess",
                    output, network.socialLadder("Edward", "Jess"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("test20: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test21_ladder3() {
        try {
            List<String> output = new ArrayList<String>();
            output.add("Alex");
            output.add("Edward");
            assertEquals("Alex - Edward",
                    output, network.socialLadder("Alex", "Edward"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("test21: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test22_ladder4() {
        try {
            this.network = new SocialNetwork("social-network.json"); //modified social network with disconnection
            List<String> output = new ArrayList<String>();
            assertEquals("No path from Lilly to Aaron",
                    output, network.socialLadder("Lilly", "Aaron"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("test22: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test23_ladder4() {
        try {
            List<String> output = new ArrayList<String>();
            assertEquals("One person doesn't exist, empty list",
                    output, network.socialLadder("Lilly", "Dan"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("test23: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test24_sixDegrees2() {
        try {
            this.network = new SocialNetwork("social-network.json"); //modified social network with disconnection
            assertEquals("Disconnected graph, should return false",
                    false, network.sixDegreesOfSeparation());
        } catch (Exception e) {
            e.printStackTrace();
            fail("test24: failed - unexpected exception occurred");
        }
    }

//    @Test
//    public final void test25_sixDegrees3() {
//        try {
//            this.network = new SocialNetwork("social-network-long.json"); //modified social network
//            assertEquals("Long graph, should return false",
//                    false, network.sixDegreesOfSeparation());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("test25: failed - unexpected exception occurred");
//        }
//    }
//
//    @Test
//    public final void test26_glue6() {
//        try {
//            this.network = new SocialNetwork("social-network-long.json"); //modified social network
//            Set<String> output = new HashSet<String>();
//            output.add("Lilly");
//            output.add("Scott");
//            output.add("Malika");
//            output.add("Aaron");
//            output.add("Sam");
//            output.add("Josh");
//            output.add("Ben");
//            output.add("Steve");
//            assertEquals("Long graph, Aaron should be glue",
//                    "Aaron", network.glue(output));
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("test26: failed - unexpected exception occurred");
//        }
//    }
//
//    @Test
//    public final void test27_glue7() {
//        try {
//            this.network = new SocialNetwork("social-network-long.json"); //modified social network
//            Set<String> output = new HashSet<String>();
//            output.add("Lilly");
//            output.add("Scott");
//            output.add("Malika");
//            output.add("Sam");
//            output.add("Josh");
//            output.add("Ben");
//            output.add("Steve");
//            assertEquals("Disconnected graph",
//                    "", network.glue(output));
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("test27: failed - unexpected exception occurred");
//        }
//    }
//
//    @Test
//    public final void test28_glue8() {
//        try {
//            this.network = new SocialNetwork("social-network-long.json"); //modified social network
//            Set<String> output = new HashSet<String>();
//            output.add("Sam");
//            output.add("Josh");
//            output.add("Ben");
//            assertEquals("Josh should be glue",
//                    "Josh", network.glue(output));
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("test28: failed - unexpected exception occurred");
//        }
//    }
//
//    @Test
//    public final void test29_ladder4() {
//        try {
//            this.network = new SocialNetwork("social-network-long.json"); //modified social network
//            List<String> output = new ArrayList<String>();
//            output.add("Sam");
//            output.add("Josh");
//            output.add("Ben");
//            output.add("Steve");
//            assertEquals("Sam - Josh - Ben - Steve",
//                    output, network.socialLadder("Sam", "Steve"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("test29: failed - unexpected exception occurred");
//        }
//    }
}
