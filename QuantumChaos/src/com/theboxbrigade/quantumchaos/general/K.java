package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Input.Keys;

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
		Input.WALK_WEST,
		Input.AFFIRM
	};
	private boolean[] p;					// progression
	public int i = 0;						// index
	private boolean s = false;				// solved
	
	public K() {
		p = new boolean[11];
		for (int j=0; j<p.length; j++) {
			p[j] = false;
		}
	}
	
	/**
	 * solved?<br/>
	 * @return solved
	 */
	public boolean s() {
		return s;
	}
	
	/**
	 * check if solved
	 */
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
				System.out.println("YES!");
				p[i++] = true;
				in.releaseAllKeys();
			} else {
				//System.out.println("BZZZZ!");
				//r();
			}
			cs();
		}
	}
	
	/**
	 * reset
	 */
	private void r() {
		i = 0;
		for (int j=0; j<p.length; j++) {
			p[j] = false;
		}
	}
}
