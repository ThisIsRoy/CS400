import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;
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
			this.network = new SocialNetwork("");
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
                    true, network.mutualFriends("Bailey", "Alex").equals(output));
        } catch (Exception e) {
            fail("test03: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test04_influencer() {
        try {
            assertEquals("Influencer should be Alex with 6 connections",
                    true, network.influencer().equals("Alex"));
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
                    true, network.haveSeenMeme("Daniel", 1).equals(output));
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
            assertEquals("Daniel should know no one",
                    true, network.youMayKnow("Daniel").size() == 0);
        } catch (Exception e) {
            fail("test08: failed - unexpected exception occurred");
        }
    }

    @Test
    public final void test09_youMayKnow2() {
        try {
            Set<String> output = new HashSet<String>();
            output.add("Jess");
            output.add("Bailey");
            output.add("Edward");
            output.add("Addison");
            assertEquals("Alex should know Bailey, Edward, Addison, and Jess",
                    true, network.youMayKnow("Alex").equals(output));
        } catch (Exception e) {
            fail("test07: failed - unexpected exception occurred");
        }
    }
}
