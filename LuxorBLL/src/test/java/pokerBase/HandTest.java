package pokerBase;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DeckException;
import exceptions.HandException;
import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;

public class HandTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private Hand SetHand(ArrayList<Card> setCards, Hand h)
	{
		Object t = null;
		
		try {
			//	Load the Class into 'c'
			Class<?> c = Class.forName("pokerBase.Hand");
			//	Create a new instance 't' from the no-arg Deck constructor
			t = c.newInstance();
			//	Load 'msetCardsInHand' with the 'Draw' method (no args);
			Method msetCardsInHand = c.getDeclaredMethod("setCardsInHand", new Class[]{ArrayList.class});
			//	Change the visibility of 'setCardsInHand' to true *Good Grief!*
			msetCardsInHand.setAccessible(true);
			//	invoke 'msetCardsInHand'
			Object oDraw = msetCardsInHand.invoke(t, setCards);
			
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		h = (Hand)t;
		return h;
		
	}
	/**
	 * This test checks to see if a HandException is throw if the hand only has two cards.
	 * @throws Exception
	 */
	@Test(expected = HandException.class)
	public void TestEvalShortHand() throws Exception {
		
		Hand h = new Hand();
		
		ArrayList<Card> ShortHand = new ArrayList<Card>();
		ShortHand.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ShortHand.add(new Card(eSuit.CLUBS,eRank.ACE,0));

		h = SetHand(ShortHand,h);
		
		//	This statement should throw a HandException
		h = Hand.EvaluateHand(h);	
	}	
			
	@Test
	public void TestFourOfAKind() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));		
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}
	
	@Test
	public void TestFourOfAKindEval() {
		
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFourOfAKindEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}
	@Test
	public void TestFourOfAKindEval2() {
		
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFourOfAKindEval2 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}	
	@Test
	public void TestFullHouseEval() {
		
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(FullHouse,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFullHouseEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsHandFullHouse = true;
		
		
		assertEquals(bActualIsHandFullHouse,bExpectedIsHandFullHouse);		

		assertEquals(hs.getHiHand(),eRank.KING.getiRankNbr());		
	}	
	@Test
	public void TestFullHouseEval2() {
		
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(FullHouse,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFullHouseEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsHandFullHouse = true;
		
		
		assertEquals(bActualIsHandFullHouse,bExpectedIsHandFullHouse);		

		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	}	
	@Test
	public void TestFlushEval() {
		
		ArrayList<Card> Flush = new ArrayList<Card>();
		Flush.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(Flush,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFlushEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFlush = Hand.isHandFlush(h, hs);
		boolean bExpectedIsHandFlush = true;
		
		
		assertEquals(bActualIsHandFlush,bExpectedIsHandFlush);		

		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	}	
	@Test
	public void TestStraightEval() {
		
		ArrayList<Card> Straight = new ArrayList<Card>();
		Straight.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.SIX,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.SEVEN,0));
		
		Hand h = new Hand();
		h = SetHand(Straight,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestStraightEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandStraight = Hand.isHandStraight(h, hs);
		boolean bExpectedIsHandStraight = true;
		
		
		assertEquals(bActualIsHandStraight,bExpectedIsHandStraight);		

		assertEquals(hs.getHiHand(),eRank.SEVEN.getiRankNbr());		
	}
	@Test
	public void TestStraightEval2() {
		
		ArrayList<Card> Straight = new ArrayList<Card>();
		Straight.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		
		Hand h = new Hand();
		h = SetHand(Straight,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestStraightEval2 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandStraight = Hand.isHandStraight(h, hs);
		boolean bExpectedIsHandStraight = true;
		
		
		assertEquals(bActualIsHandStraight,bExpectedIsHandStraight);		

		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	}
	@Test
	public void TestStraightEval3() {
		
		ArrayList<Card> Straight = new ArrayList<Card>();
		Straight.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.JACK,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		
		Hand h = new Hand();
		h = SetHand(Straight,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestStraightEval3 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandStraight = Hand.isHandStraight(h, hs);
		boolean bExpectedIsHandStraight = true;
		
		
		assertEquals(bActualIsHandStraight,bExpectedIsHandStraight);		

		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	}
	@Test
	public void TestThreeOfAKindEval() {
		
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestThreeOfAKindEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;
		
		
		assertEquals(bActualIsHandThreeOfAKind,bExpectedIsHandThreeOfAKind);		

		assertEquals(hs.getHiHand(),eRank.FOUR.getiRankNbr());
		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
				
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.FIVE);
	}	
	@Test
	public void TestThreeOfAKindEval2() {
		
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestThreeOfAKindEval2 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;
		
		
		assertEquals(bActualIsHandThreeOfAKind,bExpectedIsHandThreeOfAKind);		

		assertEquals(hs.getHiHand(),eRank.FIVE.getiRankNbr());
		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
				
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.FOUR);
	}	
	@Test
	public void TestThreeOfAKindEval3() {
		
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FOUR,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestThreeOfAKindEval3 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandThreeOfAKind = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedIsHandThreeOfAKind = true;
		
		
		assertEquals(bActualIsHandThreeOfAKind,bExpectedIsHandThreeOfAKind);		

		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());
		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
				
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.FIVE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.FOUR);
	}	
	@Test
	public void TestTwoPairEval() {
		
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestTwoPairEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;
		
	
		assertEquals(bActualIsHandTwoPair,bExpectedIsHandTwoPair);		
	
		assertEquals(hs.getHiHand(),eRank.KING.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
	}	
	@Test
	public void TestTwoPairEval2() {
		
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestTwoPairEval2 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;
		
	
		assertEquals(bActualIsHandTwoPair,bExpectedIsHandTwoPair);		
	
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.TWO);
	}	
	@Test
	public void TestTwoPairEval3() {
		
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestTwoPairEval3 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedIsHandTwoPair = true;
		
	
		assertEquals(bActualIsHandTwoPair,bExpectedIsHandTwoPair);		
	
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}	
	@Test
	public void TestPairEval() {
		
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestPairEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;
		
	
		assertEquals(bActualIsHandPair,bExpectedIsHandPair);		
	
		assertEquals(hs.getHiHand(),eRank.TWO.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.KING);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.QUEEN);
	}	
	@Test
	public void TestPairEval2() {
		
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestPairEval2 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;
		
	
		assertEquals(bActualIsHandPair,bExpectedIsHandPair);		
	
		assertEquals(hs.getHiHand(),eRank.KING.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.TWO);
	}		
	@Test
	public void TestPairEval3() {
		
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestPairEval3 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;
		
	
		assertEquals(bActualIsHandPair,bExpectedIsHandPair);		
	
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.QUEEN);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.TWO);
	}	
	@Test
	public void TestPairEval4() {
		
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestPairEval4 failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandPair = Hand.isHandPair(h, hs);
		boolean bExpectedIsHandPair = true;
		
	
		assertEquals(bActualIsHandPair,bExpectedIsHandPair);		
	
		assertEquals(hs.getHiHand(),eRank.QUEEN.getiRankNbr());		
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
	
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.ACE);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank(), eRank.KING);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		
		assertEquals(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank(), eRank.TWO);
	}	

	
	
	
}
