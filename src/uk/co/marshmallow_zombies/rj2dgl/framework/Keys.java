package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of keys for use within game input detection.
 * 
 * @author Oliver Davenport (12033278)
 */
public enum Keys {

	// List of all keys. Not going to document each one...
	NONE(0), BACK(8), TAB(9), ENTER(13), PAUSE(19), CAPS_LOCK(20), KANA(21), KANJI(25), ESCAPE(27), IME_CONVERT(28), IME_NO_CONVERT(
			29), SPACE(32), PAGE_UP(33), PAGE_DOWN(34), END(35), HOME(36), LEFT(37), UP(38), RIGHT(39), DOWN(40), SELECT(
			41), PRINT(42), EXECUTE(43), PRINT_SCREEN(44), INSERT(45), DELETE(46), HELP(47), D0(48), D1(49), D2(50), D3(
			51), D4(52), D5(53), D6(54), D7(55), D8(56), D9(57), A(65), B(66), C(67), D(68), E(69), F(70), G(71), H(72), I(
			73), J(74), K(75), L(76), M(77), N(78), O(79), P(80), Q(81), R(82), S(83), T(84), U(85), V(86), W(87), X(88), Y(
			89), Z(90), LEFT_WINDOWS(91), RIGHT_WINDOWS(92), APPS(93), SLEEP(95), NUM_PAD_0(96), NUM_PAD_1(97), NUM_PAD_2(
			98), NUM_PAD_3(99), NUM_PAD_4(100), NUM_PAD_5(101), NUM_PAD_6(102), NUM_PAD_7(103), NUM_PAD_8(104), NUM_PAD_9(
			105), MULTIPLY(106), ADD(107), SEPERATOR(108), SUBTRACT(109), DECIMAL(110), DIVIDE(111), F1(112), F2(113), F3(
			114), F4(115), F5(116), F6(117), F7(118), F8(119), F9(120), F10(121), F11(122), F12(123), F13(124), F14(125), F15(
			126), F16(127), F17(128), F18(129), F19(130), F20(131), F21(132), F22(133), F23(134), F24(135), NUM_LOCK(
			144), SCROLL(145), LEFT_SHIFT(160), RIGHT_SHIFT(161), LEFT_CONTROL(162), RIGHT_CONTROL(163), LEFT_ALT(164), RIGHT_ALT(
			165), BROWSER_BACK(166), BROWSER_FORWARD(167), BROWSER_REFRESH(168), BROWSER_STOP(169), BROWSER_SEARCH(170), BROWSER_FAVORITES(
			171), BROWSER_HOME(172), VOLUME_MUTE(173), VOLUME_DOWN(174), VOLUME_UP(175), MEDIA_NEXT_TRACK(176), MEDIA_PREVIOUS_TRACK(
			177), MEDIA_STOP(178), MEDIA_PLAY_PAUSE(179), LAUNCH_MAIL(180), SELECT_MEDIA(181), LAUNCH_APPLICATION_1(182), LAUNCH_APPLICATION_2(
			183), OEM_SEMICOLON(186), OEM_PLUS(187), OEM_COMMA(188), OEM_MINUS(189), OEM_PERIOD(190), OEM_QUESTION(191), OEM_TILDE(
			192), CHAT_PAD_GREEN(202), CHAT_PAD_ORANGE(203), OEM_OPEN_BRACKETS(219), OEM_PIPE(220), OEM_CLOSE_BRACKETS(
			221), OEM_QUOTES(222), OEM_8(223), OEM_BACKSLASH(226), PROCESS_KEY(229), OEM_COPY(242), OEM_AUTO(243), OEM_ENL_W(
			244), ATTN(246), CRSEL(247), EXSEL(248), ERASE_EOF(249), PLAY(250), ZOOM(251), PA_1(253), OEM_CLEAR(254);

	// The key in its integer form
	private final int key;
	// A variable to map the integer values to their enum key values
	private static final Map<Integer, Keys> map = new HashMap<Integer, Keys>();

	static {
		// Iterate through all keys
		for (Keys key : Keys.values()) {
			// Add the key to the map
			map.put(key.getValue(), key);
		}
	}

	/**
	 * Initialises a new {@code Keys} with the key in its integer form.
	 * 
	 * @param value
	 *            The key in its integer form.
	 */
	Keys(int value) {
		// Store the key passed into the constructor
		this.key = value;
	}

	/**
	 * Gets the key in its integer form.
	 * 
	 * @return Returns an {@code int} which represents the key.
	 */
	public int getValue() {
		// Return the key
		return this.key;
	}

	/**
	 * Determines the key which is represented by {@code value}.
	 * 
	 * @param value
	 *            The integer form of a {@code Keys}.
	 * @return Returns a {@code Keys} which was represented by {@code value}.
	 */
	public static Keys fromInt(int value) {
		// Determine the key from the value passed
		Keys key = map.get(value);

		if (key == null)
			// Return the default value if the passed value was null
			return Keys.NONE;

		// Return the key
		return key;
	}

};
