package com.theboxbrigade.quantumchaos.general;

/**
 * This class is intentionally difficult to read
 * @author Jason
 */
public class K {
	private static final int[] c = {
		Input.DPAD_UP,
		Input.DPAD_UP,
		Input.DPAD_DOWN,
		Input.DPAD_DOWN,
		Input.DPAD_LEFT,
		Input.DPAD_RIGHT,
		Input.DPAD_LEFT,
		Input.DPAD_RIGHT,
		Input.B,
		Input.WALK_SOUTH,
		Input.AFFIRM
	};
	private boolean[] p;					// progression
	public int i = 0;						// index
	private boolean s = false;				// solved
	
	public K() {
		p = new boolean[c.length];
		for (int j=0; j<p.length; j++) {
			p[j] = false;
		}
	}
	
	public boolean s() {
		return s;
	}
	
	private void cs() {
		boolean so = true;
		for (int j=0; j<p.length; j++) {
			so = (p[j])? so : !so;
			if (!so) break;
		}
		s = so;
	}
	
	public void pr(Input in) {
		if (i < c.length) {
			if (in.buttons[c[i]]) {
				in.buttons[c[i]] = false;
				p[i++] = true;
				in.releaseAllKeys();
			} else {
				for (int j=0; j<in.buttons.length; j++) {
					if (in.buttons[j]) {
						r();
						break;
					}
				}
			}
			cs();
		}
	}
	
	private void r() {
		i = 0;
		for (int j=0; j<p.length; j++) {
			p[j] = false;
		}
	}
}
